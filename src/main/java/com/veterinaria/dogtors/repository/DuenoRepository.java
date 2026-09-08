package com.veterinaria.dogtors.repository;

import com.veterinaria.dogtors.entities.Dueno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DuenoRepository extends JpaRepository<Dueno, Long> {
    // JPA genera automaticamente la consulta a partir del nombre del metodo
    Dueno findByCorreo(String correo);
}