import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { mergeMap, catchError, of } from 'rxjs';
import { DuenoService } from '../../services/dueno.service';
import { MascotaService } from '../../services/mascota.service';
import { AuthService } from '../../services/auth.service';
import { Dueno } from '../../models/dueno.model';
import { Mascota } from '../../models/mascota.model';
import { PerfilPetCardComponent } from './components/perfil-pet-card/perfil-pet-card.component';

@Component({
  selector: 'app-perfil-dueno',
  imports: [PerfilPetCardComponent],
  templateUrl: './perfil-dueno.component.html',
  styleUrl: './perfil-dueno.component.scss'
})
export class PerfilDuenoComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private duenoService = inject(DuenoService);
  private mascotaService = inject(MascotaService);
  authService = inject(AuthService);

  dueno: Dueno | undefined;
  mascotas: Mascota[] = [];
  cargando: boolean = true;
  errorMsg: string = '';

  get activasCount(): number {
    return this.mascotas.filter(m => m.activa).length;
  }

  get inactivasCount(): number {
    return this.mascotas.filter(m => !m.activa).length;
  }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.params['id']);
    // Si el usuario es cliente (DUEÑO), no debe poder consultar el perfil de otro dueño
    const usuarioActual = this.authService.usuarioActual();
    if (this.authService.esDueno() && usuarioActual?.id && usuarioActual.id !== id) {
      this.router.navigate(['/perfil', usuarioActual.id]);
      return;
    }
    this.cargarPerfil(id);
  }

  cargarPerfil(id: number): void {
    this.cargando = true;
    this.errorMsg = '';

    // Patrón mergeMap de Transcript 10: primero trae el dueño y luego sus mascotas
    this.duenoService.obtenerDuenoPorId(id).pipe(
      mergeMap(d => {
        this.dueno = d;
        // Si no había sesión activa en memoria, establecerla como dueño para mantener las restricciones de rol
        if (!this.authService.usuarioActual()) {
          this.authService.establecerSesionDueno(d);
        }
        return this.mascotaService.obtenerMascotasPorDueno(id).pipe(
          catchError(err => {
            console.warn('El propietario no registra mascotas asociadas o hubo un error al listarlas:', err);
            return of([]);
          })
        );
      })
    ).subscribe({
      next: (pets) => {
        this.mascotas = pets;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar perfil del dueño:', err);
        this.errorMsg = 'No se pudo cargar el perfil del propietario desde el servidor.';
        this.cargando = false;
      }
    });
  }
}

