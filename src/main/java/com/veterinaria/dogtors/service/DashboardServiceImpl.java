package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.dto.DashboardKpisDTO;
import com.veterinaria.dogtors.dto.MedicamentoCantidadDTO;
import com.veterinaria.dogtors.entities.Droga;
import com.veterinaria.dogtors.entities.Tratamiento;
import com.veterinaria.dogtors.repository.DrogaRepository;
import com.veterinaria.dogtors.repository.MascotaRepository;
import com.veterinaria.dogtors.repository.TratamientoRepository;
import com.veterinaria.dogtors.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación del servicio del Dashboard del Administrador (Feature 09 - AC29).
 * Calcula todos los KPIs sin devolver objetos anidados.
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private DrogaRepository drogaRepository;

    @Override
    public DashboardKpisDTO obtenerKpis() {
        LocalDate haceUnMes = LocalDate.now().minusMonths(1);
        long tratamientosUltimoMes = tratamientoRepository.countByFechaGreaterThanEqual(haceUnMes);
        long veterinariosActivos = veterinarioRepository.countByActivoTrue();
        long veterinariosInactivos = veterinarioRepository.countByActivoFalse();
        long mascotasTotales = mascotaRepository.count();
        long mascotasActivas = mascotaRepository.countByActivaTrue();

        List<Droga> todasLasDrogas = drogaRepository.findAll();
        double ventasTotales = 0.0;
        double gananciasTotales = 0.0;

        for (Droga d : todasLasDrogas) {
            int vendidas = d.getUnidadesVendidas() != null ? d.getUnidadesVendidas() : 0;
            double pVenta = d.getPrecioVenta() != null ? d.getPrecioVenta() : 0.0;
            double pCompra = d.getPrecioCompra() != null ? d.getPrecioCompra() : 0.0;
            ventasTotales += vendidas * pVenta;
            gananciasTotales += vendidas * (pVenta - pCompra);
        }

        return new DashboardKpisDTO(
                tratamientosUltimoMes,
                veterinariosActivos,
                veterinariosInactivos,
                mascotasTotales,
                mascotasActivas,
                ventasTotales,
                gananciasTotales
        );
    }

    @Override
    public List<MedicamentoCantidadDTO> obtenerTratamientosPorMedicamentoUltimoMes() {
        LocalDate haceUnMes = LocalDate.now().minusMonths(1);
        List<Tratamiento> recientes = tratamientoRepository.findByFechaGreaterThanEqual(haceUnMes);

        Map<String, Long> conteoPorMedicamento = new LinkedHashMap<>();
        for (Tratamiento t : recientes) {
            String nombreMed = t.getDrogaNombre();
            if (nombreMed == null || nombreMed.isBlank()) {
                nombreMed = "Medicamento registrado";
            }
            conteoPorMedicamento.put(nombreMed, conteoPorMedicamento.getOrDefault(nombreMed, 0L) + 1L);
        }

        List<MedicamentoCantidadDTO> resultado = new ArrayList<>();
        for (Map.Entry<String, Long> entry : conteoPorMedicamento.entrySet()) {
            resultado.add(new MedicamentoCantidadDTO(entry.getKey(), entry.getValue()));
        }
        resultado.sort(Comparator.comparingLong(MedicamentoCantidadDTO::getCantidad).reversed());
        return resultado;
    }

    @Override
    public List<Droga> obtenerTop3TratamientosVendidos() {
        return drogaRepository.findTop3ByOrderByUnidadesVendidasDesc();
    }
}

