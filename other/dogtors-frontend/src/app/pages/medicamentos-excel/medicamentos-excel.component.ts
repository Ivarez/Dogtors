import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { DrogaService } from '../../services/droga.service';
import { Droga } from '../../models/droga.model';

@Component({
  selector: 'app-medicamentos-excel',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './medicamentos-excel.component.html',
  styleUrl: './medicamentos-excel.component.scss'
})
export class MedicamentosExcelComponent implements OnInit {
  private drogaService = inject(DrogaService);

  medicamentos: Droga[] = [];
  archivoSeleccionado: File | null = null;
  nombreArchivo: string = '';
  tamanoArchivo: string = '';
  estaArrastrando: boolean = false;
  sobrescribirCatalogo: boolean = false;

  cargandoLista: boolean = false;
  subiendoExcel: boolean = false;
  
  mensajeExito: string = '';
  mensajeError: string = '';
  medicamentosCargadosContador: number | null = null;

  filtroBusqueda: string = '';

  ngOnInit(): void {
    this.cargarMedicamentos();
  }

  cargarMedicamentos(): void {
    this.cargandoLista = true;
    this.drogaService.obtenerTodas().subscribe({
      next: (data) => {
        this.medicamentos = data;
        this.cargandoLista = false;
      },
      error: (err) => {
        console.error('Error al listar medicamentos:', err);
        this.mensajeError = 'No se pudo conectar con el servidor para cargar el inventario de medicamentos.';
        this.cargandoLista = false;
      }
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.procesarArchivo(input.files[0]);
    }
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    this.estaArrastrando = true;
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    this.estaArrastrando = false;
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    this.estaArrastrando = false;

    if (event.dataTransfer && event.dataTransfer.files.length > 0) {
      this.procesarArchivo(event.dataTransfer.files[0]);
    }
  }

  procesarArchivo(archivo: File): void {
    this.mensajeError = '';
    this.mensajeExito = '';

    const extension = archivo.name.split('.').pop()?.toLowerCase();
    if (extension !== 'xlsx' && extension !== 'xls') {
      this.mensajeError = 'Formato inválido. Por favor seleccione un archivo Excel (.xlsx o .xls).';
      this.archivoSeleccionado = null;
      this.nombreArchivo = '';
      this.tamanoArchivo = '';
      return;
    }

    this.archivoSeleccionado = archivo;
    this.nombreArchivo = archivo.name;
    this.tamanoArchivo = (archivo.size / 1024).toFixed(1) + ' KB';
  }

  subirArchivo(): void {
    if (!this.archivoSeleccionado) {
      this.mensajeError = 'Por favor seleccione o arrastre un archivo Excel antes de continuar.';
      return;
    }

    this.subiendoExcel = true;
    this.mensajeError = '';
    this.mensajeExito = '';

    this.drogaService.cargarExcel(this.archivoSeleccionado, this.sobrescribirCatalogo).subscribe({
      next: (resp) => {
        this.subiendoExcel = false;
        this.mensajeExito = resp.mensaje || '¡Archivo Excel cargado y procesado exitosamente!';
        this.medicamentosCargadosContador = resp.medicamentosCargados;
        this.archivoSeleccionado = null;
        this.nombreArchivo = '';
        this.tamanoArchivo = '';
        this.cargarMedicamentos();
      },
      error: (err) => {
        this.subiendoExcel = false;
        console.error('Error al subir Excel:', err);
        this.mensajeError = err.error?.message || err.error?.mensaje || 'Error al procesar el archivo Excel. Verifique la estructura del archivo.';
      }
    });
  }

  cargarDefaultServidor(): void {
    this.subiendoExcel = true;
    this.mensajeError = '';
    this.mensajeExito = '';

    this.drogaService.cargarExcelDefault().subscribe({
      next: (resp) => {
        this.subiendoExcel = false;
        this.mensajeExito = resp.mensaje || '¡Medicamentos del archivo institucional cargados con éxito!';
        this.medicamentosCargadosContador = resp.medicamentosCargados;
        this.cargarMedicamentos();
      },
      error: (err) => {
        this.subiendoExcel = false;
        console.error('Error al cargar Excel por defecto:', err);
        this.mensajeError = err.error?.message || err.error?.mensaje || 'No se pudo cargar el archivo Excel por defecto del servidor.';
      }
    });
  }

  eliminarMedicamento(id: number | undefined, nombre: string): void {
    if (!id) return;
    if (confirm(`¿Está seguro de eliminar el medicamento "${nombre}" del catálogo?`)) {
      this.drogaService.eliminar(id).subscribe({
        next: () => {
          this.mensajeExito = `El medicamento "${nombre}" fue eliminado del catálogo.`;
          this.cargarMedicamentos();
        },
        error: (err) => {
          console.error('Error al eliminar medicamento:', err);
          this.mensajeError = 'No se pudo eliminar el medicamento del catálogo.';
        }
      });
    }
  }

  vaciarCatalogo(): void {
    if (confirm('¿Está seguro de vaciar completamente el catálogo de medicamentos? Los tratamientos previos registrados en mascotas no se perderán.')) {
      this.cargandoLista = true;
      this.drogaService.limpiarCatalogo().subscribe({
        next: () => {
          this.mensajeExito = 'El catálogo de medicamentos ha sido vaciado exitosamente.';
          this.cargarMedicamentos();
        },
        error: (err) => {
          console.error('Error al vaciar catálogo:', err);
          this.mensajeError = 'No se pudo vaciar el catálogo de medicamentos.';
          this.cargandoLista = false;
        }
      });
    }
  }

  get medicamentosFiltrados(): Droga[] {
    if (!this.filtroBusqueda.trim()) {
      return this.medicamentos;
    }
    const busqueda = this.filtroBusqueda.toLowerCase();
    return this.medicamentos.filter(m => 
      m.nombre.toLowerCase().includes(busqueda)
    );
  }

  get totalDisponibles(): number {
    return this.medicamentos.reduce((acc, curr) => acc + (curr.unidadesDisponibles || 0), 0);
  }

  get totalAgotados(): number {
    return this.medicamentos.filter(m => (m.unidadesDisponibles || 0) <= 0).length;
  }
}
