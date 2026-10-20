package com.veterinaria.dogtors.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Droga getDroga() {
        return droga;
    }

    public void setDroga(Droga droga) {
        this.droga = droga;
    }
}
