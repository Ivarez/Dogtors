import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Dueno } from '../models/dueno.model';

/**
 * Servicio HTTP para la gestión de propietarios (dueños de mascotas).
 */
@Injectable({
  providedIn: 'root'
})
export class DuenoService {
  private http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/duenos';

  obtenerDuenos(): Observable<Dueno[]> {
    return this.http.get<Dueno[]>(this.apiUrl);
  }

  obtenerDuenoPorId(id: number): Observable<Dueno> {
    return this.http.get<Dueno>(`${this.apiUrl}/${id}`);
  }

  obtenerDuenoPorCedula(cedula: string): Observable<Dueno> {
    return this.http.get<Dueno>(`${this.apiUrl}/cedula/${cedula}`);
  }

  agregarDueno(dueno: Dueno): Observable<Dueno> {
    return this.http.post<Dueno>(this.apiUrl, dueno);
  }

  actualizarDueno(id: number, dueno: Dueno): Observable<void> {
    return this.http.put<void>(this.apiUrl, { ...dueno, id });
  }

  eliminarDueno(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
