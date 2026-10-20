package com.veterinaria.dogtors.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString(exclude = "dueno")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String especie;

    @Column(length = 80)
    private String raza;

    @Column
    private Integer edad;

    @Column(nullable = false)
    private Double peso;

    @Column(nullable = false, length = 255)
    private String enfermedad;

    @Column(length = 500)
    private String fotoUrl;

    @Column(nullable = false)
    private Boolean activa = true;

    // Relacion ManyToOne con Dueno
    @ManyToOne
    @JoinColumn(name = "dueno_id")
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private Dueno dueno;

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.util.List<Tratamiento> tratamientos = new java.util.ArrayList<>();

    public Mascota() {
    }

    @Builder
    public Mascota(Long id, String nombre, String especie, String raza, Integer edad,
                   Double peso, String enfermedad, String fotoUrl, Boolean activa,
                   Dueno dueno, java.util.List<Tratamiento> tratamientos) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.peso = peso;
        this.enfermedad = enfermedad;
        this.fotoUrl = fotoUrl;
        this.activa = (activa != null) ? activa : true;
        this.dueno = dueno;
        if (tratamientos != null) {
            this.tratamientos = tratamientos;
        }
    }

    // Constructor con todos los parametros excepto id y relaciones
    public Mascota(String nombre, String especie, String raza, Integer edad,
                   Double peso, String enfermedad, String fotoUrl, Boolean activa) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.peso = peso;
        this.enfermedad = enfermedad;
        this.fotoUrl = fotoUrl;
        this.activa = activa;
    }
}
