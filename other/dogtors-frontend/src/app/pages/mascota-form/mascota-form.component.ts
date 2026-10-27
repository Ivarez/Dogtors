import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MascotaService } from '../../services/mascota.service';
import { DuenoService } from '../../services/dueno.service';
import { Mascota } from '../../models/mascota.model';
import { Dueno } from '../../models/dueno.model';
import { FormFieldComponent } from './components/form-field/form-field.component';

/**
 * Formulario para crear o editar una mascota.
 * Envía el identificador plano del dueño (duenoId) sin anidar objetos en la petición ni respuesta.
 */
@Component({
  selector: 'app-mascota-form',
  imports: [ReactiveFormsModule, RouterLink, FormFieldComponent],
  templateUrl: './mascota-form.component.html',
  styleUrl: './mascota-form.component.scss'
})
export class MascotaFormComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private mascotaService = inject(MascotaService);
  private duenoService = inject(DuenoService);

  isEdit: boolean = false;
  mascotaId: number | undefined;
  duenos: Dueno[] = [];
  guardando: boolean = false;
  errorMsg: string = '';

  mascotaForm = new FormGroup({
    nombre: new FormControl('', [Validators.required, Validators.minLength(2)]),
    especie: new FormControl('Perro', [Validators.required]),
    raza: new FormControl('', [Validators.required]),
    edad: new FormControl<number | null>(null, [Validators.required, Validators.min(0), Validators.max(35)]),
    peso: new FormControl<number | null>(null, [Validators.required, Validators.min(0.1)]),
    enfermedad: new FormControl(''),
    fotoUrl: new FormControl(''),
    dueno: new FormControl<Dueno | null>(null, [Validators.required]),
    activa: new FormControl<boolean>(true)
  });

  ngOnInit(): void {
    this.duenoService.obtenerDuenos().subscribe({
      next: (data) => {
        this.duenos = data;
        const idParam = this.route.snapshot.params['id'];
        if (idParam) {
          this.mascotaId = Number(idParam);
          this.isEdit = true;
          this.cargarMascota(this.mascotaId);
        }
      },
      error: (err) => {
        console.error('Error al cargar dueños:', err);
        this.errorMsg = 'No se pudieron cargar los propietarios desde el servidor.';
      }
    });
  }

  cargarMascota(id: number): void {
    this.mascotaService.obtenerMascotaPorId(id).subscribe({
      next: (m) => {
        const idDueno = m.duenoId ?? m.dueno?.id;
        const duenoEncontrado = idDueno ? this.duenos.find(d => d.id === idDueno) || null : null;
        this.mascotaForm.patchValue({
          nombre: m.nombre,
          especie: m.especie,
          raza: m.raza,
          edad: m.edad,
          peso: m.peso,
          enfermedad: m.enfermedad || '',
          fotoUrl: m.fotoUrl || '',
          dueno: duenoEncontrado,
          activa: m.activa
        });
      },
      error: (err) => {
        console.error('Error al cargar mascota:', err);
        this.errorMsg = 'No se pudo cargar la información de la mascota.';
      }
    });
  }

  compararDuenos(d1: Dueno | null, d2: Dueno | null): boolean {
    return d1 !== null && d2 !== null && d1.id === d2.id;
  }

  handleSubmit(): void {
    if (this.mascotaForm.invalid || this.guardando) {
      this.mascotaForm.markAllAsTouched();
      return;
    }

    this.guardando = true;
    this.errorMsg = '';
    const formVal = this.mascotaForm.value;

    const mascotaData: Mascota = {
      nombre: formVal.nombre || '',
      especie: formVal.especie || 'Perro',
      raza: formVal.raza || '',
      edad: Number(formVal.edad) || 0,
      peso: Number(formVal.peso) || 0,
      enfermedad: formVal.enfermedad || '',
      fotoUrl: formVal.fotoUrl || undefined,
      activa: formVal.activa ?? true,
      duenoId: formVal.dueno?.id
    };

    if (this.isEdit && this.mascotaId) {
      this.mascotaService.actualizarMascota(this.mascotaId, mascotaData).subscribe({
        next: () => {
          this.guardando = false;
          this.router.navigate(['/mascotas']);
        },
        error: (err) => {
          console.error('Error al actualizar mascota:', err);
          this.errorMsg = 'Error al actualizar el expediente de la mascota.';
          this.guardando = false;
        }
      });
    } else {
      this.mascotaService.agregarMascota(mascotaData).subscribe({
        next: () => {
          this.guardando = false;
          this.router.navigate(['/mascotas']);
        },
        error: (err) => {
          console.error('Error al registrar mascota:', err);
          this.errorMsg = 'Error al registrar el paciente en el backend.';
          this.guardando = false;
        }
      });
    }
  }
}
