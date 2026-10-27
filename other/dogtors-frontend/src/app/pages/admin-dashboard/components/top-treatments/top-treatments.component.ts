import { Component, input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MedicamentoCantidad } from '../../../../models/dashboard.model';
import { Droga } from '../../../../models/droga.model';

/**
 * Componente visual hijo que muestra las tablas de:
 * - Top 3 tratamientos/medicamentos con más unidades vendidas
 * - Cantidad de tratamientos por tipo de medicamento administrado en el último mes
 */
@Component({
  selector: 'app-top-treatments',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './top-treatments.component.html',
  styleUrl: './top-treatments.component.scss'
})
export class TopTreatmentsComponent {
  /** Top 3 medicamentos con más unidades vendidas */
  topTratamientos = input.required<Droga[]>();

  /** Desglose de tratamientos administrados en el último mes por tipo de medicamento */
  tratamientosPorMedicamento = input.required<MedicamentoCantidad[]>();
}

