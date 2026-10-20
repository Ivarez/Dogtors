package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.entities.Dueno;
import com.veterinaria.dogtors.service.DuenoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/duenos")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
public class DuenoController {

    @Autowired
    private DuenoService duenoService;

    // Listar todos los dueños
    @GetMapping
    @Operation(summary = "Listar todos los dueños")
    public List<Dueno> listarDuenos() {
        return duenoService.findAll();
    }

    // Buscar dueño por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un dueño por su ID")
    public Dueno buscarPorId(@PathVariable Long id) {
        return duenoService.findById(id);
    }

    // Agregar nuevo dueño (datos recibidos en el Body)
    @PostMapping
    @Operation(summary = "Registrar un nuevo dueño")
    public Dueno agregarDueno(@RequestBody Dueno dueno) {
        duenoService.save(dueno);
        return dueno;
    }

    // Actualizar dueño
    @PutMapping
    @Operation(summary = "Actualizar datos de un dueño")
    public void actualizarDueno(@RequestBody Dueno dueno) {
        duenoService.save(dueno);
    }

    // Borrar dueño por ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un dueño por ID")
    public void borrarDueno(@PathVariable Long id) {
        duenoService.delete(id);
    }

    @GetMapping("/borrar/{id}")
    @Operation(summary = "Eliminar un dueño (GET alternativo)")
    public void borrarDuenoGet(@PathVariable Long id) {
        duenoService.delete(id);
    }

    // Procesar Login mediante POST con credenciales en el Body
    @PostMapping("/login")
    @Operation(summary = "Login de cliente por correo y contraseña")
    public Dueno procesarLogin(@RequestBody Dueno credenciales) {
        return duenoService.validarLogin(credenciales.getCorreo(), credenciales.getPassword());
    }
}