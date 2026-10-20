package com.veterinaria.dogtors.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha = LocalDate.now();

    @Column(length = 500)
    private String observaciones;

    // Relación con Mascota
    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Mascota mascota;

    // Relación con Veterinario
    @ManyToOne
    @JoinColumn(name = "veterinario_id", nullable = false)
    private Veterinario veterinario;

    // Relación con Droga
    @ManyToOne
    @JoinColumn(name = "droga_id", nullable = true)
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.SET_NULL)
    private Droga droga;

    public Tratamiento() {
    }

    @Builder
    public Tratamiento(Long id, LocalDate fecha, String observaciones, Mascota mascota, Veterinario veterinario, Droga droga) {
        this.id = id;
        this.fecha = (fecha != null) ? fecha : LocalDate.now();
        this.observaciones = observaciones;
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.droga = droga;
    }

    public Tratamiento(LocalDate fecha, String observaciones, Mascota mascota, Veterinario veterinario, Droga droga) {
        this.fecha = (fecha != null) ? fecha : LocalDate.now();
        this.observaciones = observaciones;
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.droga = droga;
    }
}

