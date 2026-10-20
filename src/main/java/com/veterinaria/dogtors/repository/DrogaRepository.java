package com.veterinaria.dogtors.repository;

import com.veterinaria.dogtors.entities.Droga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DrogaRepository extends JpaRepository<Droga, Long> {

    Optional<Droga> findByNombreIgnoreCase(String nombre);

    List<Droga> findByUnidadesDisponiblesGreaterThan(Integer cantidad);
}
