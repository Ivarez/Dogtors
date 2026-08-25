package com.veterinaria.dogtors.repository;

import com.veterinaria.dogtors.entities.Mascota;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.HashMap;

@Repository
public class MascotaRepository {
    
    private HashMap<Integer, Mascota> data = new HashMap<>();

    public MascotaRepository() {
        data.put(1, new Mascota(1, "Max", "Golden Retriever", 4, 30.5, "vacío", "https://ejemplo.com/max.jpg", true));
        data.put(2, new Mascota(2, "Luna", "Siamés", 2, 4.2, "Gastritis", "https://ejemplo.com/luna.jpg", true));
        data.put(3, new Mascota(3, "Rocky", "Bulldog", 5, 22.0, "vacío", "https://ejemplo.com/rocky.jpg", false));
    }

    public Collection<Mascota> findAll() {
        return data.values();
    }

    public Mascota findById(Integer id) {
        return data.get(id);
    }
}