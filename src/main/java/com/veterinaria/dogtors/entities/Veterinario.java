package com.veterinaria.dogtors.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }
}
