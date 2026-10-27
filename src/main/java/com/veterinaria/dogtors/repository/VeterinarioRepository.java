package com.veterinaria.dogtors.repository;

import com.veterinaria.dogtors.entities.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la gestión de médicos veterinarios y búsquedas por cédula, nombre o especialidad.
 */
@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {

    Veterinario findByCorreo(String correo);

    Veterinario findByCedula(String cedula);

    List<Veterinario> findByCedulaContainingIgnoreCaseOrNombreContainingIgnoreCaseOrEspecialidadContainingIgnoreCase(
            String cedula, String nombre, String especialidad);

    long countByActivoTrue();

    long countByActivoFalse();
}
