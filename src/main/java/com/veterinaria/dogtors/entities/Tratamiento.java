package com.veterinaria.dogtors.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Builder.Default
    private LocalDate fecha = LocalDate.now();

    @Column(length = 500)
    private String observaciones;

    // Relación con Mascota
    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    // Relación con Veterinario
    @ManyToOne
    @JoinColumn(name = "veterinario_id", nullable = false)
    private Veterinario veterinario;

    // Relación con Droga
    @ManyToOne
    @JoinColumn(name = "droga_id", nullable = false)
    private Droga droga;

    public Tratamiento(LocalDate fecha, String observaciones, Mascota mascota, Veterinario veterinario, Droga droga) {
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.droga = droga;
    }
}

