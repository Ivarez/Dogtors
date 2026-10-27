package com.veterinaria.dogtors.dto;

/**
 * DTO plano (sin objetos ni listas anidadas) con los KPIs principales del Dashboard del Administrador (AC29).
 */
public class DashboardKpisDTO {

    private long tratamientosUltimoMes;
    private long veterinariosActivos;
    private long veterinariosInactivos;
    private long mascotasTotales;
    private long mascotasActivas;
    private double ventasTotales;
    private double gananciasTotales;

    public DashboardKpisDTO() {
    }

    public DashboardKpisDTO(long tratamientosUltimoMes, long veterinariosActivos, long veterinariosInactivos,
                            long mascotasTotales, long mascotasActivas, double ventasTotales, double gananciasTotales) {
        this.tratamientosUltimoMes = tratamientosUltimoMes;
        this.veterinariosActivos = veterinariosActivos;
        this.veterinariosInactivos = veterinariosInactivos;
        this.mascotasTotales = mascotasTotales;
        this.mascotasActivas = mascotasActivas;
        this.ventasTotales = ventasTotales;
        this.gananciasTotales = gananciasTotales;
    }

    public long getTratamientosUltimoMes() {
        return tratamientosUltimoMes;
    }

    public void setTratamientosUltimoMes(long tratamientosUltimoMes) {
        this.tratamientosUltimoMes = tratamientosUltimoMes;
    }

    public long getVeterinariosActivos() {
        return veterinariosActivos;
    }

    public void setVeterinariosActivos(long veterinariosActivos) {
        this.veterinariosActivos = veterinariosActivos;
    }

    public long getVeterinariosInactivos() {
        return veterinariosInactivos;
    }

    public void setVeterinariosInactivos(long veterinariosInactivos) {
        this.veterinariosInactivos = veterinariosInactivos;
    }

    public long getMascotasTotales() {
        return mascotasTotales;
    }

    public void setMascotasTotales(long mascotasTotales) {
        this.mascotasTotales = mascotasTotales;
    }

    public long getMascotasActivas() {
        return mascotasActivas;
    }

    public void setMascotasActivas(long mascotasActivas) {
        this.mascotasActivas = mascotasActivas;
    }

    public double getVentasTotales() {
        return ventasTotales;
    }

    public void setVentasTotales(double ventasTotales) {
        this.ventasTotales = ventasTotales;
    }

    public double getGananciasTotales() {
        return gananciasTotales;
    }

    public void setGananciasTotales(double gananciasTotales) {
        this.gananciasTotales = gananciasTotales;
    }
}

