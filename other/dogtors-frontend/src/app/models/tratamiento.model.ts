import { Mascota } from './mascota.model';
import { Veterinario } from './veterinario.model';
import { Droga } from './droga.model';

export interface Tratamiento {
  id?: number;
  fecha: string;
  observaciones: string;
  mascota?: Mascota;
  veterinario?: Veterinario;
  droga?: Droga;
}
