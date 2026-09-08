package com.veterinaria.dogtors.repository;

import com.veterinaria.dogtors.entities.Dueno;
import com.veterinaria.dogtors.entities.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    // JPA genera la consulta automaticamente
    List<Mascota> findByDueno(Dueno dueno);
}