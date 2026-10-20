import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { forkJoin } from 'rxjs';
import { TratamientoService } from '../../services/tratamiento.service';
import { MascotaService } from '../../services/mascota.service';
import { DrogaService } from '../../services/droga.service';
import { VeterinarioService } from '../../services/veterinario.service';
import { Mascota } from '../../models/mascota.model';
import { Droga } from '../../models/droga.model';
import { Veterinario } from '../../models/veterinario.model';

@Component({
  selector: 'app-tratamiento-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './tratamiento-form.component.html',
  styleUrl: './tratamiento-form.component.scss'
})
export class TratamientoFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private tratamientoService = inject(TratamientoService);
  private mascotaService = inject(MascotaService);
  private drogaService = inject(DrogaService);
  private veterinarioService = inject(VeterinarioService);

  tratamientoForm!: FormGroup;
  mascotas: Mascota[] = [];
  drogas: Droga[] = [];
  veterinarios: Veterinario[] = [];

  mascotaSeleccionada: Mascota | null = null;
  drogaSeleccionada: Droga | null = null;

  cargandoDatos: boolean = true;
  guardando: boolean = false;

  mensajeError: string = '';
  mensajeExito: string = '';
  errorSinDrogas: boolean = false;

  ngOnInit(): void {
    this.tratamientoForm = this.fb.group({
      mascotaId: [null, [Validators.required]],
      veterinarioId: [null, [Validators.required]],
      drogaId: [null, [Validators.required]],
      observaciones: ['', [Validators.required, Validators.minLength(5)]]
    });

    this.cargarDatosIniciales();

    // Reaccionar al cambio de droga para validar stock y mostrar advertencias
    this.tratamientoForm.get('drogaId')?.valueChanges.subscribe((drogaId) => {
      this.drogaSeleccionada = this.drogas.find(d => d.id === Number(drogaId)) || null;
      if (this.drogaSeleccionada && (this.drogaSeleccionada.unidadesDisponibles || 0) <= 0) {
        this.mensajeError = `El medicamento "${this.drogaSeleccionada.nombre}" no cuenta con unidades disponibles en el inventario.`;
      } else if (this.mensajeError.includes('unidades disponibles')) {
        this.mensajeError = '';
      }
    });

    // Reaccionar al cambio de mascota
    this.tratamientoForm.get('mascotaId')?.valueChanges.subscribe((mascotaId) => {
      this.mascotaSeleccionada = this.mascotas.find(m => m.id === Number(mascotaId)) || null;
    });
  }

  cargarDatosIniciales(): void {
    this.cargandoDatos = true;
    this.mensajeError = '';

    forkJoin({
      mascotas: this.mascotaService.obtenerMascotas(),
      drogas: this.drogaService.obtenerTodas(),
      veterinarios: this.veterinarioService.obtenerTodos()
    }).subscribe({
      next: ({ mascotas, drogas, veterinarios }) => {
        this.mascotas = mascotas;
        this.drogas = drogas;
        this.veterinarios = veterinarios;

        // Validar si no hay drogas en el sistema
        if (!this.drogas || this.drogas.length === 0) {
          this.errorSinDrogas = true;
          this.mensajeError = 'No hay medicamentos registrados en el sistema. Debe subir el inventario en formato Excel antes de recetar tratamientos.';
        } else {
          const hayConStock = this.drogas.some(d => (d.unidadesDisponibles || 0) > 0);
          if (!hayConStock) {
            this.errorSinDrogas = true;
            this.mensajeError = 'Todos los medicamentos registrados tienen stock agotado (0 unidades disponibles). Por favor reabastezca o cargue el Excel.';
          }
        }

        // Revisar si viene mascotaId por query param (ej. /tratamientos/nuevo?mascotaId=3)
        const qMascotaId = this.route.snapshot.queryParams['mascotaId'];
        if (qMascotaId) {
          const numId = Number(qMascotaId);
          this.tratamientoForm.patchValue({ mascotaId: numId });
          this.mascotaSeleccionada = this.mascotas.find(m => m.id === numId) || null;
        }

        // Si hay veterinarios, preseleccionar el primero por defecto
        if (this.veterinarios.length > 0 && !this.tratamientoForm.value.veterinarioId) {
          this.tratamientoForm.patchValue({ veterinarioId: this.veterinarios[0].id });
        }

        this.cargandoDatos = false;
      },
      error: (err) => {
        console.error('Error al cargar datos del formulario de tratamiento:', err);
        this.mensajeError = 'Error de conexión al cargar la información del hospital. Verifique que el backend esté activo.';
        this.cargandoDatos = false;
      }
    });
  }

  get esDrogaSinStock(): boolean {
    return !!(this.drogaSeleccionada && (this.drogaSeleccionada.unidadesDisponibles || 0) <= 0);
  }

  handleSubmit(): void {
    if (this.tratamientoForm.invalid) {
      this.tratamientoForm.markAllAsTouched();
      return;
    }

    if (this.esDrogaSinStock) {
      this.mensajeError = `Operación rechazada: No hay unidades disponibles de "${this.drogaSeleccionada?.nombre}". Descuento de inventario no permitido.`;
      return;
    }

    this.guardando = true;
    this.mensajeError = '';
    this.mensajeExito = '';

    const val = this.tratamientoForm.value;
    const dto = {
      mascotaId: Number(val.mascotaId),
      veterinarioId: Number(val.veterinarioId),
      drogaId: Number(val.drogaId),
      observaciones: val.observaciones
    };

    this.tratamientoService.crearTratamiento(dto).subscribe({
      next: (tratamientoCreado) => {
        this.guardando = false;
        this.mensajeExito = '¡Tratamiento registrado con éxito! El inventario ha sido actualizado.';
        // Redirigir al detalle de la mascota tras 1.2 segundos para ver el tratamiento reflejado
        setTimeout(() => {
          this.router.navigate(['/mascotas', dto.mascotaId]);
        }, 1200);
      },
      error: (err) => {
        this.guardando = false;
        console.error('Error al registrar tratamiento:', err);
        // Mostrar mensaje detallado devuelto por RestExceptionHandler
        const msg = err.error?.message || err.error?.error || 'No se pudo registrar el tratamiento.';
        this.mensajeError = msg;
      }
    });
  }
}
