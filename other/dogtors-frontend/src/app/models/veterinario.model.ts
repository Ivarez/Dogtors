import { Tratamiento } from './tratamiento.model';

/**
 * Modelo de Médico Veterinario de la clínica.
 */
export interface Veterinario {
  id?: number;
  cedula: string;
  nombre: string;
  correo: string;
  password?: string;
  especialidad: string;
  fotoUrl?: string;
  activo: boolean;
  numeroAtenciones?: number;
  tratamientos?: Tratamiento[];
}
