import { Tratamiento } from './tratamiento.model';

export interface Veterinario {
  id?: number;
  nombre: string;
  correo: string;
  password?: string;
  especialidad: string;
  tratamientos?: Tratamiento[];
}
