import { Injectable } from '@angular/core';
import { Dueno } from '../models/dueno.model';

@Injectable({
  providedIn: 'root'
})
export class DuenoService {

  private duenos: Dueno[] = [
    { id: 1, nombre: 'Juan Pérez', correo: 'juan.perez@dogtors.com', password: '123456' },
    { id: 2, nombre: 'María García', correo: 'maria.garcia@dogtors.com', password: '123456' },
    { id: 3, nombre: 'Carlos López', correo: 'carlos.lopez@dogtors.com', password: '123456' },
    { id: 4, nombre: 'Ana Martínez', correo: 'ana.martinez@dogtors.com', password: '123456' },
    { id: 5, nombre: 'Pedro Rodríguez', correo: 'pedro.rodriguez@dogtors.com', password: '123456' },
    { id: 6, nombre: 'Laura Sánchez', correo: 'laura.sanchez@dogtors.com', password: '123456' }
  ];

  constructor() { }

  obtenerDuenos(): Dueno[] {
    return this.duenos;
  }

  obtenerDuenoPorId(id: number): Dueno | undefined {
    return this.duenos.find(d => d.id === id);
  }

  agregarDueno(dueno: Dueno): void {
    const maxId = this.duenos.length > 0 ? Math.max(...this.duenos.map(d => d.id || 0)) : 0;
    dueno.id = maxId + 1;
    this.duenos.push(dueno);
  }

  actualizarDueno(id: number, dueno: Dueno): void {
    const existente = this.obtenerDuenoPorId(id);
    if (existente) {
      // Se modifica el mismo objeto para que las mascotas asociadas vean el cambio
      Object.assign(existente, { ...dueno, id: id });
    }
  }

  eliminarDueno(id: number): void {
    const index = this.duenos.findIndex(d => d.id === id);
    if (index !== -1) {
      this.duenos.splice(index, 1);
    }
  }
}

