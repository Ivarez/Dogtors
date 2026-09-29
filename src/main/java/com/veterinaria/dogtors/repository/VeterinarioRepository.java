package com.veterinaria.dogtors.repository;

import com.veterinaria.dogtors.entities.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
    Veterinario findByCorreo(String correo);
}
