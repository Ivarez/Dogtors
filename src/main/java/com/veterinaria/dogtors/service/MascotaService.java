package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Mascota;
import java.util.Collection;

public interface MascotaService {
    Collection<Mascota> buscarTodas();
    Mascota buscarPorId(Integer id);
    void save(Mascota mascota);
    void delete(Integer id);
    
    Collection<Mascota> buscarPorDuenoId(Integer duenoId);
}