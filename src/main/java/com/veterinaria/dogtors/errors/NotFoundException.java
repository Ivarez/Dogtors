package com.veterinaria.dogtors.errors;

/**
 * Excepción personalizada para entidades no encontradas en el sistema.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Long id, String entidad) {
        super("No se pudo encontrar el/la " + entidad + " con ID " + id);
    }
}
