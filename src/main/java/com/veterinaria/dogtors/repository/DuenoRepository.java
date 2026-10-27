package com.veterinaria.dogtors.repository;

import com.veterinaria.dogtors.entities.Dueno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la gestión de propietarios (dueños) de mascotas.
 */
@Repository
public interface DuenoRepository extends JpaRepository<Dueno, Long> {

    Dueno findByCorreo(String correo);

    Dueno findByCedula(String cedula);
}
