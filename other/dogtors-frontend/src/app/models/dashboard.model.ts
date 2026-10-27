/**
 * Modelos planos para los indicadores (KPIs) del Dashboard del Administrador (Feature 09 - AC29).
 */
export interface DashboardKpis {
  tratamientosUltimoMes: number;
  veterinariosActivos: number;
  veterinariosInactivos: number;
  mascotasTotales: number;
  mascotasActivas: number;
  ventasTotales: number;
  gananciasTotales: number;
}

export interface MedicamentoCantidad {
  medicamento: string;
  cantidad: number;
}

