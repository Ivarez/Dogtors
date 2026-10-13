import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { DuenoService } from '../../services/dueno.service';
import { MascotaService } from '../../services/mascota.service';
import { Dueno } from '../../models/dueno.model';
import { Mascota } from '../../models/mascota.model';
import { DuenoInfoComponent } from './components/dueno-info/dueno-info.component';
import { DuenoMascotasComponent } from './components/dueno-mascotas/dueno-mascotas.component';

@Component({
  selector: 'app-dueno-detail',
  imports: [RouterLink, DuenoInfoComponent, DuenoMascotasComponent],
  templateUrl: './dueno-detail.component.html',
  styleUrl: './dueno-detail.component.scss'
})
export class DuenoDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private duenoService = inject(DuenoService);
  private mascotaService = inject(MascotaService);

  dueno: Dueno | undefined;
  mascotas: Mascota[] = [];

  ngOnInit(): void {
    const id = Number(this.route.snapshot.params['id']);
    this.dueno = this.duenoService.obtenerDuenoPorId(id);
    if (this.dueno) {
      this.mascotas = this.mascotaService.obtenerMascotasPorDueno(id);
    }
  }

  eliminarDueno(): void {
    if (this.dueno?.id && confirm(`¿Estás seguro de eliminar a ${this.dueno.nombre}?`)) {
      this.mascotaService.quitarDueno(this.dueno.id);
      this.duenoService.eliminarDueno(this.dueno.id);
      this.router.navigate(['/duenos']);
    }
  }
}

