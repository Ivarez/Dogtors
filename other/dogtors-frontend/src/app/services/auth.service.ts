import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Dueno } from '../models/dueno.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/duenos/login';

  // Sesión en memoria pura (sin localStorage, sin cookies, como exige el sprint)
  readonly usuarioActual = signal<Dueno | null>(null);

  login(correo: string, password: string): Observable<Dueno> {
    return this.http.post<Dueno>(this.apiUrl, { correo, password }).pipe(
      tap(dueno => {
        this.usuarioActual.set(dueno);
      })
    );
  }

  logout(): void {
    this.usuarioActual.set(null);
  }

  estaAutenticado(): boolean {
    return this.usuarioActual() !== null;
  }
}

