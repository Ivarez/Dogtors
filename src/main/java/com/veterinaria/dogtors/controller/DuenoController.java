package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.entities.Dueno;
import com.veterinaria.dogtors.service.DuenoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("/duenos") // Buena práctica: agrupar rutas lógicas de la entidad
public class DuenoController {

    @Autowired
    private DuenoService duenoService;

    // 1. Mostrar formulario de Login
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("dueno", new Dueno()); // Objeto vacío para el th:object
        return "login"; 
    }

    // 2. Procesar Login y crear Sesión
    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute("dueno") Dueno dueno, HttpSession session, Model model) {
        Dueno autenticado = duenoService.validarLogin(dueno.getCorreo(), dueno.getPassword());
        
        if (autenticado != null) {
            // Guardamos el objeto entero en la sesión del navegador
            session.setAttribute("usuarioLogueado", autenticado);
            return "redirect:/mascotas"; // Redirección exitosa al CRUD
        }
        
        model.addAttribute("error", "Correo o contraseña incorrectos");
        return "login"; // Si falla, recarga la vista
    }

    // 3. Mostrar formulario de Registro
    @GetMapping("/nueva")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("dueno", new Dueno());
        return "registro_dueno"; 
    }

    // 4. Procesar el Registro en la BD falsa
    @PostMapping("/nueva")
    public String procesarRegistro(@ModelAttribute("dueno") Dueno dueno) {
        duenoService.save(dueno);
        return "redirect:/duenos/login"; // Patrón PRG (Post-Redirect-Get) del profesor
    }

    // 5. Listar todos los dueños (CRUD)
    @GetMapping("/lista")
    public String listarDuenos(Model model, HttpSession session) {
        // En una app real, esto estaría protegido para un rol "Admin".
        model.addAttribute("duenos", duenoService.findAll());
        return "mostrar_todos_duenos";
    }

    // 6. Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model, HttpSession session) {
        Dueno dueno = duenoService.findById(id);
        if (dueno == null) {
            return "redirect:/duenos/lista";
        }
        model.addAttribute("dueno", dueno);
        return "editar_dueno";
    }

    // 7. Procesar edición
    @PostMapping("/editar")
    public String procesarEdicion(@ModelAttribute("dueno") Dueno dueno) {
        duenoService.save(dueno); // Como el ID ya viene, el repository lo actualiza
        return "redirect:/duenos/lista";
    }

    // 8. Borrar dueño
    @GetMapping("/borrar/{id}")
    public String borrarDueno(@PathVariable Integer id, HttpSession session) {
        duenoService.delete(id);
        return "redirect:/duenos/lista";
    }

    // 9. Cerrar Sesión
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); // Destruye la sesión actual
        return "redirect:/"; // Redirige a la landing page
    }
}