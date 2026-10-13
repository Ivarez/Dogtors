import { Component, input } from '@angular/core';
import { Tratamiento } from '../../../../models/tratamiento.model';

@Component({
  selector: 'app-treatments-history',
  imports: [],
  templateUrl: './treatments-history.component.html',
  styleUrl: './treatments-history.component.scss'
})
export class TreatmentsHistoryComponent {
  tratamientos = input<Tratamiento[]>([]);
}
