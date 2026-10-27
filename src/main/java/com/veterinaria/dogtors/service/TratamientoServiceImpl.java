package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.dto.CrearTratamientoDTO;
import com.veterinaria.dogtors.entities.Droga;
import com.veterinaria.dogtors.entities.Mascota;
import com.veterinaria.dogtors.entities.Tratamiento;
import com.veterinaria.dogtors.entities.Veterinario;
import com.veterinaria.dogtors.errors.NotFoundException;
import com.veterinaria.dogtors.repository.DrogaRepository;
import com.veterinaria.dogtors.repository.MascotaRepository;
import com.veterinaria.dogtors.repository.TratamientoRepository;
import com.veterinaria.dogtors.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación del servicio de tratamientos médicos.
 * Valida que la mascota esté activa, que el veterinario esté activo y que el medicamento tenga stock (> 0),
 * descontando 1 unidad disponible y aumentando 1 unidad vendida.
 */
@Service
public class TratamientoServiceImpl implements TratamientoService {

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private DrogaRepository drogaRepository;

    @Override
    public List<Tratamiento> buscarTodos() {
        return tratamientoRepository.findAll();
    }

    @Override
    public List<Tratamiento> buscarPorMascotaId(Long mascotaId) {
        return tratamientoRepository.findByMascotaId(mascotaId);
    }

    @Override
    public List<Tratamiento> buscarPorVeterinarioId(Long veterinarioId) {
        return tratamientoRepository.findByVeterinarioId(veterinarioId);
    }

    @Override
    public Tratamiento buscarPorId(Long id) {
        return tratamientoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tratamiento no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Tratamiento crearTratamiento(CrearTratamientoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Los datos del tratamiento son requeridos.");
        }
        if (dto.getMascotaId() == null) {
            throw new IllegalArgumentException("Debe especificar el ID de la mascota asociada al tratamiento.");
        }
        if (dto.getVeterinarioId() == null) {
            throw new IllegalArgumentException("Debe especificar el ID del veterinario tratante.");
        }
        if (dto.getDrogaId() == null) {
            throw new IllegalArgumentException("Debe especificar el ID del medicamento administrado.");
        }

        // Consulta paso a paso 1: Mascota
        Mascota mascota = mascotaRepository.findById(dto.getMascotaId())
                .orElseThrow(() -> new NotFoundException("Mascota no encontrada con ID: " + dto.getMascotaId()));

        // Regla de negocio: Si la mascota no está activa en la veterinaria, no se le puede asignar tratamiento
        if (Boolean.FALSE.equals(mascota.getActiva())) {
            throw new IllegalArgumentException("La mascota '" + mascota.getNombre() + "' no está activa en la veterinaria (alta médica). No se le puede asignar un tratamiento.");
        }

        // Consulta paso a paso 2: Veterinario
        Veterinario veterinario = veterinarioRepository.findById(dto.getVeterinarioId())
                .orElseThrow(() -> new NotFoundException("Veterinario no encontrado con ID: " + dto.getVeterinarioId()));

        if (Boolean.FALSE.equals(veterinario.getActivo())) {
            throw new IllegalArgumentException("El veterinario '" + veterinario.getNombre() + "' se encuentra inactivo y no puede registrar tratamientos.");
        }

        // Consulta paso a paso 3: Droga / Medicamento
        Droga droga = drogaRepository.findById(dto.getDrogaId())
                .orElseThrow(() -> new NotFoundException("Medicamento no encontrado con ID: " + dto.getDrogaId()));

        // Regla de negocio: Si no hay unidades disponibles, rechazar el tratamiento
        if (droga.getUnidadesDisponibles() == null || droga.getUnidadesDisponibles() <= 0) {
            throw new IllegalArgumentException("No hay unidades disponibles del medicamento '" + droga.getNombre() + "' para asignar el tratamiento.");
        }

        // Disminuir cantidad disponible y aumentar cantidad vendida
        droga.setUnidadesDisponibles(droga.getUnidadesDisponibles() - 1);
        droga.setUnidadesVendidas(droga.getUnidadesVendidas() != null ? droga.getUnidadesVendidas() + 1 : 1);
        drogaRepository.save(droga);

        // Crear tratamiento con la fecha actual
        Tratamiento tratamiento = new Tratamiento(
                LocalDate.now(),
                dto.getObservaciones(),
                mascota,
                veterinario,
                droga
        );

        Tratamiento guardado = tratamientoRepository.save(tratamiento);
        if (veterinario.getTratamientos() != null) {
            veterinario.getTratamientos().add(guardado);
        }
        return guardado;
    }
}
