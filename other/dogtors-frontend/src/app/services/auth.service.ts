import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, tap, throwError } from 'rxjs';
import { Dueno } from '../models/dueno.model';
import { Veterinario } from '../models/veterinario.model';
import { Administrador } from '../models/administrador.model';

/**
 * Interfaz que representa al usuario autenticado en el cliente (Dueño, Veterinario o Administrador).
 */
export interface UsuarioSesion {
  id?: number;
  cedula?: string;
  nombre: string;
  correo: string;
  celular?: string;
  password?: string;
  especialidad?: string;
  fotoUrl?: string;
  activo?: boolean;
  rol?: 'DUENO' | 'VETERINARIO' | 'ADMIN';
}

/**
 * Servicio de autenticación y gestión de sesión por roles en el frontend.
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private readonly duenosLoginUrl = 'http://localhost:8080/duenos/login';
  private readonly veterinariosLoginUrl = 'http://localhost:8080/veterinarios/login';
  private readonly administradoresLoginUrl = 'http://localhost:8080/administradores/login';
  private readonly storageKeyUsuario = 'dogtors_usuario_sesion';
  private readonly storageKeyVet = 'dogtors_veterinario_sesion';

  readonly usuarioActual = signal<UsuarioSesion | null>(this.leerUsuarioGuardado());
  readonly veterinarioActual = signal<Veterinario | null>(this.leerVeterinarioGuardado());

  private leerUsuarioGuardado(): UsuarioSesion | null {
    try {
      const raw = sessionStorage.getItem(this.storageKeyUsuario);
      return raw ? (JSON.parse(raw) as UsuarioSesion) : null;
    } catch {
      return null;
    }
  }

  private leerVeterinarioGuardado(): Veterinario | null {
    try {
      const raw = sessionStorage.getItem(this.storageKeyVet);
      return raw ? (JSON.parse(raw) as Veterinario) : null;
    } catch {
      return null;
    }
  }

  private guardarSesion(usuario: UsuarioSesion | null, vet: Veterinario | null): void {
    this.usuarioActual.set(usuario);
    this.veterinarioActual.set(vet);
    try {
      if (usuario) {
        sessionStorage.setItem(this.storageKeyUsuario, JSON.stringify(usuario));
      } else {
        sessionStorage.removeItem(this.storageKeyUsuario);
      }
      if (vet) {
        sessionStorage.setItem(this.storageKeyVet, JSON.stringify(vet));
      } else {
        sessionStorage.removeItem(this.storageKeyVet);
      }
    } catch {
      // Ignorar si sessionStorage no está disponible
    }
  }

  /**
   * Login universal: intenta autenticar primero como Veterinario (respetando el bloqueo si está desactivado),
   * luego como Administrador y finalmente como Dueño/Cliente.
   */
  login(correo: string, password: string): Observable<UsuarioSesion> {
    return this.http.post<Veterinario>(this.veterinariosLoginUrl, { correo, password }).pipe(
      map((vet): { usuario: UsuarioSesion; vet: Veterinario } => ({
        vet,
        usuario: {
          ...vet,
          rol: 'VETERINARIO'
        }
      })),
      tap(({ usuario, vet }) => {
        this.guardarSesion(usuario, vet);
      }),
      map(({ usuario }) => usuario),
      catchError((errVet) => {
        const msgVet: string = errVet?.error?.message || errVet?.error?.error || '';
        // Si el veterinario está desactivado, propagar el error inmediatamente para impedir el login
        if (msgVet.toLowerCase().includes('desactivado') || msgVet.toLowerCase().includes('inactivo')) {
          return throwError(() => errVet);
        }
        return this.http.post<Administrador>(this.administradoresLoginUrl, { correo, password }).pipe(
          map((admin): UsuarioSesion => ({
            ...admin,
            rol: 'ADMIN'
          })),
          tap(usuario => {
            this.guardarSesion(usuario, null);
          }),
          catchError(() =>
            this.http.post<Dueno>(this.duenosLoginUrl, { correo, password }).pipe(
              map((dueno): UsuarioSesion => ({
                ...dueno,
                rol: 'DUENO'
              })),
              tap(usuario => {
                this.guardarSesion(usuario, null);
              })
            )
          )
        );
      })
    );
  }

  establecerSesionDueno(dueno: Dueno): void {
    this.guardarSesion(
      {
        ...dueno,
        rol: 'DUENO'
      },
      null
    );
  }

  logout(): void {
    this.guardarSesion(null, null);
  }

  estaAutenticado(): boolean {
    return this.usuarioActual() !== null;
  }

  esAdmin(): boolean {
    return this.usuarioActual()?.rol === 'ADMIN';
  }

  esVeterinario(): boolean {
    return this.usuarioActual()?.rol === 'VETERINARIO';
  }

  esDueno(): boolean {
    return this.usuarioActual()?.rol === 'DUENO';
  }
}
