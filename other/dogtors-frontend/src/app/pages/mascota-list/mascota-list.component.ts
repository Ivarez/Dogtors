import { Component, inject, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MascotaService } from '../../services/mascota.service';
import { Mascota } from '../../models/mascota.model';
import { MascotaTableComponent } from './components/mascota-table/mascota-table.component';

@Component({
  selector: 'app-mascota-list',
  imports: [RouterLink, MascotaTableComponent],
  templateUrl: './mascota-list.component.html',
  styleUrl: './mascota-list.component.scss'
})
export class MascotaListComponent implements OnInit {
  private mascotaService = inject(MascotaService);

  mascotasActivas: Mascota[] = [];
  mascotasInactivas: Mascota[] = [];
  cargando: boolean = true;
  errorMsg: string = '';

  ngOnInit(): void {
    this.cargarMascotas();
  }

  cargarMascotas(): void {
    this.cargando = true;
    this.errorMsg = '';
    this.mascotaService.obtenerMascotas().subscribe({
      next: (todas) => {
        this.mascotasActivas = todas.filter(m => m.activa);
        this.mascotasInactivas = todas.filter(m => !m.activa);
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar mascotas:', err);
        this.errorMsg = 'No se pudo conectar con el backend de Spring Boot (puerto 8080).';
        this.cargando = false;
      }
    });
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
