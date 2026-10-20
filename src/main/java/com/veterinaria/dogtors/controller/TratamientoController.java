package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.dto.CrearTratamientoDTO;
import com.veterinaria.dogtors.entities.Tratamiento;
import com.veterinaria.dogtors.service.TratamientoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un tratamiento por ID")
    public Tratamiento buscarPorId(@PathVariable("id") Long id) {
        return tratamientoService.buscarPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo tratamiento (descuenta 1 unidad de la droga en el backend)")
    public Tratamiento crearTratamiento(@RequestBody CrearTratamientoDTO dto) {
        return tratamientoService.crearTratamiento(dto);
    }
}

