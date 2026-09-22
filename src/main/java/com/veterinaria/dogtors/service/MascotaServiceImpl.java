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

@Service
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
        // Buscar el dueno y asociarlo a la mascota
        Dueno dueno = duenoRepository.findById(duenoId)
                .orElseThrow(() -> new NotFoundException(duenoId, "dueno"));
        mascota.setDueno(dueno);
        mascotaRepository.save(mascota);
    }

    @Override
    @Transactional
    public void cambiarEstado(Long id) {
        // No eliminar mascota, solo cambiar su estado de activa/inactiva
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "mascota"));
        mascota.setActiva(!mascota.getActiva());
        mascotaRepository.save(mascota);
    }

    @Override
    public List<Mascota> buscarPorDueno(Dueno dueno) {
        return mascotaRepository.findByDueno(dueno);
    }
}