import { Component, input, output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Mascota } from '../../../../models/mascota.model';

@Component({
  selector: 'app-mascota-table',
  imports: [RouterLink],
  templateUrl: './mascota-table.component.html',
  styleUrl: './mascota-table.component.scss'
})
export class MascotaTableComponent {
  mascotaArray = input<Mascota[]>([]);
  esActivas = input<boolean>(true);
  mascotaEstadoCambiado = output<Mascota>();

  cambiarEstado(mascota: Mascota): void {
    this.mascotaEstadoCambiado.emit(mascota);
  }
}
