package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.entities.Mascota;
import com.veterinaria.dogtors.service.MascotaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mascotas")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    // Mostrar todas las mascotas (devuelve JSON directamente, tal como explica el transcript)
    @GetMapping
    @Operation(summary = "Mostrar todas las mascotas")
    public List<Mascota> mostrarTodasLasMascotas() {
        return mascotaService.buscarTodas();
    }

    // Mostrar información de una única mascota por ID
    @GetMapping("/{id}")
    @Operation(summary = "Mostrar detalle de una mascota")
    public Mascota mostrarDetalleMascota(@PathVariable Long id) {
        return mascotaService.buscarPorId(id);
    }

    // Traer las mascotas de un dueño en específico (patrón student/{id} del transcript)
    @GetMapping("/dueno/{duenoId}")
    @Operation(summary = "Mostrar mascotas asociadas a un dueño")
    public List<Mascota> mostrarMascotasPorDueno(@PathVariable Long duenoId) {
        return mascotaService.buscarPorDuenoId(duenoId);
    }

    // Agregar mascota recibida por el body de la petición
    @PostMapping
    @Operation(summary = "Agregar una mascota")
    public void agregarMascota(@RequestBody Mascota mascota) {
        mascotaService.save(mascota);
    }

    // Actualizar mascota recibida por el body de la petición
    @PutMapping
    @Operation(summary = "Actualizar una mascota")
    public void actualizarMascota(@RequestBody Mascota mascota) {
        mascotaService.save(mascota);
    }

    // Alternar estado de la mascota (no se elimina, se cambia de activa a inactiva)
    @PutMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de activa/inactiva de una mascota")
    public void cambiarEstadoMascota(@PathVariable Long id) {
        mascotaService.cambiarEstado(id);
    }

    @GetMapping("/cambiar-estado/{id}")
    @Operation(summary = "Cambiar estado de activa/inactiva (GET alternativo)")
    public void cambiarEstadoMascotaGet(@PathVariable Long id) {
        mascotaService.cambiarEstado(id);
    }
}