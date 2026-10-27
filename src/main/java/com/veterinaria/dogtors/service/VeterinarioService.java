package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Mascota;
import com.veterinaria.dogtors.entities.Veterinario;

import java.util.List;

/**
 * Interfaz de servicio para el CRUD y operaciones de negocio de médicos veterinarios.
 */
public interface VeterinarioService {

    List<Veterinario> buscarTodos();

    List<Veterinario> buscarPorTermino(String termino);

    Veterinario buscarPorId(Long id);

    Veterinario buscarPorCedula(String cedula);

    Veterinario guardar(Veterinario veterinario);

    Veterinario cambiarEstado(Long id);

    void eliminar(Long id);

    List<Mascota> obtenerMascotasTratadas(Long veterinarioId);

    Veterinario validarLogin(String correo, String password);
}
