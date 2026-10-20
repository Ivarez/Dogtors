import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
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
  private mascotaService = inject(MascotaService);

  mascota: Mascota | undefined;
  cargando: boolean = true;
  errorMsg: string = '';

  ngOnInit(): void {
    const id = Number(this.route.snapshot.params['id']);
    this.cargarDetalle(id);
  }

  cargarDetalle(id: number): void {
    this.cargando = true;
    this.errorMsg = '';
    this.mascotaService.obtenerMascotaPorId(id).subscribe({
      next: (m) => {
        this.mascota = m;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar mascota:', err);
        this.errorMsg = 'No se encontró el paciente o el servidor no respondió.';
        this.cargando = false;
      }
    });
  }

  cambiarEstado(): void {
    if (this.mascota && this.mascota.id) {
      this.mascotaService.cambiarEstado(this.mascota.id).subscribe({
        next: () => {
          if (this.mascota?.id) {
            this.cargarDetalle(this.mascota.id);
          }
        },
        error: (err) => console.error('Error al cambiar estado:', err)
      });
    }
  }
}
