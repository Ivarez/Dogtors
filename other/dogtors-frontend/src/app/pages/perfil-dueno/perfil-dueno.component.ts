import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { mergeMap } from 'rxjs';
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
    this.cargarPerfil(id);
  }

  cargarPerfil(id: number): void {
    this.cargando = true;
    this.errorMsg = '';

    // Patrón mergeMap de Transcript 10: primero trae el dueño y luego sus mascotas
    this.duenoService.obtenerDuenoPorId(id).pipe(
      mergeMap(d => {
        this.dueno = d;
        // Si no había sesión activa en memoria, establecerla para el saludo del navbar
        if (!this.authService.usuarioActual()) {
          this.authService.usuarioActual.set(d);
        }
        return this.mascotaService.obtenerMascotasPorDueno(id);
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

