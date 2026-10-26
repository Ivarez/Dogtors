import { Component, input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Tratamiento } from '../../../../models/tratamiento.model';

@Component({
  selector: 'app-treatments-history',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './treatments-history.component.html',
  styleUrl: './treatments-history.component.scss'
})
export class TreatmentsHistoryComponent {
  tratamientos = input<Tratamiento[]>([]);
  mascotaId = input<number | undefined>();
  mascotaNombre = input<string | undefined>();
  permitirAsignar = input<boolean>(true);
}
