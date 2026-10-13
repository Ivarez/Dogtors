import { Component, inject, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DuenoService } from '../../services/dueno.service';
import { MascotaService } from '../../services/mascota.service';
import { Dueno } from '../../models/dueno.model';
import { DuenoTableComponent } from './components/dueno-table/dueno-table.component';

@Component({
  selector: 'app-dueno-list',
  imports: [RouterLink, DuenoTableComponent],
  templateUrl: './dueno-list.component.html',
  styleUrl: './dueno-list.component.scss'
})
export class DuenoListComponent implements OnInit {
  private duenoService = inject(DuenoService);
  private mascotaService = inject(MascotaService);

  duenos: Dueno[] = [];

  ngOnInit(): void {
    this.cargarDuenos();
  }

  cargarDuenos(): void {
    this.duenos = [...this.duenoService.obtenerDuenos()];
  }

  eliminarDueno(dueno: Dueno): void {
    if (dueno.id && confirm(`¿Estás seguro de eliminar a ${dueno.nombre}?`)) {
      this.mascotaService.quitarDueno(dueno.id);
      this.duenoService.eliminarDueno(dueno.id);
      this.cargarDuenos();
    }
  }
}

