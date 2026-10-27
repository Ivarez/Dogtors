package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Dueno;
import java.util.List;

/**
 * Interfaz de servicio para la lógica de negocio asociada a los propietarios (dueños).
 */
public interface DuenoService {

    List<Dueno> findAll();

    Dueno findById(Long id);

    Dueno findByCedula(String cedula);

    void save(Dueno dueno);

    void delete(Long id);

    Dueno validarLogin(String correo, String password);
}
