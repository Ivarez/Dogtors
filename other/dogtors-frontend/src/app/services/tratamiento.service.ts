import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tratamiento } from '../models/tratamiento.model';

export interface CrearTratamientoDTO {
  mascotaId: number;
  veterinarioId: number;
  drogaId: number;
  observaciones?: string;
  fecha?: string;
}

@Injectable({
  providedIn: 'root'
})
export class TratamientoService {
  private http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/tratamientos';

  obtenerTodos(): Observable<Tratamiento[]> {
    return this.http.get<Tratamiento[]>(this.apiUrl);
  }

  obtenerPorMascota(mascotaId: number): Observable<Tratamiento[]> {
    return this.http.get<Tratamiento[]>(`${this.apiUrl}/mascota/${mascotaId}`);
  }

  obtenerPorId(id: number): Observable<Tratamiento> {
    return this.http.get<Tratamiento>(`${this.apiUrl}/${id}`);
  }

  crearTratamiento(dto: CrearTratamientoDTO): Observable<Tratamiento> {
    return this.http.post<Tratamiento>(this.apiUrl, dto);
  }
}

