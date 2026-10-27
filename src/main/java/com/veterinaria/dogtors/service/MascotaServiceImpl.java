package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Dueno;
import com.veterinaria.dogtors.entities.Mascota;
import com.veterinaria.dogtors.errors.NotFoundException;
import com.veterinaria.dogtors.repository.DuenoRepository;
import com.veterinaria.dogtors.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de mascotas.
 * Las mascotas no se eliminan directamente, sino que cambian su estado entre activa e inactiva.
 */
@Service
@SuppressWarnings("null")
public class MascotaServiceImpl implements MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private DuenoRepository duenoRepository;

    @Override
    public List<Mascota> buscarTodas() {
        return mascotaRepository.findAll();
    }

    @Override
    public List<Mascota> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return mascotaRepository.findAll();
        }
        return mascotaRepository.findByNombreContainingIgnoreCase(nombre.trim());
    }

    @Override
    public Mascota buscarPorId(Long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "mascota"));
    }

    @Override
    @Transactional
    public void save(Mascota mascota, Long duenoId) {
        if (mascota.getActiva() == null) {
            mascota.setActiva(true);
        }
        if (mascota.getEnfermedad() == null) {
            mascota.setEnfermedad("");
        }
        Dueno dueno = duenoRepository.findById(duenoId)
                .orElseThrow(() -> new NotFoundException(duenoId, "dueno"));
        mascota.setDueno(dueno);

        if (mascota.getId() != null) {
            mascotaRepository.findById(mascota.getId()).ifPresent(existente -> {
                mascota.setTratamientos(existente.getTratamientos());
            });
        }

        mascotaRepository.save(mascota);
    }

    @Override
    @Transactional
    public void cambiarEstado(Long id) {
        // Las mascotas únicamente se activan o desactivan según si están hospitalizadas o en casa
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "mascota"));
        mascota.setActiva(!mascota.getActiva());
        mascotaRepository.save(mascota);
    }

    @Override
    @Transactional
    public void save(Mascota mascota) {
        if (mascota.getActiva() == null) {
            mascota.setActiva(true);
        }
        if (mascota.getEnfermedad() == null) {
            mascota.setEnfermedad("");
        }

        // Resolver dueño ya sea por el campo plano duenoId o por referencia existente
        Long idDueno = mascota.getDuenoId();
        if (idDueno != null) {
            Dueno dueno = duenoRepository.findById(idDueno)
                    .orElseThrow(() -> new NotFoundException(idDueno, "dueno"));
            mascota.setDueno(dueno);
        }

        if (mascota.getId() != null) {
            mascotaRepository.findById(mascota.getId()).ifPresent(existente -> {
                if (mascota.getDueno() == null) {
                    mascota.setDueno(existente.getDueno());
                }
                mascota.setTratamientos(existente.getTratamientos());
            });
        }

        mascotaRepository.save(mascota);
    }

    @Override
    public List<Mascota> buscarPorDueno(Dueno dueno) {
        return mascotaRepository.findByDueno(dueno);
    }

    @Override
    public List<Mascota> buscarPorDuenoId(Long duenoId) {
        return mascotaRepository.findByDueno_Id(duenoId);
    }
}
