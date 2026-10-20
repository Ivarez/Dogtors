package com.veterinaria.dogtors.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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
    private List<Tratamiento> tratamientos = new ArrayList<>();

    public Mascota() {
    }

    public Mascota(Long id, String nombre, String especie, String raza, Integer edad,
                   Double peso, String enfermedad, String fotoUrl, Boolean activa,
                   Dueno dueno, List<Tratamiento> tratamientos) {
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

    public Mascota(String nombre, String especie, String raza, Integer edad,
                   Double peso, String enfermedad, String fotoUrl, Boolean activa, Dueno dueno) {
        this(nombre, especie, raza, edad, peso, enfermedad, fotoUrl, activa);
        this.dueno = dueno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public Dueno getDueno() {
        return dueno;
    }

    public void setDueno(Dueno dueno) {
        this.dueno = dueno;
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }
}
