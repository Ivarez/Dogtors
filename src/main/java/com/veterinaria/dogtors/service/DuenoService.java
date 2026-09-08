package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Dueno;
import java.util.List;

public interface DuenoService {
    List<Dueno> findAll();
    Dueno findById(Long id);
    void save(Dueno dueno);
    void delete(Long id);
    Dueno validarLogin(String correo, String password);
}