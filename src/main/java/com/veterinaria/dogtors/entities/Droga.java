package com.veterinaria.dogtors.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Setter
public class Droga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Double precioVenta;

    @Column(nullable = false)
    private Double precioCompra;

    @Column(nullable = false)
    private Integer unidadesDisponibles;

    @Column(nullable = false)
    private Integer unidadesVendidas;

    @OneToMany(mappedBy = "droga")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Tratamiento> tratamientos = new ArrayList<>();

    public Droga() {
    }

    @Builder
    public Droga(Long id, String nombre, Double precioVenta, Double precioCompra, Integer unidadesDisponibles, Integer unidadesVendidas, List<Tratamiento> tratamientos) {
        this.id = id;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.precioCompra = precioCompra;
        this.unidadesDisponibles = unidadesDisponibles;
        this.unidadesVendidas = unidadesVendidas;
        if (tratamientos != null) {
            this.tratamientos = tratamientos;
        }
    }

    // Constructor con todos los parametros excepto id y relaciones
    public Droga(String nombre, Double precioVenta, Double precioCompra, Integer unidadesDisponibles, Integer unidadesVendidas) {
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.precioCompra = precioCompra;
        this.unidadesDisponibles = unidadesDisponibles;
        this.unidadesVendidas = unidadesVendidas;
    }
}