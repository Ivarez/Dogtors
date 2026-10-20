package com.veterinaria.dogtors.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Setter
public class Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 150)
    private String correo;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 100)
    private String especialidad;

    @OneToMany(mappedBy = "veterinario")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Tratamiento> tratamientos = new ArrayList<>();

    public Veterinario() {
    }

    @Builder
    public Veterinario(Long id, String nombre, String correo, String password, String especialidad, List<Tratamiento> tratamientos) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.especialidad = especialidad;
        if (tratamientos != null) {
            this.tratamientos = tratamientos;
        }
    }

    // Constructor con todos los parametros excepto id y relaciones
    public Veterinario(String nombre, String correo, String password, String especialidad) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.especialidad = especialidad;
    }
}