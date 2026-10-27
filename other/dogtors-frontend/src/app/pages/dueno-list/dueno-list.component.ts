import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { DuenoService } from '../../services/dueno.service';
import { Dueno } from '../../models/dueno.model';
import { DuenoTableComponent } from './components/dueno-table/dueno-table.component';

/**
 * Página de listado y búsqueda de propietarios (clientes) por cédula, nombre o correo.
 */
@Component({
  selector: 'app-dueno-list',
  imports: [RouterLink, FormsModule, DuenoTableComponent],
  templateUrl: './dueno-list.component.html',
  styleUrl: './dueno-list.component.scss'
})
export class DuenoListComponent implements OnInit {
  private duenoService = inject(DuenoService);

  duenos: Dueno[] = [];
  filtroBusqueda: string = '';
  cargando: boolean = true;
  errorMsg: string = '';

  ngOnInit(): void {
    this.cargarDuenos();
  }

  get duenosFiltrados(): Dueno[] {
    const q = this.filtroBusqueda.trim().toLowerCase();
    if (!q) {
      return this.duenos;
    }
    return this.duenos.filter(d =>
      (d.cedula || '').toLowerCase().includes(q) ||
      (d.nombre || '').toLowerCase().includes(q) ||
      (d.correo || '').toLowerCase().includes(q) ||
      (d.celular || '').toLowerCase().includes(q)
    );
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
    if (dueno.id && confirm(`¿Estás seguro de eliminar a ${dueno.nombre}? Al eliminar el propietario se eliminarán también sus mascotas asociadas.`)) {
      this.duenoService.eliminarDueno(dueno.id).subscribe({
        next: () => this.cargarDuenos(),
        error: (err) => console.error('Error al eliminar dueño:', err)
      });
    }
  }
}
