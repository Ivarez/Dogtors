import { Component, input, output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Veterinario } from '../../../../models/veterinario.model';

/**
 * Componente visual hijo que renderiza la tabla de médicos veterinarios
 * en el Portal del Administrador, incluyendo sus atenciones, estado y acciones CRUD.
 */
@Component({
  selector: 'app-veterinario-table',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './veterinario-table.component.html',
  styleUrl: './veterinario-table.component.scss'
})
export class VeterinarioTableComponent {
  /** Lista de veterinarios filtrada recibida desde el componente padre */
  veterinarios = input.required<Veterinario[]>();

  /** Evento emitido para activar o desactivar un veterinario */
  estadoCambiado = output<number>();

  /** Evento emitido para eliminar un veterinario */
  veterinarioEliminado = output<number>();

  cambiarEstado(id: number | undefined): void {
    if (id !== undefined) {
      this.estadoCambiado.emit(id);
    }
  }

  eliminar(id: number | undefined): void {
    if (id !== undefined) {
      this.veterinarioEliminado.emit(id);
    }
  }
}

