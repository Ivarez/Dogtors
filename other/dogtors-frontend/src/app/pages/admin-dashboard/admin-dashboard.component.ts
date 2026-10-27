import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { forkJoin } from 'rxjs';
import { DashboardService } from '../../services/dashboard.service';
import { DashboardKpis, MedicamentoCantidad } from '../../models/dashboard.model';
import { Droga } from '../../models/droga.model';
import { KpiCardsComponent } from './components/kpi-cards/kpi-cards.component';
import { TopTreatmentsComponent } from './components/top-treatments/top-treatments.component';

/**
 * Página principal del Portal del Administrador (Feature 09 - Tablero de Control / Dashboard).
 * Divide la interfaz en componentes visuales hijos (KpiCardsComponent y TopTreatmentsComponent)
 * y consume el servicio DashboardService mediante forkJoin sin devolver objetos anidados.
 */
@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, KpiCardsComponent, TopTreatmentsComponent],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.scss'
})
export class AdminDashboardComponent implements OnInit {
  private dashboardService = inject(DashboardService);

  kpis: DashboardKpis = {
    tratamientosUltimoMes: 0,
    veterinariosActivos: 0,
    veterinariosInactivos: 0,
    mascotasTotales: 0,
    mascotasActivas: 0,
    ventasTotales: 0,
    gananciasTotales: 0
  };

  tratamientosPorMedicamento: MedicamentoCantidad[] = [];
  top3Tratamientos: Droga[] = [];

  cargando: boolean = true;
  errorMsg: string = '';

  ngOnInit(): void {
    this.cargarDashboard();
  }

  /**
   * Consulta en paralelo los tres endpoints planos del Dashboard administrativo.
   */
  cargarDashboard(): void {
    this.cargando = true;
    this.errorMsg = '';

    forkJoin({
      kpis: this.dashboardService.obtenerKpis(),
      porMedicamento: this.dashboardService.obtenerTratamientosPorMedicamento(),
      top3: this.dashboardService.obtenerTop3Tratamientos()
    }).subscribe({
      next: ({ kpis, porMedicamento, top3 }) => {
        this.kpis = kpis;
        this.tratamientosPorMedicamento = porMedicamento;
        this.top3Tratamientos = top3;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar los indicadores del dashboard:', err);
        this.errorMsg = 'No se pudo cargar la información del tablero de control.';
        this.cargando = false;
      }
    });
  }
}

