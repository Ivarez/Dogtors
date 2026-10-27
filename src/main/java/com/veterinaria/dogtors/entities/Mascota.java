package com.veterinaria.dogtors.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa a una mascota hospitalizada o dada de alta en la veterinaria.
 * Todas las relaciones tienen @JsonIgnore para evitar devolver objetos anidados desde el backend.
 */
@Entity
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

    // Puede ser vacío cuando la mascota recién ingresa mientras es atendida por un médico
    @Column(length = 255)
    private String enfermedad;

    @Column(length = 500)
    private String fotoUrl;

    @Column(nullable = false)
    private Boolean activa = true;

    // Relación ManyToOne con Dueno ignorada en la serialización JSON para evitar objetos anidados
    @ManyToOne
    @JoinColumn(name = "dueno_id")
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    @JsonIgnore
    private Dueno dueno;

    // Campo auxiliar plano para recibir o exponer el ID del dueño sin anidar el objeto Dueno
    @Transient
    private Long duenoId;

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
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

    @JsonIgnore
    public Dueno getDueno() {
        return dueno;
    }

    public void setDueno(Dueno dueno) {
        this.dueno = dueno;
    }

    // Propiedades escalares planas del dueño (no devuelven ningún objeto anidado)
    public Long getDuenoId() {
        if (dueno != null && dueno.getId() != null) {
            return dueno.getId();
        }
        return duenoId;
    }

    public void setDuenoId(Long duenoId) {
        this.duenoId = duenoId;
    }

    public String getDuenoNombre() {
        return dueno != null ? dueno.getNombre() : null;
    }

    public String getDuenoCedula() {
        return dueno != null ? dueno.getCedula() : null;
    }

    public String getDuenoCorreo() {
        return dueno != null ? dueno.getCorreo() : null;
    }

    public String getDuenoCelular() {
        return dueno != null ? dueno.getCelular() : null;
    }

    @JsonIgnore
    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }
}
