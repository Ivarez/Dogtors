import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { UsuarioSesion } from '../../../../services/auth.service';

/**
 * Componente visual que presenta el estado de la cuenta,
 * resumen de vinculación hospitalaria y accesos rápidos del usuario.
 */
@Component({
  selector: 'app-account-activity-card',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './account-activity-card.component.html',
  styleUrl: './account-activity-card.component.scss'
})
export class AccountActivityCardComponent {
  usuario = input<UsuarioSesion | null>(null);
  totalMascotas = input<number>(0);
}
