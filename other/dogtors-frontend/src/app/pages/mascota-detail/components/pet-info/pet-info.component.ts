import { Component, input, output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Mascota } from '../../../../models/mascota.model';

@Component({
  selector: 'app-pet-info',
  imports: [RouterLink],
  templateUrl: './pet-info.component.html',
  styleUrl: './pet-info.component.scss'
})
export class PetInfoComponent {
  mascota = input.required<Mascota>();
  mostrarAcciones = input<boolean>(true);
  estadoCambiado = output<void>();

  cambiarEstado(): void {
    this.estadoCambiado.emit();
  }
}
