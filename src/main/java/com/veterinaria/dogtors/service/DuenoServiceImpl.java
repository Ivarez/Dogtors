package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Dueno;
import com.veterinaria.dogtors.errors.NotFoundException;
import com.veterinaria.dogtors.repository.DuenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de propietarios (dueños).
 * Al eliminar un dueño se eliminan en cascada todas sus mascotas asociadas.
 */
@Service
public class DuenoServiceImpl implements DuenoService {

    @Autowired
    private DuenoRepository duenoRepository;

    @Override
    public List<Dueno> findAll() {
        return duenoRepository.findAll();
    }

    @Override
    public Dueno findById(Long id) {
        return duenoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "dueno"));
    }

    @Override
    public Dueno findByCedula(String cedula) {
        Dueno dueno = duenoRepository.findByCedula(cedula);
        if (dueno == null) {
            throw new NotFoundException("Propietario no encontrado con cédula: " + cedula);
        }
        return dueno;
    }

    @Override
    @Transactional
    public void save(Dueno dueno) {
        if (dueno.getCedula() == null || dueno.getCedula().isBlank()) {
            dueno.setCedula("1000" + Math.abs(dueno.getCorreo() != null ? dueno.getCorreo().hashCode() % 100000 : 0));
        }
        if (dueno.getCelular() == null || dueno.getCelular().isBlank()) {
            dueno.setCelular("3000000000");
        }
        // Si es actualización, conservar las mascotas ya asociadas al dueño
        if (dueno.getId() != null) {
            duenoRepository.findById(dueno.getId()).ifPresent(existente -> {
                dueno.setMascotas(existente.getMascotas());
            });
        }
        duenoRepository.save(dueno);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Dueno dueno = findById(id);
        duenoRepository.delete(dueno);
    }

    @Override
    public Dueno validarLogin(String correo, String password) {
        Dueno dueno = duenoRepository.findByCorreo(correo);
        if (dueno == null || !dueno.getPassword().equals(password)) {
            throw new IllegalArgumentException("Correo o contraseña incorrectos");
        }
        return dueno;
    }
}
