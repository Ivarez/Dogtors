import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Mascota } from '../../../../models/mascota.model';

@Component({
  selector: 'app-dueno-mascotas',
  imports: [RouterLink],
  templateUrl: './dueno-mascotas.component.html',
  styleUrl: './dueno-mascotas.component.scss'
})
export class DuenoMascotasComponent {
  mascotas = input<Mascota[]>([]);
}

