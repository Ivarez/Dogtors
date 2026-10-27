package com.veterinaria.dogtors.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa a un médico veterinario de la clínica.
 * Incluye cédula, especialidad, foto, estado laboral (activo/inactivo) y conteo de atenciones.
 */
@Entity
public class Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String cedula;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 150)
    private String correo;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String especialidad;

    @Column(length = 500)
    private String fotoUrl;

    @Column(nullable = false)
    private Boolean activo = true;

    @OneToMany(mappedBy = "veterinario", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Tratamiento> tratamientos = new ArrayList<>();

    public Veterinario() {
    }

    public Veterinario(Long id, String cedula, String nombre, String correo, String password,
                       String especialidad, String fotoUrl, Boolean activo, List<Tratamiento> tratamientos) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.especialidad = especialidad;
        this.fotoUrl = fotoUrl;
        this.activo = (activo != null) ? activo : true;
        if (tratamientos != null) {
            this.tratamientos = tratamientos;
        }
    }

    public Veterinario(String cedula, String nombre, String correo, String password,
                       String especialidad, String fotoUrl, Boolean activo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.especialidad = especialidad;
        this.fotoUrl = fotoUrl;
        this.activo = (activo != null) ? activo : true;
    }

    public Veterinario(String nombre, String correo, String password, String especialidad) {
        this(
                "8000" + Math.abs(correo != null ? correo.hashCode() % 100000 : 0),
                nombre,
                correo,
                password,
                especialidad,
                "https://images.unsplash.com/photo-1612349317150-e413f6a5b16d?w=400",
                true
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    /**
     * Calcula el número de atenciones (tratamientos suministrados) por este veterinario.
     */
    public Integer getNumeroAtenciones() {
        return tratamientos != null ? tratamientos.size() : 0;
    }

    @JsonIgnore
    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }
}
