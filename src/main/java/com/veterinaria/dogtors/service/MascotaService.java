package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Mascota;
import java.util.Collection;

public interface MascotaService {
    Collection<Mascota> buscarTodas();
    Mascota buscarPorId(Integer id);
}