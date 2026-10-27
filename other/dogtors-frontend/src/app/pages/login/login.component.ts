import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

/**
 * Componente de inicio de sesión universal para Clientes, Veterinarios y Administradores.
 */
@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  cargando: boolean = false;
  errorMsg: string = '';

  loginForm = new FormGroup({
    correo: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  });

  handleSubmit(): void {
    if (this.loginForm.invalid || this.cargando) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.cargando = true;
    this.errorMsg = '';
    const { correo, password } = this.loginForm.value;

    this.authService.login(correo || '', password || '').subscribe({
      next: (usuario) => {
        this.cargando = false;
        if (usuario.rol === 'ADMIN') {
          this.router.navigate(['/admin/dashboard']);
        } else if (usuario.rol === 'VETERINARIO') {
          this.router.navigate(['/mascotas']);
        } else {
          this.router.navigate(['/perfil', usuario.id]);
        }
      },
      error: (err) => {
        console.error('Error al iniciar sesión:', err);
        this.cargando = false;
        const msgBackend: string = err?.error?.message || err?.error?.error || '';
        if (msgBackend.toLowerCase().includes('desactivado') || msgBackend.toLowerCase().includes('inactivo')) {
          this.errorMsg = msgBackend;
        } else if (err.status === 401 || err.status === 400 || err.status === 404) {
          this.errorMsg = 'Correo o contraseña incorrectos.';
        } else {
          this.errorMsg = 'No se pudo conectar con el servidor de Dogtors (puerto 8080).';
        }
      }
    });
  }
}
