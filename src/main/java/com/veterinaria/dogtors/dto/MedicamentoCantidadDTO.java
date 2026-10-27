package com.veterinaria.dogtors.dto;

/**
 * DTO plano que representa una fila de la tabla "Medicamento - Cantidad administrada en el último mes" (AC29).
 */
public class MedicamentoCantidadDTO {

    private String medicamento;
    private long cantidad;

    public MedicamentoCantidadDTO() {
    }

    public MedicamentoCantidadDTO(String medicamento, long cantidad) {
        this.medicamento = medicamento;
        this.cantidad = cantidad;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }
}

