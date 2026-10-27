package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.entities.Dueno;
import com.veterinaria.dogtors.service.DuenoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el CRUD y autenticación de propietarios (dueños de mascotas).
 */
@RestController
@RequestMapping("/duenos")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
public class DuenoController {

    @Autowired
    private DuenoService duenoService;

    @GetMapping
    @Operation(summary = "Listar todos los dueños")
    public List<Dueno> listarDuenos() {
        return duenoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un dueño por su ID")
    public Dueno buscarPorId(@PathVariable("id") Long id) {
        return duenoService.findById(id);
    }

    @GetMapping("/cedula/{cedula}")
    @Operation(summary = "Obtener un dueño por su número de cédula")
    public Dueno buscarPorCedula(@PathVariable("cedula") String cedula) {
        return duenoService.findByCedula(cedula);
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo dueño")
    public Dueno agregarDueno(@RequestBody Dueno dueno) {
        duenoService.save(dueno);
        return dueno;
    }

    @PutMapping
    @Operation(summary = "Actualizar datos de un dueño")
    public void actualizarDueno(@RequestBody Dueno dueno) {
        duenoService.save(dueno);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un dueño por ID junto con sus mascotas en cascada")
    public void borrarDueno(@PathVariable("id") Long id) {
        duenoService.delete(id);
    }

    @GetMapping("/borrar/{id}")
    @Operation(summary = "Eliminar un dueño (GET alternativo)")
    public void borrarDuenoGet(@PathVariable("id") Long id) {
        duenoService.delete(id);
    }

    @PostMapping("/login")
    @Operation(summary = "Login de cliente por correo y contraseña")
    public Dueno procesarLogin(@RequestBody Dueno credenciales) {
        return duenoService.validarLogin(credenciales.getCorreo(), credenciales.getPassword());
    }
}

