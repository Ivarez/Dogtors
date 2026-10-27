/**
 * Modelo de Tratamiento médico suministrado a una mascota.
 * Recibe propiedades escalares planas desde el backend (sin objetos anidados).
 */
export interface Tratamiento {
  id?: number;
  fecha: string;
  observaciones: string;
  mascotaId?: number;
  mascotaNombre?: string;
  veterinarioId?: number;
  veterinarioNombre?: string;
  veterinarioEspecialidad?: string;
  drogaId?: number;
  drogaNombre?: string;
  drogaPrecioVenta?: number;
}
