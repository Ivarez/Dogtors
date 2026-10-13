import { Tratamiento } from './tratamiento.model';

export interface Droga {
  id?: number;
  nombre: string;
  precioVenta: number;
  precioCompra: number;
  unidadesDisponibles: number;
  unidadesVendidas: number;
  tratamientos?: Tratamiento[];
}
