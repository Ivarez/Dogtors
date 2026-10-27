package com.veterinaria.dogtors.repository;

import com.veterinaria.dogtors.entities.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio JPA para la gestión de tratamientos médicos suministrados.
 */
@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {

    List<Tratamiento> findByMascota_Id(Long mascotaId);

    List<Tratamiento> findByVeterinario_Id(Long veterinarioId);

    List<Tratamiento> findByDroga_Id(Long drogaId);

    List<Tratamiento> findByFechaGreaterThanEqual(LocalDate fechaDesde);

    long countByFechaGreaterThanEqual(LocalDate fechaDesde);
}
