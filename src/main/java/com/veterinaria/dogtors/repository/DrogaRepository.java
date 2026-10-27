package com.veterinaria.dogtors.repository;

import com.veterinaria.dogtors.entities.Droga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la gestión del catálogo de medicamentos (drogas).
 */
@Repository
public interface DrogaRepository extends JpaRepository<Droga, Long> {

    Optional<Droga> findByNombreIgnoreCase(String nombre);

    List<Droga> findByUnidadesDisponiblesGreaterThan(Integer cantidad);

    List<Droga> findTop3ByOrderByUnidadesVendidasDesc();
}
