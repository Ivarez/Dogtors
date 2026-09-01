package com.veterinaria.dogtors.repository;

import com.veterinaria.dogtors.entities.Mascota;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MascotaRepository {
    private Map<Integer, Mascota> data = new HashMap<>();
    private AtomicInteger idCounter = new AtomicInteger(0);

    public MascotaRepository() {
        // Orden: id, duenoId, nombre, especie, raza, edad, peso, enfermedad, fotoUrl, activa
        save(new Mascota(null, 1, "Firulais", "Perro", "Golden Retriever", 4, 32.5, "Dermatitis leve", "/images/perro.jpg", true));
        save(new Mascota(null, 1, "Michi", "Gato", "Gato Persa", 6, 4.2, "Obstrucción urinaria", "/images/gato.jpg", true));
    }

    public Collection<Mascota> findAll() {
        return data.values();
    }

    public Mascota findById(Integer id) {
        return data.get(id);
    }

    public void save(Mascota mascota) {
        if (mascota.getId() == null) {
            mascota.setId(idCounter.incrementAndGet());
        }
        data.put(mascota.getId(), mascota);
    }

    public void delete(Integer id) {
        data.remove(id);
    }
}