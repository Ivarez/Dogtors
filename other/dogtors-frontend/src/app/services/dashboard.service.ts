import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DashboardKpis, MedicamentoCantidad } from '../models/dashboard.model';
import { Droga } from '../models/droga.model';

/**
 * Servicio HTTP para consultar los indicadores clave (KPIs) del Dashboard de Administrador (Feature 09 - AC29).
 */
@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/dashboard';

  obtenerKpis(): Observable<DashboardKpis> {
    return this.http.get<DashboardKpis>(`${this.apiUrl}/kpis`);
  }

  obtenerTratamientosPorMedicamento(): Observable<MedicamentoCantidad[]> {
    return this.http.get<MedicamentoCantidad[]>(`${this.apiUrl}/tratamientos-por-medicamento`);
  }

  obtenerTop3Tratamientos(): Observable<Droga[]> {
    return this.http.get<Droga[]>(`${this.apiUrl}/top-tratamientos`);
  }
}

