package com.veterinaria.dogtors.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearTratamientoDTO {

    private Long mascotaId;
    private Long veterinarioId;
    private Long drogaId;
    private String observaciones;

    public CrearTratamientoDTO() {
    }

    public CrearTratamientoDTO(Long mascotaId, Long veterinarioId, Long drogaId, String observaciones) {
        this.mascotaId = mascotaId;
        this.veterinarioId = veterinarioId;
        this.drogaId = drogaId;
        this.observaciones = observaciones;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public Long getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(Long veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    public Long getDrogaId() {
        return drogaId;
    }

    public void setDrogaId(Long drogaId) {
        this.drogaId = drogaId;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
