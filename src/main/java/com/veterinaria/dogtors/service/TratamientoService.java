package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.dto.CrearTratamientoDTO;
import com.veterinaria.dogtors.entities.Tratamiento;

import java.util.List;

/**
 * Interfaz de servicio para la gestión de tratamientos médicos suministrados.
 */
public interface TratamientoService {

    List<Tratamiento> buscarTodos();

    List<Tratamiento> buscarPorMascotaId(Long mascotaId);

    List<Tratamiento> buscarPorVeterinarioId(Long veterinarioId);

    Tratamiento buscarPorId(Long id);

    Tratamiento crearTratamiento(CrearTratamientoDTO dto);
}
