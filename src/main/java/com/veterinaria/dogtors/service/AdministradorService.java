package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Administrador;
import java.util.List;

/**
 * Interfaz de servicio para la gestión y autenticación de administradores.
 */
public interface AdministradorService {

    List<Administrador> buscarTodos();

    Administrador buscarPorId(Long id);

    Administrador validarLogin(String correo, String password);
}

