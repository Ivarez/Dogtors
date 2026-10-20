package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Dueno;
import com.veterinaria.dogtors.entities.Mascota;
import java.util.List;

public interface MascotaService {
    List<Mascota> buscarTodas();
    Mascota buscarPorId(Long id);
    void save(Mascota mascota, Long duenoId);
    void save(Mascota mascota);
    void cambiarEstado(Long id);
    List<Mascota> buscarPorDueno(Dueno dueno);
    List<Mascota> buscarPorDuenoId(Long duenoId);
}