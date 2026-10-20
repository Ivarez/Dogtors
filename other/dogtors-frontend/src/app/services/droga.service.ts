import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Droga } from '../models/droga.model';

@Injectable({
  providedIn: 'root'
})
export class DrogaService {
  private http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/drogas';

  obtenerTodas(): Observable<Droga[]> {
    return this.http.get<Droga[]>(this.apiUrl);
  }

  obtenerDisponibles(): Observable<Droga[]> {
    return this.http.get<Droga[]>(`${this.apiUrl}/disponibles`);
  }

  obtenerPorId(id: number): Observable<Droga> {
    return this.http.get<Droga>(`${this.apiUrl}/${id}`);
  }

  cargarExcel(archivo: File, sobrescribir: boolean = false): Observable<{ status: number; mensaje: string; medicamentosCargados: number }> {
    const formData = new FormData();
    formData.append('archivo', archivo);
    return this.http.post<{ status: number; mensaje: string; medicamentosCargados: number }>(
      `${this.apiUrl}/cargar-excel?sobrescribir=${sobrescribir}`,
      formData
    );
  }

  cargarExcelDefault(): Observable<{ status: number; mensaje: string; medicamentosCargados: number }> {
    return this.http.post<{ status: number; mensaje: string; medicamentosCargados: number }>(`${this.apiUrl}/cargar-excel-default`, {});
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  limpiarCatalogo(): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/limpiar`);
  }
}
