package com.veterinaria.dogtors.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa al propietario (cliente) de una o varias mascotas en la clínica.
 */
@Entity
@Getter
@Setter
public class Dueno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String cedula;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 150)
    private String correo;

    @Column(nullable = false, length = 30)
    private String celular;

    @Column(nullable = false, length = 100)
    private String password;

    // Se ignora en JSON para no devolver listas ni objetos anidados desde el backend
    @OneToMany(mappedBy = "dueno", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Mascota> mascotas = new ArrayList<>();

    public Dueno() {
    }

    public Dueno(Long id, String cedula, String nombre, String correo, String celular, String password, List<Mascota> mascotas) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.celular = celular;
        this.password = password;
        if (mascotas != null) {
            this.mascotas = mascotas;
        }
    }

    public Dueno(String cedula, String nombre, String correo, String celular, String password) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.celular = celular;
        this.password = password;
    }

    public Dueno(String nombre, String correo, String password) {
        this("1000" + Math.abs(correo != null ? correo.hashCode() % 100000 : 0), nombre, correo, "3000000000", password);
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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }
}
