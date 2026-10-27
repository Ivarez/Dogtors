import { Dueno } from './dueno.model';
import { Tratamiento } from './tratamiento.model';

/**
 * Modelo de Mascota (Paciente de la veterinaria).
 * Utiliza propiedades escalares planas (duenoId, duenoNombre, etc.) para evitar objetos anidados.
 */
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
  duenoId?: number;
  duenoNombre?: string;
  duenoCedula?: string;
  duenoCorreo?: string;
  duenoCelular?: string;
  dueno?: Dueno;
  tratamientos?: Tratamiento[];
}
