package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Administrador;
import com.veterinaria.dogtors.errors.NotFoundException;
import com.veterinaria.dogtors.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de administradores.
 */
@Service
public class AdministradorServiceImpl implements AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    public List<Administrador> buscarTodos() {
        return administradorRepository.findAll();
    }

    @Override
    public Administrador buscarPorId(Long id) {
        return administradorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Administrador no encontrado con ID: " + id));
    }

    @Override
    public Administrador validarLogin(String correo, String password) {
        Administrador admin = administradorRepository.findByCorreo(correo);
        if (admin == null || !admin.getPassword().equals(password)) {
            throw new IllegalArgumentException("Correo o contraseña incorrectos");
        }
        return admin;
    }
}

