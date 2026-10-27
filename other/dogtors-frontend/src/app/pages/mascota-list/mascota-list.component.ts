import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MascotaService } from '../../services/mascota.service';
import { VeterinarioService } from '../../services/veterinario.service';
import { AuthService } from '../../services/auth.service';
import { Mascota } from '../../models/mascota.model';
import { MascotaTableComponent } from './components/mascota-table/mascota-table.component';

/**
 * Directorio de pacientes (mascotas) del portal veterinario.
 * Incluye barra de búsqueda por nombre de mascota (AC21) y filtro para ver
 * únicamente las mascotas tratadas anteriormente por el veterinario en sesión (AC32).
 */
@Component({
  selector: 'app-mascota-list',
  imports: [RouterLink, FormsModule, MascotaTableComponent],
  templateUrl: './mascota-list.component.html',
  styleUrl: './mascota-list.component.scss'
})
export class MascotaListComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private mascotaService = inject(MascotaService);
  private veterinarioService = inject(VeterinarioService);
  authService = inject(AuthService);

  todasLasMascotas: Mascota[] = [];
  filtroNombre: string = '';
  soloMisTratadas: boolean = false;
  cargando: boolean = true;
  errorMsg: string = '';

  ngOnInit(): void {
    // Si entra por la ruta /mis-pacientes o con queryParam ?tratadas=true, activar el filtro del veterinario
    const rutaActual = this.route.snapshot.routeConfig?.path || '';
    const qTratadas = this.route.snapshot.queryParams['tratadas'];
    if (rutaActual === 'mis-pacientes' || qTratadas === 'true') {
      this.soloMisTratadas = true;
    }
    this.cargarMascotas();
  }

  get mascotasFiltradas(): Mascota[] {
    const q = this.filtroNombre.trim().toLowerCase();
    if (!q) {
      return this.todasLasMascotas;
    }
    return this.todasLasMascotas.filter(m =>
      (m.nombre || '').toLowerCase().includes(q) ||
      (m.raza || '').toLowerCase().includes(q) ||
      (m.duenoNombre || '').toLowerCase().includes(q)
    );
  }

  get mascotasActivas(): Mascota[] {
    return this.mascotasFiltradas.filter(m => m.activa);
  }

  get mascotasInactivas(): Mascota[] {
    return this.mascotasFiltradas.filter(m => !m.activa);
  }

  alternarFiltroVeterinario(soloTratadas: boolean): void {
    if (this.soloMisTratadas === soloTratadas) {
      return;
    }
    this.soloMisTratadas = soloTratadas;
    this.cargarMascotas();
  }

  cargarMascotas(): void {
    this.cargando = true;
    this.errorMsg = '';

    const vetId = this.authService.veterinarioActual()?.id || this.authService.usuarioActual()?.id;
    if (this.soloMisTratadas && vetId) {
      this.veterinarioService.obtenerMascotasTratadas(vetId).subscribe({
        next: (tratadas) => {
          this.todasLasMascotas = tratadas;
          this.cargando = false;
        },
        error: (err) => {
          console.error('Error al cargar mascotas tratadas por el veterinario:', err);
          this.errorMsg = 'No se pudieron consultar las mascotas tratadas por el médico veterinario.';
          this.cargando = false;
        }
      });
    } else {
      this.mascotaService.obtenerMascotas().subscribe({
        next: (todas) => {
          this.todasLasMascotas = todas;
          this.cargando = false;
        },
        error: (err) => {
          console.error('Error al cargar mascotas:', err);
          this.errorMsg = 'No se pudo conectar con el backend de Spring Boot (puerto 8080).';
          this.cargando = false;
        }
      });
    }
  }

  cambiarEstado(mascota: Mascota): void {
    if (mascota.id) {
      this.mascotaService.cambiarEstado(mascota.id).subscribe({
        next: () => this.cargarMascotas(),
        error: (err) => console.error('Error al cambiar estado:', err)
      });
    }
  }
}
