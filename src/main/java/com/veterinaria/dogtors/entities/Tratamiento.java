package com.veterinaria.dogtors.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entidad que representa un tratamiento médico suministrado a una mascota.
 * Todas las relaciones (@ManyToOne) tienen @JsonIgnore para evitar devolver objetos anidados.
 * Expone propiedades escalares planas y conserva respaldos de nombre de veterinario y droga
 * por si el veterinario o el medicamento son eliminados posteriormente.
 */
@Entity
@Getter
@Setter
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha = LocalDate.now();

    @Column(length = 500)
    private String observaciones;

    // Respaldo escalar en caso de que el veterinario o droga se eliminen del sistema
    @Column(length = 100)
    @JsonIgnore
    private String nombreVeterinarioRespaldo;

    @Column(length = 100)
    @JsonIgnore
    private String especialidadVeterinarioRespaldo;

    @Column(length = 100)
    @JsonIgnore
    private String nombreDrogaRespaldo;

    @Column
    @JsonIgnore
    private Double precioDrogaRespaldo;

    // Relación con Mascota (ignorada en JSON para no devolver objetos anidados)
    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    @JsonIgnore
    private Mascota mascota;

    // Relación con Veterinario (nullable para permitir eliminar veterinario sin borrar sus tratamientos)
    @ManyToOne
    @JoinColumn(name = "veterinario_id", nullable = true)
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.SET_NULL)
    @JsonIgnore
    private Veterinario veterinario;

    // Relación con Droga (nullable para permitir recargar/eliminar drogas sin perder historial)
    @ManyToOne
    @JoinColumn(name = "droga_id", nullable = true)
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.SET_NULL)
    @JsonIgnore
    private Droga droga;

    public Tratamiento() {
    }

    public Tratamiento(Long id, LocalDate fecha, String observaciones, Mascota mascota, Veterinario veterinario, Droga droga) {
        this(fecha, observaciones, mascota, veterinario, droga);
        this.id = id;
    }

    public Tratamiento(LocalDate fecha, String observaciones, Mascota mascota, Veterinario veterinario, Droga droga) {
        this.fecha = (fecha != null) ? fecha : LocalDate.now();
        this.observaciones = observaciones;
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.droga = droga;
        if (veterinario != null) {
            this.nombreVeterinarioRespaldo = veterinario.getNombre();
            this.especialidadVeterinarioRespaldo = veterinario.getEspecialidad();
        }
        if (droga != null) {
            this.nombreDrogaRespaldo = droga.getNombre();
            this.precioDrogaRespaldo = droga.getPrecioVenta();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @JsonIgnore
    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    @JsonIgnore
    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
        if (veterinario != null) {
            this.nombreVeterinarioRespaldo = veterinario.getNombre();
            this.especialidadVeterinarioRespaldo = veterinario.getEspecialidad();
        }
    }

    @JsonIgnore
    public Droga getDroga() {
        return droga;
    }

    public void setDroga(Droga droga) {
        this.droga = droga;
        if (droga != null) {
            this.nombreDrogaRespaldo = droga.getNombre();
            this.precioDrogaRespaldo = droga.getPrecioVenta();
        }
    }

    // Propiedades escalares planas para el frontend (sin objetos anidados)
    public Long getMascotaId() {
        return mascota != null ? mascota.getId() : null;
    }

    public String getMascotaNombre() {
        return mascota != null ? mascota.getNombre() : null;
    }

    public Long getVeterinarioId() {
        return veterinario != null ? veterinario.getId() : null;
    }

    public String getVeterinarioNombre() {
        if (veterinario != null) {
            return veterinario.getNombre();
        }
        return nombreVeterinarioRespaldo != null ? nombreVeterinarioRespaldo : "Médico Veterinario";
    }

    public String getVeterinarioEspecialidad() {
        if (veterinario != null) {
            return veterinario.getEspecialidad();
        }
        return especialidadVeterinarioRespaldo;
    }

    public Long getDrogaId() {
        return droga != null ? droga.getId() : null;
    }

    public String getDrogaNombre() {
        if (droga != null) {
            return droga.getNombre();
        }
        return nombreDrogaRespaldo != null ? nombreDrogaRespaldo : "Medicamento registrado";
    }

    public Double getDrogaPrecioVenta() {
        if (droga != null) {
            return droga.getPrecioVenta();
        }
        return precioDrogaRespaldo;
    }
}
