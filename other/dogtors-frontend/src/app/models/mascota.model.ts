import { Dueno } from './dueno.model';
import { Tratamiento } from './tratamiento.model';

export interface Mascota {
  id?: number;
  nombre: string;
  especie: string;
  raza: string;
  edad: number;
  peso: number;
  enfermedad: string;
  fotoUrl?: string;
  activa: boolean;
  dueno?: Dueno;
  tratamientos?: Tratamiento[];
}
