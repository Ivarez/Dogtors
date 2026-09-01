package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Dueno;
import java.util.Collection;

public interface DuenoService {
    Collection<Dueno> findAll();
    Dueno findById(Integer id);
    void save(Dueno dueno);
    void delete(Integer id);
    Dueno validarLogin(String correo, String password);
}