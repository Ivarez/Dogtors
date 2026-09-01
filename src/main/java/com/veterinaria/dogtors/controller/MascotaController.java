package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.entities.Dueno;
import com.veterinaria.dogtors.entities.Mascota;
import com.veterinaria.dogtors.service.MascotaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    private boolean validarSesion(HttpSession session) {
        return session.getAttribute("usuarioLogueado") != null;
    }

    @GetMapping
    public String mostrarTodasLasMascotas(Model model, HttpSession session) {
        if (!validarSesion(session)) return "redirect:/duenos/login";
        Dueno usuario = (Dueno) session.getAttribute("usuarioLogueado");
        model.addAttribute("mascotas", mascotaService.buscarPorDuenoId(usuario.getId()));
        return "mostrar_todas_mascotas"; 
    }

    @GetMapping("/{id}")
    public String mostrarDetalleMascota(@PathVariable Integer id, Model model, HttpSession session) {
        if (!validarSesion(session)) return "redirect:/duenos/login";
        model.addAttribute("mascota", mascotaService.buscarPorId(id));
        return "mostrar_mascota";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioCrear(Model model, HttpSession session) {
        if (!validarSesion(session)) return "redirect:/duenos/login";
        Dueno usuario = (Dueno) session.getAttribute("usuarioLogueado");
        Mascota nuevaMascota = new Mascota();
        nuevaMascota.setDuenoId(usuario.getId());
        nuevaMascota.setActiva(true);
        model.addAttribute("mascota", nuevaMascota);
        return "formulario_mascota"; 
    }

    @PostMapping("/guardar")
    public String guardarMascota(@ModelAttribute("mascota") Mascota mascota, HttpSession session) {
        if (!validarSesion(session)) return "redirect:/duenos/login";
        Dueno usuario = (Dueno) session.getAttribute("usuarioLogueado");
        
        // Blindaje de seguridad: forzar el ID del dueño logueado en servidor
        mascota.setDuenoId(usuario.getId());
        
        mascotaService.save(mascota);
        return "redirect:/mascotas"; 
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model, HttpSession session) {
        if (!validarSesion(session)) return "redirect:/duenos/login";
        model.addAttribute("mascota", mascotaService.buscarPorId(id));
        return "formulario_mascota"; 
    }

    @GetMapping("/borrar/{id}")
    public String borrarMascota(@PathVariable Integer id, HttpSession session) {
        if (!validarSesion(session)) return "redirect:/duenos/login";
        mascotaService.delete(id);
        return "redirect:/mascotas";
    }
}