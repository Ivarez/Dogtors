import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  selector: 'app-error-page',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './error-page.component.html',
  styleUrl: './error-page.component.scss'
})
export class ErrorPageComponent {
  private route = inject(ActivatedRoute);

  codigo: string = '404 / 500';
  protocolo: string = 'ERR_NO_DRUGS_FOUND';
  titulo: string = 'Medicamentos o Insumos no Encontrados';
  mensaje: string = 'No se encontraron medicamentos activos o insumos disponibles en el depósito farmacéutico para completar la operación clínica solicitada.';
  sugerencia: string = 'Verifique si el catálogo de medicamentos fue importado correctamente desde el archivo Excel institucional o comuníquese con farmacia central.';

  ngOnInit(): void {
    const pCodigo = this.route.snapshot.queryParams['codigo'];
    const pTipo = this.route.snapshot.queryParams['tipo'];

    if (pTipo === 'sin-drogas') {
      this.codigo = 'DEPÓSITO VACÍO';
      this.protocolo = 'ERR_FARMACIA_SIN_STOCK';
      this.titulo = 'Catálogo de Medicamentos Agotado o Inexistente';
      this.mensaje = 'El hospital no dispone actualmente de fármacos con unidades en existencia en bodega. Es mandatorio cargar el archivo Excel con las dosis y suministros oficiales.';
      this.sugerencia = 'Acceda al módulo de importación Excel para restaurar o actualizar las existencias de medicamentos de la clínica.';
    } else if (pCodigo) {
      this.codigo = pCodigo;
    }
  }

  copiarIncidente(): void {
    navigator.clipboard?.writeText('INC-' + Date.now());
    alert('Identificador de incidente clínico copiado al portapapeles.');
  }
}
