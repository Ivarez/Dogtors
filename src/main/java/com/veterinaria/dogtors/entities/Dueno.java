package com.veterinaria.dogtors.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@ToString(exclude = "mascotas")
public class Dueno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 150)
    private String correo;

    @Column(nullable = false, length = 100)
    private String password;

    @OneToMany(mappedBy = "dueno", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Mascota> mascotas = new ArrayList<>();

    public Dueno() {
    }

    @Builder
    public Dueno(Long id, String nombre, String correo, String password, List<Mascota> mascotas) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        if (mascotas != null) {
            this.mascotas = mascotas;
        }
    }

    // Constructor con todos los parametros excepto id y relaciones
    public Dueno(String nombre, String correo, String password) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
    }
}
