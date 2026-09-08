package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.entities.Mascota;
import com.veterinaria.dogtors.service.MascotaService;
import com.veterinaria.dogtors.service.DuenoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private DuenoService duenoService;

    // Mostrar TODAS las mascotas (Veterinario)
    @GetMapping
    public String mostrarTodasLasMascotas(Model model) {
        model.addAttribute("mascotas", mascotaService.buscarTodas());
        return "mostrar_todas_mascotas";
    }

    // Detalle de una mascota
    @GetMapping("/{id}")
    public String mostrarDetalleMascota(@PathVariable Long id, Model model) {
        model.addAttribute("mascota", mascotaService.buscarPorId(id));
        return "mostrar_mascota";
    }

    // Formulario de creacion (Veterinario)
    @GetMapping("/nueva")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("mascota", new Mascota());
        model.addAttribute("duenos", duenoService.findAll());
        return "formulario_mascota";
    }

    // Guardar mascota (recibe el duenoId como @RequestParam, como enseña el profe)
    @PostMapping("/guardar")
    public String guardarMascota(@ModelAttribute("mascota") Mascota mascota,
                                 @RequestParam("duenoId") Long duenoId) {
        mascotaService.save(mascota, duenoId);
        return "redirect:/mascotas";
    }

    // Formulario de edicion (Veterinario)
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("mascota", mascotaService.buscarPorId(id));
        model.addAttribute("duenos", duenoService.findAll());
        return "formulario_mascota";
    }

    // Sprint 4: No eliminar mascota, cambiar su estado activa/inactiva
    @GetMapping("/cambiar-estado/{id}")
    public String cambiarEstadoMascota(@PathVariable Long id) {
        mascotaService.cambiarEstado(id);
        return "redirect:/mascotas";
    }
}