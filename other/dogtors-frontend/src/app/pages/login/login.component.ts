import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

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
      next: (dueno) => {
        this.cargando = false;
        // Redirigir al perfil del cliente con sus mascotas
        this.router.navigate(['/perfil', dueno.id]);
      },
      error: (err) => {
        console.error('Error al iniciar sesión:', err);
        this.cargando = false;
        if (err.status === 401 || err.status === 400) {
          this.errorMsg = 'Correo o contraseña incorrectos.';
        } else {
          this.errorMsg = 'No se pudo conectar con el servidor de Dogtors (puerto 8080).';
        }
      }
    });
  }
}

