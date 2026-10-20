import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DuenoService } from '../../services/dueno.service';
import { Dueno } from '../../models/dueno.model';
import { FormFieldComponent } from '../mascota-form/components/form-field/form-field.component';

@Component({
  selector: 'app-dueno-form',
  imports: [ReactiveFormsModule, RouterLink, FormFieldComponent],
  templateUrl: './dueno-form.component.html',
  styleUrl: './dueno-form.component.scss'
})
export class DuenoFormComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private duenoService = inject(DuenoService);

  isEdit: boolean = false;
  duenoId: number | undefined;
  guardando: boolean = false;
  errorMsg: string = '';

  duenoForm = new FormGroup({
    nombre: new FormControl('', [Validators.required, Validators.minLength(3)]),
    correo: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(4)])
  });

  ngOnInit(): void {
    const idParam = this.route.snapshot.params['id'];
    if (idParam) {
      this.duenoId = Number(idParam);
      this.isEdit = true;
      this.duenoService.obtenerDuenoPorId(this.duenoId).subscribe({
        next: (duenoExistente) => {
          this.duenoForm.patchValue({
            nombre: duenoExistente.nombre,
            correo: duenoExistente.correo,
            password: duenoExistente.password || ''
          });
        },
        error: (err) => {
          console.error('Error al cargar dueño:', err);
          this.errorMsg = 'No se encontró el propietario solicitado.';
        }
      });
    }
  }

  handleSubmit(): void {
    if (this.duenoForm.invalid || this.guardando) {
      this.duenoForm.markAllAsTouched();
      return;
    }

    this.guardando = true;
    this.errorMsg = '';
    const formVal = this.duenoForm.value;

    const duenoData: Dueno = {
      nombre: formVal.nombre || '',
      correo: formVal.correo || '',
      password: formVal.password || ''
    };

    if (this.isEdit && this.duenoId) {
      this.duenoService.actualizarDueno(this.duenoId, duenoData).subscribe({
        next: () => {
          this.guardando = false;
          this.router.navigate(['/duenos']);
        },
        error: (err) => {
          console.error('Error al actualizar dueño:', err);
          this.errorMsg = 'Error al actualizar el propietario.';
          this.guardando = false;
        }
      });
    } else {
      this.duenoService.agregarDueno(duenoData).subscribe({
        next: () => {
          this.guardando = false;
          this.router.navigate(['/duenos']);
        },
        error: (err) => {
          console.error('Error al registrar dueño:', err);
          this.errorMsg = err.error?.message || 'Error al registrar el propietario en el backend.';
          this.guardando = false;
        }
      });
    }
  }
}
