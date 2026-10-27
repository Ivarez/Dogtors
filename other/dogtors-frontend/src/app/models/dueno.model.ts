import { Mascota } from './mascota.model';

/**
 * Modelo de Propietario (Cliente / Dueño de mascota).
 */
export interface Dueno {
  id?: number;
  cedula: string;
  nombre: string;
  correo: string;
  celular: string;
  password?: string;
  mascotas?: Mascota[];
}
