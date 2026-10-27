import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { VeterinarioService } from '../../services/veterinario.service';
import { Veterinario } from '../../models/veterinario.model';
import { VeterinarioTableComponent } from './components/veterinario-table/veterinario-table.component';

/**
 * Página del Portal del Administrador para la gestión CRUD de Veterinarios (Feature 03).
 * Incluye barra de búsqueda por cédula, nombre o especialidad, activación/desactivación
 * y eliminación preservando los tratamientos históricos.
 */
@Component({
  selector: 'app-veterinario-list',
  standalone: true,
  imports: [FormsModule, RouterLink, VeterinarioTableComponent],
  templateUrl: './veterinario-list.component.html',
  styleUrl: './veterinario-list.component.scss'
})
export class VeterinarioListComponent implements OnInit {
  private veterinarioService = inject(VeterinarioService);

  veterinarios: Veterinario[] = [];
  filtroBusqueda: string = '';

  get veterinariosFiltrados(): Veterinario[] {
    const q = this.filtroBusqueda.trim().toLowerCase();
    if (!q) {
      return this.veterinarios;
    }
    return this.veterinarios.filter(v =>
      (v.cedula || '').toLowerCase().includes(q) ||
      v.nombre.toLowerCase().includes(q) ||
      v.especialidad.toLowerCase().includes(q)
    );
  }

  get totalCount(): number {
    return this.veterinarios.length;
  }

  get activosCount(): number {
    return this.veterinarios.filter(v => v.activo !== false).length;
  }

  get inactivosCount(): number {
    return this.veterinarios.filter(v => v.activo === false).length;
  }

  ngOnInit(): void {
    this.cargarVeterinarios();
  }

  cargarVeterinarios(): void {
    this.veterinarioService.obtenerTodos().subscribe({
      next: (data) => {
        this.veterinarios = data;
      },
      error: (err) => console.error('Error al cargar veterinarios:', err)
    });
  }

  onEstadoCambiado(id: number): void {
    this.veterinarioService.cambiarEstado(id).subscribe({
      next: () => this.cargarVeterinarios(),
      error: (err) => console.error('Error al cambiar estado del veterinario:', err)
    });
  }

  onVeterinarioEliminado(id: number): void {
    if (confirm('¿Está seguro de eliminar este veterinario? Sus tratamientos suministrados se conservarán en el historial clínico.')) {
      this.veterinarioService.eliminar(id).subscribe({
        next: () => this.cargarVeterinarios(),
        error: (err) => console.error('Error al eliminar veterinario:', err)
      });
    }
  }
}

