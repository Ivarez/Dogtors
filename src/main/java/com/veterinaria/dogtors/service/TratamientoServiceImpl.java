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

        // Consulta paso a paso 2: Veterinario
        Veterinario veterinario = veterinarioRepository.findById(dto.getVeterinarioId())
                .orElseThrow(() -> new NotFoundException("Veterinario no encontrado con ID: " + dto.getVeterinarioId()));

        // Consulta paso a paso 3: Droga / Medicamento
        Droga droga = drogaRepository.findById(dto.getDrogaId())
                .orElseThrow(() -> new NotFoundException("Medicamento no encontrado con ID: " + dto.getDrogaId()));

        // Regla de negocio en el Backend: si no hay unidades disponibles, rechazar
        if (droga.getUnidadesDisponibles() == null || droga.getUnidadesDisponibles() <= 0) {
            throw new IllegalArgumentException("No hay unidades disponibles del medicamento '" + droga.getNombre() + "' para asignar el tratamiento.");
        }

        // Descontar inventario en el backend
        droga.setUnidadesDisponibles(droga.getUnidadesDisponibles() - 1);
        droga.setUnidadesVendidas(droga.getUnidadesVendidas() != null ? droga.getUnidadesVendidas() + 1 : 1);
        drogaRepository.save(droga);

        // Crear tratamiento
        LocalDate fecha = (dto.getFecha() != null) ? dto.getFecha() : LocalDate.now();
        Tratamiento tratamiento = new Tratamiento(
                fecha,
                dto.getObservaciones(),
                mascota,
                veterinario,
                droga
        );

        return tratamientoRepository.save(tratamiento);
    }
}

