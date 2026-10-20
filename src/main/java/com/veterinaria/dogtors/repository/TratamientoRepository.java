package com.veterinaria.dogtors.repository;

import com.veterinaria.dogtors.entities.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {

    List<Tratamiento> findByMascotaId(Long mascotaId);

    List<Tratamiento> findByDrogaId(Long drogaId);
}
