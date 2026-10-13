import { Injectable, inject } from '@angular/core';
import { Mascota } from '../models/mascota.model';
import { Dueno } from '../models/dueno.model';
import { DuenoService } from './dueno.service';

@Injectable({
  providedIn: 'root'
})
export class MascotaService {
  private duenoService = inject(DuenoService);

  // Los dueños vienen de DuenoService: las mascotas apuntan a esos mismos objetos
  private duenos: Dueno[] = this.duenoService.obtenerDuenos();

  private mascotas: Mascota[] = [
    {
      id: 1,
      nombre: 'Max',
      especie: 'Perro',
      raza: 'Golden Retriever',
      edad: 4,
      peso: 32.5,
      enfermedad: 'Gastroenteritis aguda',
      fotoUrl: 'https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=600',
      activa: true,
      dueno: this.duenos[0],
      tratamientos: [
        {
          id: 1,
          fecha: '2026-03-10',
          observaciones: 'Hidratación intravenosa y protector gástrico',
          veterinario: { id: 1, nombre: 'Dra. Carolina Ríos', correo: 'c.rios@dogtors.com', especialidad: 'Medicina Interna' },
          droga: { id: 1, nombre: 'Omeprazol Inyectable', precioCompra: 12000, precioVenta: 25000, unidadesDisponibles: 45, unidadesVendidas: 120 }
        }
      ]
    },
    {
      id: 2,
      nombre: 'Luna',
      especie: 'Gato',
      raza: 'Siamés',
      edad: 2,
      peso: 4.1,
      enfermedad: 'Infección urinaria',
      fotoUrl: 'https://images.unsplash.com/photo-1574158622682-e40e69881006?w=600',
      activa: true,
      dueno: this.duenos[1],
      tratamientos: [
        {
          id: 2,
          fecha: '2026-03-12',
          observaciones: 'Antibiótico de amplio espectro por 7 días',
          veterinario: { id: 2, nombre: 'Dr. Alejandro Cruz', correo: 'a.cruz@dogtors.com', especialidad: 'Urología Felina' },
          droga: { id: 2, nombre: 'Amoxicilina + Clavulánico', precioCompra: 18000, precioVenta: 35000, unidadesDisponibles: 30, unidadesVendidas: 85 }
        }
      ]
    },
    {
      id: 3,
      nombre: 'Rocky',
      especie: 'Perro',
      raza: 'Bulldog Francés',
      edad: 5,
      peso: 12.8,
      enfermedad: 'Dermatitis atópica severa',
      fotoUrl: 'https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?w=600',
      activa: true,
      dueno: this.duenos[2],
      tratamientos: []
    },
    {
      id: 4,
      nombre: 'Bella',
      especie: 'Perro',
      raza: 'Pastor Alemán',
      edad: 6,
      peso: 28.0,
      enfermedad: 'Displasia de cadera grado II',
      fotoUrl: 'https://images.unsplash.com/photo-1587300003388-59208cc962cb?w=600',
      activa: true,
      dueno: this.duenos[3],
      tratamientos: []
    },
    {
      id: 5,
      nombre: 'Milo',
      especie: 'Gato',
      raza: 'Maine Coon',
      edad: 3,
      peso: 6.5,
      enfermedad: 'Control postoperatorio',
      fotoUrl: 'https://images.unsplash.com/photo-1573865526739-10659fec78a5?w=600',
      activa: false,
      dueno: this.duenos[4],
      tratamientos: []
    },
    {
      id: 6,
      nombre: 'Thor',
      especie: 'Perro',
      raza: 'Husky Siberiano',
      edad: 3,
      peso: 24.3,
      enfermedad: 'Otitis externa bilateral',
      fotoUrl: 'https://images.unsplash.com/photo-1548199973-03cce0bbc87b?w=600',
      activa: true,
      dueno: this.duenos[5],
      tratamientos: []
    },
    {
      id: 7,
      nombre: 'Coco',
      especie: 'Perro',
      raza: 'Poodle',
      edad: 7,
      peso: 7.2,
      enfermedad: 'Profilaxis dental y extracción',
      fotoUrl: 'https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=600',
      activa: false,
      dueno: this.duenos[0],
      tratamientos: []
    },
    {
      id: 8,
      nombre: 'Simba',
      especie: 'Gato',
      raza: 'Persa',
      edad: 1,
      peso: 3.8,
      enfermedad: 'Conjuntivitis bacteriana',
      fotoUrl: 'https://images.unsplash.com/photo-1574158622682-e40e69881006?w=600',
      activa: true,
      dueno: this.duenos[1],
      tratamientos: []
    }
  ];

  constructor() { }

  obtenerMascotas(): Mascota[] {
    return this.mascotas;
  }

  obtenerMascotasActivas(): Mascota[] {
    return this.mascotas.filter(m => m.activa);
  }

  obtenerMascotasInactivas(): Mascota[] {
    return this.mascotas.filter(m => !m.activa);
  }

  obtenerMascotaPorId(id: number): Mascota | undefined {
    return this.mascotas.find(m => m.id === id);
  }

  obtenerDuenos(): Dueno[] {
    return this.duenoService.obtenerDuenos();
  }

  obtenerDuenoPorId(id: number): Dueno | undefined {
    return this.duenoService.obtenerDuenoPorId(id);
  }

  obtenerMascotasPorDueno(duenoId: number): Mascota[] {
    return this.mascotas.filter(m => m.dueno?.id === duenoId);
  }

  quitarDueno(duenoId: number): void {
    this.mascotas
      .filter(m => m.dueno?.id === duenoId)
      .forEach(m => m.dueno = undefined);
  }

  agregarMascota(mascota: Mascota): void {
    const maxId = this.mascotas.length > 0 ? Math.max(...this.mascotas.map(m => m.id || 0)) : 0;
    mascota.id = maxId + 1;
    if (!mascota.tratamientos) {
      mascota.tratamientos = [];
    }
    this.mascotas.push(mascota);
  }

  actualizarMascota(id: number, mascota: Mascota): void {
    const index = this.mascotas.findIndex(m => m.id === id);
    if (index !== -1) {
      this.mascotas[index] = {
        ...this.mascotas[index],
        ...mascota,
        id: id
      };
    }
  }

  cambiarEstado(id: number): void {
    const mascota = this.obtenerMascotaPorId(id);
    if (mascota) {
      mascota.activa = !mascota.activa;
    }
  }
}
