package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Mascota;
import com.veterinaria.dogtors.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;

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
}