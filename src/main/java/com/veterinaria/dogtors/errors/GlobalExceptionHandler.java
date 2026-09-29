package com.veterinaria.dogtors.errors;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Controlador especial que atrapa excepciones y redirige a una pagina de error
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(org.springframework.dao.DataIntegrityViolationException ex, Model model) {
        // En lugar de dar un 500 feo, mostramos el error amigable
        model.addAttribute("mensaje", "Error de integridad: Ya existe un registro con esos datos únicos (como el correo o la cédula).");
        return "error";
    }
}
