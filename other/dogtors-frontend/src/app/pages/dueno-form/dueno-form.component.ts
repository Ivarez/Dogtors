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

  duenoForm = new FormGroup({
    nombre: new FormControl('', [Validators.required, Validators.minLength(3)]),
    correo: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)])
  });

  ngOnInit(): void {
    const idParam = this.route.snapshot.params['id'];
    if (idParam) {
      this.duenoId = Number(idParam);
      this.isEdit = true;
      const duenoExistente = this.duenoService.obtenerDuenoPorId(this.duenoId);
      if (duenoExistente) {
        this.duenoForm.patchValue({
          nombre: duenoExistente.nombre,
          correo: duenoExistente.correo,
          password: duenoExistente.password || ''
        });
      }
    }
  }

  handleSubmit(): void {
    if (this.duenoForm.invalid) {
      this.duenoForm.markAllAsTouched();
      return;
    }

    const formVal = this.duenoForm.value;

    const duenoData: Dueno = {
      nombre: formVal.nombre || '',
      correo: formVal.correo || '',
      password: formVal.password || ''
    };

    if (this.isEdit && this.duenoId) {
      this.duenoService.actualizarDueno(this.duenoId, duenoData);
    } else {
      this.duenoService.agregarDueno(duenoData);
    }

    this.router.navigate(['/duenos']);
  }
}

