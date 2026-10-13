import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MascotaService } from '../../services/mascota.service';
import { Mascota } from '../../models/mascota.model';
import { Dueno } from '../../models/dueno.model';
import { FormFieldComponent } from './components/form-field/form-field.component';

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

  isEdit: boolean = false;
  mascotaId: number | undefined;
  duenos: Dueno[] = [];

  mascotaForm = new FormGroup({
    nombre: new FormControl('', [Validators.required, Validators.minLength(2)]),
    especie: new FormControl('Perro', [Validators.required]),
    raza: new FormControl('', [Validators.required]),
    edad: new FormControl<number | null>(null, [Validators.required, Validators.min(0), Validators.max(35)]),
    peso: new FormControl<number | null>(null, [Validators.required, Validators.min(0.1)]),
    enfermedad: new FormControl('', [Validators.required]),
    fotoUrl: new FormControl(''),
    dueno: new FormControl<Dueno | null>(null, [Validators.required]),
    activa: new FormControl<boolean>(true)
  });

  ngOnInit(): void {
    this.duenos = this.mascotaService.obtenerDuenos();
    const idParam = this.route.snapshot.params['id'];
    if (idParam) {
      this.mascotaId = Number(idParam);
      this.isEdit = true;
      const mascotaExistente = this.mascotaService.obtenerMascotaPorId(this.mascotaId);
      if (mascotaExistente) {
        this.mascotaForm.patchValue({
          nombre: mascotaExistente.nombre,
          especie: mascotaExistente.especie,
          raza: mascotaExistente.raza,
          edad: mascotaExistente.edad,
          peso: mascotaExistente.peso,
          enfermedad: mascotaExistente.enfermedad,
          fotoUrl: mascotaExistente.fotoUrl || '',
          dueno: mascotaExistente.dueno || null,
          activa: mascotaExistente.activa
        });
      }
    }
  }

  compararDuenos(d1: Dueno | null, d2: Dueno | null): boolean {
    return d1 !== null && d2 !== null && d1.id === d2.id;
  }

  handleSubmit(): void {
    if (this.mascotaForm.invalid) {
      this.mascotaForm.markAllAsTouched();
      return;
    }

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
      dueno: formVal.dueno || undefined
    };

    if (this.isEdit && this.mascotaId) {
      this.mascotaService.actualizarMascota(this.mascotaId, mascotaData);
    } else {
      this.mascotaService.agregarMascota(mascotaData);
    }

    this.router.navigate(['/mascotas']);
  }
}
