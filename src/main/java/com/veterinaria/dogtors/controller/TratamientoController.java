package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.dto.CrearTratamientoDTO;
import com.veterinaria.dogtors.entities.Tratamiento;
import com.veterinaria.dogtors.service.TratamientoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión y consulta de tratamientos médicos.
 */
@RestController
@RequestMapping("/tratamientos")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
public class TratamientoController {

    @Autowired
    private TratamientoService tratamientoService;

    @GetMapping
    @Operation(summary = "Listar todos los tratamientos")
    public List<Tratamiento> listarTodos() {
        return tratamientoService.buscarTodos();
    }

    @GetMapping("/mascota/{mascotaId}")
    @Operation(summary = "Listar tratamientos asociados a una mascota (consulta paso a paso)")
    public List<Tratamiento> listarPorMascota(@PathVariable("mascotaId") Long mascotaId) {
        return tratamientoService.buscarPorMascotaId(mascotaId);
    }

    @GetMapping("/veterinario/{veterinarioId}")
    @Operation(summary = "Listar tratamientos suministrados por un veterinario")
    public List<Tratamiento> listarPorVeterinario(@PathVariable("veterinarioId") Long veterinarioId) {
        return tratamientoService.buscarPorVeterinarioId(veterinarioId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un tratamiento por ID")
    public Tratamiento buscarPorId(@PathVariable("id") Long id) {
        return tratamientoService.buscarPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo tratamiento (valida mascota activa, descuenta 1 disponible y suma 1 vendida)")
    public Tratamiento crearTratamiento(@RequestBody CrearTratamientoDTO dto) {
        return tratamientoService.crearTratamiento(dto);
    }
}
