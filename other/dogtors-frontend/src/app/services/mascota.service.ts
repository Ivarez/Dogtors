import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mascota } from '../models/mascota.model';
import { Dueno } from '../models/dueno.model';

/**
 * Servicio HTTP para la gestión de mascotas (pacientes) y búsqueda por nombre.
 */
@Injectable({
  providedIn: 'root'
})
export class MascotaService {
  private http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/mascotas';

  obtenerMascotas(): Observable<Mascota[]> {
    return this.http.get<Mascota[]>(this.apiUrl);
  }

  buscarPorNombre(nombre: string): Observable<Mascota[]> {
    const params = new HttpParams().set('nombre', nombre);
    return this.http.get<Mascota[]>(`${this.apiUrl}/buscar`, { params });
  }

  obtenerMascotaPorId(id: number): Observable<Mascota> {
    return this.http.get<Mascota>(`${this.apiUrl}/${id}`);
  }

  obtenerDuenoDeMascota(id: number): Observable<Dueno> {
    return this.http.get<Dueno>(`${this.apiUrl}/${id}/dueno`);
  }

  obtenerMascotasPorDueno(duenoId: number): Observable<Mascota[]> {
    return this.http.get<Mascota[]>(`${this.apiUrl}/dueno/${duenoId}`);
  }

  agregarMascota(mascota: Mascota): Observable<void> {
    return this.http.post<void>(this.apiUrl, mascota);
  }

  actualizarMascota(id: number, mascota: Mascota): Observable<void> {
    return this.http.put<void>(this.apiUrl, { ...mascota, id });
  }

  cambiarEstado(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}/estado`, {});
  }
}
