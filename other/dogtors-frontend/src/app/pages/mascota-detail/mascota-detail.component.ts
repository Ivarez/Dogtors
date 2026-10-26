import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { mergeMap, catchError, of } from 'rxjs';
import { MascotaService } from '../../services/mascota.service';
import { TratamientoService } from '../../services/tratamiento.service';
import { AuthService } from '../../services/auth.service';
import { Mascota } from '../../models/mascota.model';
import { PetInfoComponent } from './components/pet-info/pet-info.component';
import { TreatmentsHistoryComponent } from './components/treatments-history/treatments-history.component';

@Component({
  selector: 'app-mascota-detail',
  imports: [RouterLink, PetInfoComponent, TreatmentsHistoryComponent],
  templateUrl: './mascota-detail.component.html',
  styleUrl: './mascota-detail.component.scss'
})
export class MascotaDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private mascotaService = inject(MascotaService);
  private tratamientoService = inject(TratamientoService);
  authService = inject(AuthService);

  mascota: Mascota | undefined;
  cargando: boolean = true;
  errorMsg: string = '';

  ngOnInit(): void {
    const id = Number(this.route.snapshot.params['id']);
    this.cargarDetalle(id);
  }

  cargarDetalle(id: number): void {
    this.cargando = true;
    this.errorMsg = '';

    // Patrón mergeMap de consultas anidadas del Transcript 10:
    // Primero obtiene la mascota y luego encadena la consulta de sus tratamientos
    this.mascotaService.obtenerMascotaPorId(id).pipe(
      mergeMap(m => {
        // Si el usuario en sesión es un cliente, solo puede ver sus propias mascotas
        if (this.authService.esDueno()) {
          const duenoIdSesion = this.authService.usuarioActual()?.id;
          if (m.dueno?.id && duenoIdSesion && m.dueno.id !== duenoIdSesion) {
            this.router.navigate(['/perfil', duenoIdSesion]);
            return of([]);
          }
        }
        this.mascota = m;
        return this.tratamientoService.obtenerPorMascota(id).pipe(
          catchError(err => {
            console.warn('No se encontraron tratamientos previos para la mascota:', err);
            return of([]);
          })
        );
      })
    ).subscribe({
      next: (tratamientos) => {
        if (this.mascota) {
          this.mascota.tratamientos = tratamientos;
        }
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar detalle de mascota o tratamientos:', err);
        this.errorMsg = 'No se encontró el paciente o el servidor no respondió.';
        this.cargando = false;
      }
    });
  }

  cambiarEstado(): void {
    if (this.authService.esDueno()) {
      return;
    }
    if (this.mascota && this.mascota.id) {
      this.mascotaService.cambiarEstado(this.mascota.id).subscribe({
        next: () => {
          if (this.mascota?.id) {
            this.cargarDetalle(this.mascota.id);
          }
        },
        error: (err) => console.error('Error al cambiar estado:', err)
      });
    }
  }
}
