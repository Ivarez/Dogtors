package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.dto.DashboardKpisDTO;
import com.veterinaria.dogtors.dto.MedicamentoCantidadDTO;
import com.veterinaria.dogtors.entities.Droga;

import java.util.List;

/**
 * Interfaz de servicio para calcular los indicadores (KPIs) del Dashboard del Administrador (Feature 09 - AC29).
 */
public interface DashboardService {

    DashboardKpisDTO obtenerKpis();

    List<MedicamentoCantidadDTO> obtenerTratamientosPorMedicamentoUltimoMes();

    List<Droga> obtenerTop3TratamientosVendidos();
}

