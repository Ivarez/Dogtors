package com.veterinaria.dogtors.errors;

// Crear excepcion personalizada que hereda de RuntimeException
public class NotFoundException extends RuntimeException {

    public NotFoundException(Long id, String entidad) {
        super("No se pudo encontrar el/la " + entidad + " con ID " + id);
    }
}
