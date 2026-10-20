import { Component, inject, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DuenoService } from '../../services/dueno.service';
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

  duenos: Dueno[] = [];
  cargando: boolean = true;
  errorMsg: string = '';

  ngOnInit(): void {
    this.cargarDuenos();
  }

  cargarDuenos(): void {
    this.cargando = true;
    this.errorMsg = '';
    this.duenoService.obtenerDuenos().subscribe({
      next: (data) => {
        this.duenos = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar dueños:', err);
        this.errorMsg = 'No se pudo conectar con el backend de Spring Boot (puerto 8080).';
        this.cargando = false;
      }
    });
  }

  eliminarDueno(dueno: Dueno): void {
    if (dueno.id && confirm(`¿Estás seguro de eliminar a ${dueno.nombre}? Al eliminar el propietario se eliminarán también sus pacientes asociados.`)) {
      this.duenoService.eliminarDueno(dueno.id).subscribe({
        next: () => this.cargarDuenos(),
        error: (err) => console.error('Error al eliminar dueño:', err)
      });
    }
  }
}
