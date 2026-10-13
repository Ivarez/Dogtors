import { Component, input, output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Dueno } from '../../../../models/dueno.model';

@Component({
  selector: 'app-dueno-info',
  imports: [RouterLink],
  templateUrl: './dueno-info.component.html',
  styleUrl: './dueno-info.component.scss'
})
export class DuenoInfoComponent {
  dueno = input.required<Dueno>();
  cantidadMascotas = input<number>(0);
  eliminado = output<void>();

  eliminar(): void {
    this.eliminado.emit();
  }
}

