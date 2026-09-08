package com.veterinaria.dogtors.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Builder.Default
    private Boolean activa = true;

    // Relacion ManyToOne con Dueno (como el profe enseña con Estudiante-Carrera)
    @ManyToOne
    @JoinColumn(name = "dueno_id")
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private Dueno dueno;

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