import { Mascota } from './mascota.model';

export interface Dueno {
  id?: number;
  nombre: string;
  correo: string;
  password?: string;
  mascotas?: Mascota[];
}
