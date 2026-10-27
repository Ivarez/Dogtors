package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Mascota;
import com.veterinaria.dogtors.entities.Tratamiento;
import com.veterinaria.dogtors.entities.Veterinario;
import com.veterinaria.dogtors.errors.NotFoundException;
import com.veterinaria.dogtors.repository.TratamientoRepository;
import com.veterinaria.dogtors.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación del servicio de médicos veterinarios.
 * Permite el CRUD desde el portal de administrador, activar/desactivar, eliminar sin borrar
 * los tratamientos suministrados, bloquear el login si está inactivo y consultar las mascotas tratadas.
 */
@Service
@SuppressWarnings("null")
public class VeterinarioServiceImpl implements VeterinarioService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Override
    public List<Veterinario> buscarTodos() {
        return veterinarioRepository.findAll();
    }

    @Override
    public List<Veterinario> buscarPorTermino(String termino) {
        if (termino == null || termino.isBlank()) {
            return veterinarioRepository.findAll();
        }
        String q = termino.trim();
        return veterinarioRepository
                .findByCedulaContainingIgnoreCaseOrNombreContainingIgnoreCaseOrEspecialidadContainingIgnoreCase(q, q, q);
    }

    @Override
    public Veterinario buscarPorId(Long id) {
        return veterinarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Veterinario no encontrado con ID: " + id));
    }

    @Override
    public Veterinario buscarPorCedula(String cedula) {
        Veterinario vet = veterinarioRepository.findByCedula(cedula);
        if (vet == null) {
            throw new NotFoundException("Veterinario no encontrado con cédula: " + cedula);
        }
        return vet;
    }

    @Override
    @Transactional
    public Veterinario guardar(Veterinario veterinario) {
        if (veterinario.getActivo() == null) {
            veterinario.setActivo(true);
        }
        if (veterinario.getCedula() == null || veterinario.getCedula().isBlank()) {
            veterinario.setCedula("8000" + Math.abs(veterinario.getCorreo() != null ? veterinario.getCorreo().hashCode() % 100000 : 0));
        }
        if (veterinario.getId() != null) {
            veterinarioRepository.findById(veterinario.getId()).ifPresent(existente -> {
                veterinario.setTratamientos(existente.getTratamientos());
                if (veterinario.getPassword() == null || veterinario.getPassword().isBlank()) {
                    veterinario.setPassword(existente.getPassword());
                }
            });
        }
        return veterinarioRepository.save(veterinario);
    }

    @Override
    @Transactional
    public Veterinario cambiarEstado(Long id) {
        Veterinario vet = buscarPorId(id);
        vet.setActivo(!Boolean.TRUE.equals(vet.getActivo()));
        return veterinarioRepository.save(vet);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Veterinario vet = buscarPorId(id);
        // Conservar el historial de tratamientos desvinculando la llave foránea sin borrar los tratamientos
        List<Tratamiento> tratamientosDelVet = tratamientoRepository.findByVeterinario_Id(id);
        for (Tratamiento t : tratamientosDelVet) {
            t.setNombreVeterinarioRespaldo(vet.getNombre());
            t.setEspecialidadVeterinarioRespaldo(vet.getEspecialidad());
            t.setVeterinario(null);
        }
        tratamientoRepository.saveAll(tratamientosDelVet);
        vet.getTratamientos().clear();
        veterinarioRepository.delete(vet);
    }

    @Override
    public List<Mascota> obtenerMascotasTratadas(Long veterinarioId) {
        buscarPorId(veterinarioId);
        List<Tratamiento> tratamientos = tratamientoRepository.findByVeterinario_Id(veterinarioId);
        Map<Long, Mascota> mascotasUnicas = new LinkedHashMap<>();
        for (Tratamiento t : tratamientos) {
            Mascota m = t.getMascota();
            if (m != null && m.getId() != null) {
                mascotasUnicas.putIfAbsent(m.getId(), m);
            }
        }
        return new ArrayList<>(mascotasUnicas.values());
    }

    @Override
    public Veterinario validarLogin(String correo, String password) {
        Veterinario vet = veterinarioRepository.findByCorreo(correo);
        if (vet == null || !vet.getPassword().equals(password)) {
            throw new IllegalArgumentException("Correo o contraseña incorrectos");
        }
        // Si el veterinario está desactivado, no se le permite iniciar sesión
        if (Boolean.FALSE.equals(vet.getActivo())) {
            throw new IllegalArgumentException("El médico veterinario se encuentra desactivado en el sistema y no puede iniciar sesión.");
        }
        return vet;
    }
}
