package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Dueno;
import com.veterinaria.dogtors.repository.DuenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DuenoServiceImpl implements DuenoService {

    @Autowired
    private DuenoRepository duenoRepository;

    @Override
    public Collection<Dueno> findAll() {
        return duenoRepository.findAll();
    }

    @Override
    public Dueno findById(Integer id) {
        return duenoRepository.findById(id);
    }

    @Override
    public void save(Dueno dueno) {
        duenoRepository.save(dueno);
    }

    @Override
    public void delete(Integer id) {
        duenoRepository.delete(id);
    }

    @Override
    public Dueno validarLogin(String correo, String password) {
        // Iteramos el HashMap para buscar coincidencias
        for (Dueno dueno : duenoRepository.findAll()) {
            if (dueno.getCorreo().equals(correo) && dueno.getPassword().equals(password)) {
                return dueno; // Credenciales correctas
            }
        }
        return null; // Credenciales incorrectas
    }
}