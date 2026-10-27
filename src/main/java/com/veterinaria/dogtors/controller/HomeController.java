package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.entities.Veterinario;
import com.veterinaria.dogtors.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Controlador de vistas estáticas de respaldo, respetando la arquitectura en capas (Controller -> Service).
 */
@Controller
public class HomeController {

    @Autowired
    private VeterinarioService veterinarioService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/veterinario")
    public String verVeterinario(Model model) {
        List<Veterinario> veterinarios = veterinarioService.buscarTodos();
        if (!veterinarios.isEmpty()) {
            model.addAttribute("veterinario", veterinarios.get(0));
        }
        return "mostrar_veterinario";
    }

    @GetMapping("/veterinario/{id}")
    public String verVeterinarioPorId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("veterinario", veterinarioService.buscarPorId(id));
        return "mostrar_veterinario";
    }
}
