package com.veterinaria.dogtors.repository;

import com.veterinaria.dogtors.entities.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
}

