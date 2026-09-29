package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired(required = false)
    private VeterinarioRepository veterinarioRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Pantalla independiente de detalle del veterinario (para sustentación)
    @GetMapping("/veterinario")
    public String verVeterinario(Model model) {
        if (veterinarioRepository != null && veterinarioRepository.count() > 0) {
            model.addAttribute("veterinario", veterinarioRepository.findAll().get(0));
        }
        return "mostrar_veterinario";
    }

    @GetMapping("/veterinario/{id}")
    public String verVeterinarioPorId(@PathVariable Long id, Model model) {
        if (veterinarioRepository != null) {
            veterinarioRepository.findById(id).ifPresent(v -> model.addAttribute("veterinario", v));
        }
        return "mostrar_veterinario";
    }
}
