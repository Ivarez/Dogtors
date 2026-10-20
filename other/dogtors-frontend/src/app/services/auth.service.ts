import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, tap } from 'rxjs';
import { Dueno } from '../models/dueno.model';
import { Veterinario } from '../models/veterinario.model';

export interface UsuarioSesion {
  id?: number;
  nombre: string;
  correo: string;
  password?: string;
  especialidad?: string;
  rol?: 'DUENO' | 'VETERINARIO';
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private readonly duenosLoginUrl = 'http://localhost:8080/duenos/login';
  private readonly veterinariosLoginUrl = 'http://localhost:8080/veterinarios/login';

  // Sesión en memoria pura (sin localStorage, sin cookies, como exige el sprint)
  readonly usuarioActual = signal<UsuarioSesion | null>(null);
  readonly veterinarioActual = signal<Veterinario | null>(null);

  /**
   * Login universal: intenta autenticar primero como Veterinario y, si no coincide,
   * intenta autenticar como Dueño/Cliente.
   */
  login(correo: string, password: string): Observable<UsuarioSesion> {
    return this.http.post<Veterinario>(this.veterinariosLoginUrl, { correo, password }).pipe(
      tap(vet => {
        this.veterinarioActual.set(vet);
      }),
      map((vet): UsuarioSesion => ({
        ...vet,
        rol: 'VETERINARIO'
      })),
      tap(usuario => {
        this.usuarioActual.set(usuario);
      }),
      catchError(() =>
        this.http.post<Dueno>(this.duenosLoginUrl, { correo, password }).pipe(
          map((dueno): UsuarioSesion => ({
            ...dueno,
            rol: 'DUENO'
          })),
          tap(usuario => {
            this.usuarioActual.set(usuario);
            this.veterinarioActual.set(null);
          })
        )
      )
    );
  }

  logout(): void {
    this.usuarioActual.set(null);
    this.veterinarioActual.set(null);
  }

  estaAutenticado(): boolean {
    return this.usuarioActual() !== null;
  }

  esVeterinario(): boolean {
    return this.usuarioActual()?.rol === 'VETERINARIO';
  }

  esDueno(): boolean {
    return this.usuarioActual()?.rol === 'DUENO';
  }
}
