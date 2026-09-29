package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Dueno;
import com.veterinaria.dogtors.errors.NotFoundException;
import com.veterinaria.dogtors.repository.DuenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DuenoServiceImpl implements DuenoService {

    @Autowired
    private DuenoRepository duenoRepository;

    @Override
    public List<Dueno> findAll() {
        return duenoRepository.findAll();
    }

    @Override
    public Dueno findById(Long id) {
        return duenoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "dueno"));
    }

    @Override
    public void save(Dueno dueno) {
        duenoRepository.save(dueno);
    }

    @Override
    public void delete(Long id) {
        duenoRepository.deleteById(id);
    }

    @Override
    public Dueno validarLogin(String correo, String password) {
        Dueno dueno = duenoRepository.findByCorreo(correo);
        if (dueno == null || !dueno.getPassword().equals(password)) {
            throw new IllegalArgumentException("Correo o contraseña incorrectos");
        }
        return dueno;
    }
}