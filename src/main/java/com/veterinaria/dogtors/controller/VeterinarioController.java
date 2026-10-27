package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.entities.Mascota;
import com.veterinaria.dogtors.entities.Veterinario;
import com.veterinaria.dogtors.service.VeterinarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el CRUD de médicos veterinarios (portal administrador),
 * búsqueda por cédula/nombre, activación/desactivación, mascotas tratadas y login.
 */
@RestController
@RequestMapping("/veterinarios")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
public class VeterinarioController {

    @Autowired
    private VeterinarioService veterinarioService;

    @GetMapping
    @Operation(summary = "Listar todos los veterinarios")
    public List<Veterinario> listarTodos() {
        return veterinarioService.buscarTodos();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar veterinarios por cédula, nombre o especialidad")
    public List<Veterinario> buscarVeterinarios(@RequestParam(value = "q", required = false) String q) {
        return veterinarioService.buscarPorTermino(q);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un veterinario por ID")
    public Veterinario buscarPorId(@PathVariable("id") Long id) {
        return veterinarioService.buscarPorId(id);
    }

    @GetMapping("/cedula/{cedula}")
    @Operation(summary = "Obtener un veterinario por su cédula")
    public Veterinario buscarPorCedula(@PathVariable("cedula") String cedula) {
        return veterinarioService.buscarPorCedula(cedula);
    }

    @GetMapping("/{id}/mascotas")
    @Operation(summary = "Listar todas las mascotas a las cuales el veterinario ha suministrado tratamiento (AC32)")
    public List<Mascota> listarMascotasTratadas(@PathVariable("id") Long id) {
        return veterinarioService.obtenerMascotasTratadas(id);
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo médico veterinario (Portal Administrador)")
    public Veterinario crearVeterinario(@RequestBody Veterinario veterinario) {
        return veterinarioService.guardar(veterinario);
    }

    @PutMapping
    @Operation(summary = "Actualizar los datos de un médico veterinario")
    public Veterinario actualizarVeterinario(@RequestBody Veterinario veterinario) {
        return veterinarioService.guardar(veterinario);
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Activar o desactivar un médico veterinario")
    public Veterinario cambiarEstadoVeterinario(@PathVariable("id") Long id) {
        return veterinarioService.cambiarEstado(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un médico veterinario conservando el historial de tratamientos")
    public void eliminarVeterinario(@PathVariable("id") Long id) {
        veterinarioService.eliminar(id);
    }

    @PostMapping("/login")
    @Operation(summary = "Login de veterinario por correo y contraseña (rechaza si está inactivo)")
    public Veterinario procesarLogin(@RequestBody Veterinario credenciales) {
        return veterinarioService.validarLogin(credenciales.getCorreo(), credenciales.getPassword());
    }
}
