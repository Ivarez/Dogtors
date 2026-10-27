package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.entities.Administrador;
import com.veterinaria.dogtors.service.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la autenticación y consulta de administradores del sistema.
 */
@RestController
@RequestMapping("/administradores")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping
    @Operation(summary = "Listar todos los administradores")
    public List<Administrador> listarTodos() {
        return administradorService.buscarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un administrador por ID")
    public Administrador buscarPorId(@PathVariable("id") Long id) {
        return administradorService.buscarPorId(id);
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar un administrador con su correo y contraseña")
    public Administrador procesarLogin(@RequestBody Administrador credenciales) {
        return administradorService.validarLogin(credenciales.getCorreo(), credenciales.getPassword());
    }
}

