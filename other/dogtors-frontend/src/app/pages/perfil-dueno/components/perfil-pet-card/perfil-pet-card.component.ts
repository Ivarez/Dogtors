import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Mascota } from '../../../../models/mascota.model';

@Component({
  selector: 'app-perfil-pet-card',
  imports: [RouterLink],
  templateUrl: './perfil-pet-card.component.html',
  styleUrl: './perfil-pet-card.component.scss'
})
export class PerfilPetCardComponent {
  mascota = input.required<Mascota>();
}

