import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { mergeMap, catchError, of } from 'rxjs';
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
  cargando: boolean = true;
  errorMsg: string = '';

  ngOnInit(): void {
    const id = Number(this.route.snapshot.params['id']);
    this.cargarDetalle(id);
  }

  cargarDetalle(id: number): void {
    this.cargando = true;
    this.errorMsg = '';

    // Patrón de consultas anidadas con mergeMap enseñado en Transcript 10
    this.duenoService.obtenerDuenoPorId(id).pipe(
      mergeMap(d => {
        this.dueno = d;
        return this.mascotaService.obtenerMascotasPorDueno(id).pipe(
          catchError(err => {
            console.warn('El propietario no tiene mascotas asociadas o hubo un problema al cargarlas:', err);
            return of([]);
          })
        );
      })
    ).subscribe({
      next: (pets) => {
        this.mascotas = pets;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar detalle del dueño:', err);
        this.errorMsg = 'No se encontró el propietario o el servidor no respondió.';
        this.cargando = false;
      }
    });
  }

  eliminarDueno(): void {
    if (this.dueno?.id && confirm(`¿Estás seguro de eliminar a ${this.dueno.nombre}? Se eliminarán también las mascotas asociadas a su expediente.`)) {
      this.duenoService.eliminarDueno(this.dueno.id).subscribe({
        next: () => this.router.navigate(['/duenos']),
        error: (err) => console.error('Error al eliminar dueño:', err)
      });
    }
  }
}
