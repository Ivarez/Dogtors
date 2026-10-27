package com.veterinaria.dogtors.repository;

import com.veterinaria.dogtors.entities.Dueno;
import com.veterinaria.dogtors.entities.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la gestión de mascotas y búsquedas por dueño o nombre.
 */
@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    List<Mascota> findByDueno(Dueno dueno);

    List<Mascota> findByDuenoId(Long duenoId);

    List<Mascota> findByNombreContainingIgnoreCase(String nombre);

    long countByActivaTrue();
}
