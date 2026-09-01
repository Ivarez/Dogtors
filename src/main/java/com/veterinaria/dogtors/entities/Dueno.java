package com.veterinaria.dogtors.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dueno {
    private Integer id;
    private String nombre;
    private String correo;
    private String password;
} 