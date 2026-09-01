package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Mascota;
import com.veterinaria.dogtors.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MascotaServiceImpl implements MascotaService {
    
    @Autowired
    private MascotaRepository mascotaRepository;

    @Override
    public Collection<Mascota> buscarTodas() {
        return mascotaRepository.findAll();
    }

    @Override
    public Mascota buscarPorId(Integer id) {
        return mascotaRepository.findById(id);
    }

    @Override
    public void save(Mascota mascota) {
        mascotaRepository.save(mascota);
    }

    @Override
    public void delete(Integer id) {
        mascotaRepository.delete(id);
    }

    @Override
    public Collection<Mascota> buscarPorDuenoId(Integer duenoId) {
        List<Mascota> mascotasFiltradas = new ArrayList<>();
        // Iteramos sobre todas las mascotas y filtramos manualmente
        for (Mascota mascota : mascotaRepository.findAll()) {
            if (mascota.getDuenoId() != null && mascota.getDuenoId().equals(duenoId)) {
                mascotasFiltradas.add(mascota);
            }
        }
        return mascotasFiltradas;
    }
}