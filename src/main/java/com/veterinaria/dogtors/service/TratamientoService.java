package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.dto.CrearTratamientoDTO;
import com.veterinaria.dogtors.entities.Tratamiento;

import java.util.List;

public interface TratamientoService {

    List<Tratamiento> buscarTodos();

    List<Tratamiento> buscarPorMascotaId(Long mascotaId);

    Tratamiento buscarPorId(Long id);

    Tratamiento crearTratamiento(CrearTratamientoDTO dto);
}

