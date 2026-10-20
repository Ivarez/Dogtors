package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Droga;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DrogaService {

    List<Droga> buscarTodas();

    List<Droga> buscarDisponibles();

    Droga buscarPorId(Long id);

    Droga guardar(Droga droga);

    int cargarDesdeExcel(MultipartFile archivo);

    int cargarDesdeExcel(MultipartFile archivo, boolean sobrescribir);

    int cargarDesdeExcelServidor(String ruta);

    void eliminar(Long id);

    void limpiarCatalogo();
}

