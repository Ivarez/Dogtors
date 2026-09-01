package com.veterinaria.dogtors.repository;

import com.veterinaria.dogtors.entities.Dueno;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class DuenoRepository {
    private Map<Integer, Dueno> data = new HashMap<>();
    private AtomicInteger idCounter = new AtomicInteger(0);

    public DuenoRepository() {
        save(new Dueno(null, "Ej", "ejemplo@gmail.com", "1234"));
    }

    public Collection<Dueno> findAll() {
        return data.values();
    }

    public Dueno findById(Integer id) {
        return data.get(id);
    }

    public void save(Dueno dueno) {
        if (dueno.getId() == null) {
            dueno.setId(idCounter.incrementAndGet());
        }
        data.put(dueno.getId(), dueno);
    }

    public void delete(Integer id) {
        data.remove(id);
    }

    public Dueno findByCorreo(String correo) {
        return data.values().stream()
                .filter(d -> d.getCorreo().equals(correo))
                .findFirst()
                .orElse(null);
    }
}