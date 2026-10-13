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

  ngOnInit(): void {
    this.cargarMascotas();
  }

  cargarMascotas(): void {
    this.mascotasActivas = this.mascotaService.obtenerMascotasActivas();
    this.mascotasInactivas = this.mascotaService.obtenerMascotasInactivas();
  }

  cambiarEstado(mascota: Mascota): void {
    if (mascota.id) {
      this.mascotaService.cambiarEstado(mascota.id);
      this.cargarMascotas();
    }
  }
}
