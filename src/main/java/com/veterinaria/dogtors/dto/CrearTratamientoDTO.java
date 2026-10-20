package com.veterinaria.dogtors.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CrearTratamientoDTO {

    private Long mascotaId;
    private Long veterinarioId;
    private Long drogaId;
    private String observaciones;
    private LocalDate fecha;

    public CrearTratamientoDTO() {
    }

    @Builder
    public CrearTratamientoDTO(Long mascotaId, Long veterinarioId, Long drogaId, String observaciones, LocalDate fecha) {
        this.mascotaId = mascotaId;
        this.veterinarioId = veterinarioId;
        this.drogaId = drogaId;
        this.observaciones = observaciones;
        this.fecha = fecha;
    }
}

