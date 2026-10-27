import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { VeterinarioService } from '../../services/veterinario.service';
import { Veterinario } from '../../models/veterinario.model';
import { FormFieldComponent } from '../mascota-form/components/form-field/form-field.component';

/**
 * Formulario reactivo para registrar o editar un Médico Veterinario desde el Portal del Administrador (Feature 03).
 * Reutiliza el componente visual hijo FormFieldComponent.
 */
@Component({
  selector: 'app-veterinario-form',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, FormFieldComponent],
  templateUrl: './veterinario-form.component.html',
  styleUrl: './veterinario-form.component.scss'
})
export class VeterinarioFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private veterinarioService = inject(VeterinarioService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  veterinarioForm!: FormGroup;
  esEdicion: boolean = false;
  veterinarioId?: number;
  errorMsg: string = '';

  ngOnInit(): void {
    this.veterinarioForm = this.fb.group({
      cedula: ['', [Validators.required]],
      nombre: ['', [Validators.required]],
      especialidad: ['', [Validators.required]],
      correo: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      fotoUrl: [''],
      activo: [true]
    });

    const idParam = this.route.snapshot.params['id'];
    if (idParam) {
      this.esEdicion = true;
      this.veterinarioId = Number(idParam);
      this.cargarVeterinarioParaEditar(this.veterinarioId);
    }
  }

  cargarVeterinarioParaEditar(id: number): void {
    this.veterinarioService.obtenerPorId(id).subscribe({
      next: (vet) => {
        if (vet) {
          this.veterinarioForm.patchValue({
            cedula: vet.cedula,
            nombre: vet.nombre,
            especialidad: vet.especialidad,
            correo: vet.correo,
            password: vet.password || 'vet123',
            fotoUrl: vet.fotoUrl || '',
            activo: vet.activo ?? true
          });
        }
      },
      error: (err) => console.error('Error al cargar veterinario para edición:', err)
    });
  }

  handleSubmit(): void {
    this.errorMsg = '';
    if (this.veterinarioForm.invalid) {
      this.veterinarioForm.markAllAsTouched();
      return;
    }

    const datos: Veterinario = this.veterinarioForm.value;

    if (this.esEdicion && this.veterinarioId) {
      this.veterinarioService.actualizar(this.veterinarioId, datos).subscribe({
        next: () => {
          this.router.navigate(['/admin/veterinarios']);
        },
        error: (err) => {
          console.error('Error al actualizar veterinario:', err);
          this.errorMsg = err?.error?.message || 'Ocurrió un error al actualizar el veterinario.';
        }
      });
    } else {
      this.veterinarioService.crear(datos).subscribe({
        next: () => {
          this.router.navigate(['/admin/veterinarios']);
        },
        error: (err) => {
          console.error('Error al crear veterinario:', err);
          this.errorMsg = err?.error?.message || 'Ocurrió un error al registrar el veterinario.';
        }
      });
    }
  }
}

