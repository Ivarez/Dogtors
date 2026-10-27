package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.dto.DashboardKpisDTO;
import com.veterinaria.dogtors.dto.MedicamentoCantidadDTO;
import com.veterinaria.dogtors.entities.Droga;
import com.veterinaria.dogtors.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST del Dashboard de Administrador (Feature 09 - AC29).
 * Expone endpoints planos (sin objetos anidados) para indicadores, tratamientos del último mes y top 3 vendidos.
 */
@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/kpis")
    @Operation(summary = "Obtener los indicadores clave (KPIs) planos del hospital veterinario")
    public DashboardKpisDTO obtenerKpis() {
        return dashboardService.obtenerKpis();
    }

    @GetMapping("/tratamientos-por-medicamento")
    @Operation(summary = "Obtener la cantidad de tratamientos por medicamento en el último mes")
    public List<MedicamentoCantidadDTO> obtenerTratamientosPorMedicamento() {
        return dashboardService.obtenerTratamientosPorMedicamentoUltimoMes();
    }

    @GetMapping("/top-tratamientos")
    @Operation(summary = "Obtener el Top 3 de medicamentos con más unidades vendidas")
    public List<Droga> obtenerTop3Tratamientos() {
        return dashboardService.obtenerTop3TratamientosVendidos();
    }
}

