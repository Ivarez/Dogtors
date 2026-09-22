package com.veterinaria.dogtors.repository;

import com.veterinaria.dogtors.entities.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Administrador findByCorreo(String correo);
}
