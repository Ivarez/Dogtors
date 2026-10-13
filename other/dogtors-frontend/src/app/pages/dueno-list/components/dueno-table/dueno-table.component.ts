import { Component, input, output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Dueno } from '../../../../models/dueno.model';

@Component({
  selector: 'app-dueno-table',
  imports: [RouterLink],
  templateUrl: './dueno-table.component.html',
  styleUrl: './dueno-table.component.scss'
})
export class DuenoTableComponent {
  duenoArray = input<Dueno[]>([]);
  duenoEliminado = output<Dueno>();

  eliminar(dueno: Dueno): void {
    this.duenoEliminado.emit(dueno);
  }
}

