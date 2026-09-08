package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.entities.Dueno;
import com.veterinaria.dogtors.service.DuenoService;
import com.veterinaria.dogtors.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/duenos")
public class DuenoController {

    @Autowired
    private DuenoService duenoService;

    @Autowired
    private MascotaService mascotaService;

    // Mostrar formulario de Login
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("dueno", new Dueno());
        return "login";
    }

    // Procesar Login
    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute("dueno") Dueno dueno, Model model) {
        try {
            Dueno autenticado = duenoService.validarLogin(dueno.getCorreo(), dueno.getPassword());
            return "redirect:/duenos/perfil/" + autenticado.getId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    // Perfil del dueno: muestra sus mascotas en formato de tarjeta
    @GetMapping("/perfil/{id}")
    public String perfilDueno(@PathVariable Long id, Model model) {
        Dueno dueno = duenoService.findById(id);
        model.addAttribute("dueno", dueno);
        model.addAttribute("mascotas", mascotaService.buscarPorDueno(dueno));
        return "perfil_dueno";
    }

    // Formulario de Registro
    @GetMapping("/nueva")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("dueno", new Dueno());
        return "registro_dueno";
    }

    // Procesar Registro
    @PostMapping("/nueva")
    public String procesarRegistro(@ModelAttribute("dueno") Dueno dueno) {
        duenoService.save(dueno);
        return "redirect:/duenos/login";
    }

    // Listar todos los duenos (CRUD Veterinario)
    @GetMapping("/lista")
    public String listarDuenos(Model model) {
        model.addAttribute("duenos", duenoService.findAll());
        return "mostrar_todos_duenos";
    }

    // Formulario de edicion
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Dueno dueno = duenoService.findById(id);
        model.addAttribute("dueno", dueno);
        return "editar_dueno";
    }

    // Procesar edicion
    @PostMapping("/editar")
    public String procesarEdicion(@ModelAttribute("dueno") Dueno dueno) {
        duenoService.save(dueno);
        return "redirect:/duenos/lista";
    }

    // Borrar dueno
    @GetMapping("/borrar/{id}")
    public String borrarDueno(@PathVariable Long id) {
        duenoService.delete(id);
        return "redirect:/duenos/lista";
    }

    // Cerrar sesion (redireccion simple)
    @GetMapping("/logout")
    public String cerrarSesion() {
        return "redirect:/";
    }
}