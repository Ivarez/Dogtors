package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.entities.Veterinario;
import com.veterinaria.dogtors.service.VeterinarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un veterinario por ID")
    public Veterinario buscarPorId(@PathVariable("id") Long id) {
        return veterinarioService.buscarPorId(id);
    }

    @PostMapping("/login")
    @Operation(summary = "Login de veterinario por correo y contraseña")
    public Veterinario procesarLogin(@RequestBody Veterinario credenciales) {
        return veterinarioService.validarLogin(credenciales.getCorreo(), credenciales.getPassword());
    }
}
