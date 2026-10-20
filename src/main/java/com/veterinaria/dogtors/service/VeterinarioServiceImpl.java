package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Veterinario;
import com.veterinaria.dogtors.errors.NotFoundException;
import com.veterinaria.dogtors.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Override
    public List<Veterinario> buscarTodos() {
        return veterinarioRepository.findAll();
    }

    @Override
    public Veterinario buscarPorId(Long id) {
        return veterinarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Veterinario no encontrado con ID: " + id));
    }

    @Override
    public Veterinario validarLogin(String correo, String password) {
        Veterinario vet = veterinarioRepository.findByCorreo(correo);
        if (vet == null || !vet.getPassword().equals(password)) {
            throw new IllegalArgumentException("Correo o contraseña incorrectos");
        }
        return vet;
    }
}
