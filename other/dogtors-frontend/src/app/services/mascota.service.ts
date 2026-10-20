import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mascota } from '../models/mascota.model';

@Injectable({
  providedIn: 'root'
})
export class MascotaService {
  private http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/mascotas';

  obtenerMascotas(): Observable<Mascota[]> {
    return this.http.get<Mascota[]>(this.apiUrl);
  }

  obtenerMascotaPorId(id: number): Observable<Mascota> {
    return this.http.get<Mascota>(`${this.apiUrl}/${id}`);
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
