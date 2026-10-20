package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Veterinario;

import java.util.List;

public interface VeterinarioService {

    List<Veterinario> buscarTodos();

    Veterinario buscarPorId(Long id);

    Veterinario validarLogin(String correo, String password);
}
