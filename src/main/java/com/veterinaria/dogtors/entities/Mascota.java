package com.veterinaria.dogtors.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mascota {
    private Integer id;
    private String nombre;
    private String raza;
    private Integer edad;
    private Double peso;
    private String enfermedad;
    private String fotoUrl;
    private Boolean activa;
}