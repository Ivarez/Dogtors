import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Veterinario } from '../models/veterinario.model';
import { Mascota } from '../models/mascota.model';

/**
 * Servicio HTTP para el CRUD de médicos veterinarios (portal administrador),
 * búsqueda por cédula/nombre, cambio de estado y consulta de mascotas tratadas (portal veterinario).
 */
@Injectable({
  providedIn: 'root'
})
export class VeterinarioService {
  private http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/veterinarios';

  obtenerTodos(): Observable<Veterinario[]> {
    return this.http.get<Veterinario[]>(this.apiUrl);
  }

  buscar(termino: string): Observable<Veterinario[]> {
    const params = new HttpParams().set('q', termino);
    return this.http.get<Veterinario[]>(`${this.apiUrl}/buscar`, { params });
  }

  obtenerPorId(id: number): Observable<Veterinario> {
    return this.http.get<Veterinario>(`${this.apiUrl}/${id}`);
  }

  obtenerPorCedula(cedula: string): Observable<Veterinario> {
    return this.http.get<Veterinario>(`${this.apiUrl}/cedula/${cedula}`);
  }

  obtenerMascotasTratadas(veterinarioId: number): Observable<Mascota[]> {
    return this.http.get<Mascota[]>(`${this.apiUrl}/${veterinarioId}/mascotas`);
  }

  crear(veterinario: Veterinario): Observable<Veterinario> {
    return this.http.post<Veterinario>(this.apiUrl, veterinario);
  }

  actualizar(id: number, veterinario: Veterinario): Observable<Veterinario> {
    return this.http.put<Veterinario>(this.apiUrl, { ...veterinario, id });
  }

  cambiarEstado(id: number): Observable<Veterinario> {
    return this.http.put<Veterinario>(`${this.apiUrl}/${id}/estado`, {});
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
