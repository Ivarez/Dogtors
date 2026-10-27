import { Component, input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardKpis } from '../../../../models/dashboard.model';

/**
 * Componente visual hijo que renderiza las tarjetas de indicadores clave (KPIs)
 * del tablero de control administrativo (Feature 09 - AC29).
 */
@Component({
  selector: 'app-kpi-cards',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './kpi-cards.component.html',
  styleUrl: './kpi-cards.component.scss'
})
export class KpiCardsComponent {
  /** Indicadores consolidados recibidos desde el componente padre AdminDashboardComponent */
  kpis = input.required<DashboardKpis>();
}

