package com.veterinaria.dogtors.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Builder.Default
    private List<Tratamiento> tratamientos = new ArrayList<>();

    // Constructor con todos los parametros excepto id y relaciones
    public Veterinario(String nombre, String correo, String password, String especialidad) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.especialidad = especialidad;
    }
}