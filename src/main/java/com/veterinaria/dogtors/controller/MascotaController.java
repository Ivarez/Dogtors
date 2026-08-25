package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.entities.Mascota;
import com.veterinaria.dogtors.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @GetMapping
    public String mostrarTodasLasMascotas(Model model) {
        model.addAttribute("mascotas", mascotaService.buscarTodas());
        return "mostrar_todas_mascotas"; 
    }

    @GetMapping("/{id}")
    public String mostrarDetalleMascota(@PathVariable Integer id, Model model) {
        model.addAttribute("mascota", mascotaService.buscarPorId(id));
        return "mostrar_mascota";
    }
}