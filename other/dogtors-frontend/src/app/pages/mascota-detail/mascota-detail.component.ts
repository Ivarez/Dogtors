import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MascotaService } from '../../services/mascota.service';
import { Mascota } from '../../models/mascota.model';
import { PetInfoComponent } from './components/pet-info/pet-info.component';
import { TreatmentsHistoryComponent } from './components/treatments-history/treatments-history.component';

@Component({
  selector: 'app-mascota-detail',
  imports: [RouterLink, PetInfoComponent, TreatmentsHistoryComponent],
  templateUrl: './mascota-detail.component.html',
  styleUrl: './mascota-detail.component.scss'
})
export class MascotaDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private mascotaService = inject(MascotaService);

  mascota: Mascota | undefined;

  ngOnInit(): void {
    const id = Number(this.route.snapshot.params['id']);
    this.mascota = this.mascotaService.obtenerMascotaPorId(id);
  }

  cambiarEstado(): void {
    if (this.mascota && this.mascota.id) {
      this.mascotaService.cambiarEstado(this.mascota.id);
      this.mascota = this.mascotaService.obtenerMascotaPorId(this.mascota.id);
    }
  }
}
