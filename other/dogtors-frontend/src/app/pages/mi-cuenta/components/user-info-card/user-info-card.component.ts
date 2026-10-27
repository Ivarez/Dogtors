import { Component, input } from '@angular/core';
import { UsuarioSesion } from '../../../../services/auth.service';

/**
 * Componente visual que presenta la ficha de información personal
 * y de contacto del usuario en sesión (cédula, correo, celular, nombre).
 */
@Component({
  selector: 'app-user-info-card',
  standalone: true,
  templateUrl: './user-info-card.component.html',
  styleUrl: './user-info-card.component.scss'
})
export class UserInfoCardComponent {
  usuario = input<UsuarioSesion | null>(null);
}
