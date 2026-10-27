import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService, UsuarioSesion } from '../../services/auth.service';
import { DuenoService } from '../../services/dueno.service';
import { MascotaService } from '../../services/mascota.service';
import { UserInfoCardComponent } from './components/user-info-card/user-info-card.component';
import { AccountActivityCardComponent } from './components/account-activity-card/account-activity-card.component';

/**
 * Página de perfil y gestión de cuenta personal para usuarios autenticados.
 * Muestra cédula, datos de contacto, tipo de perfil hospitalario y resumen de actividad.
 */
@Component({
  selector: 'app-mi-cuenta',
  standalone: true,
  imports: [RouterLink, UserInfoCardComponent, AccountActivityCardComponent],
  templateUrl: './mi-cuenta.component.html',
  styleUrl: './mi-cuenta.component.scss'
})
export class MiCuentaComponent implements OnInit {
  authService = inject(AuthService);
  private router = inject(Router);
  private duenoService = inject(DuenoService);
  private mascotaService = inject(MascotaService);

  usuario: UsuarioSesion | null = null;
  totalMascotas: number = 0;
  cargando: boolean = true;

  ngOnInit(): void {
    const user = this.authService.usuarioActual();
    if (!user) {
      this.router.navigate(['/login']);
      return;
    }

    this.usuario = user;

    if (user?.id && user.rol === 'DUENO') {
      // Consultar datos actualizados de dueño y conteo de mascotas
      this.duenoService.obtenerDuenoPorId(user.id).subscribe({
        next: (dueno) => {
          if (this.usuario) {
            this.usuario = {
              ...this.usuario,
              cedula: dueno.cedula || this.usuario.cedula,
              celular: dueno.celular || this.usuario.celular,
              correo: dueno.correo || this.usuario.correo,
              nombre: dueno.nombre || this.usuario.nombre
            };
          }
          this.cargando = false;
        },
        error: () => {
          this.cargando = false;
        }
      });

      this.mascotaService.obtenerMascotasPorDueno(user.id).subscribe({
        next: (mascotas) => {
          this.totalMascotas = mascotas.length;
        },
        error: () => {
          this.totalMascotas = 0;
        }
      });
    } else {
      this.cargando = false;
    }
  }

  logout(): void {
    this.authService.logout();
  }
}
