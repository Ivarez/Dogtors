package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Droga;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface ExcelService {

    /**
     * Parsea un archivo Excel (.xlsx) desde un InputStream y retorna la lista de entidades Droga.
     */
    List<Droga> leerDrogasDesdeExcel(InputStream inputStream);

    /**
     * Parsea un archivo Excel (.xlsx) desde un File físico.
     */
    List<Droga> leerDrogasDesdeArchivo(File archivo);

    /**
     * Parsea un archivo Excel subido mediante MultipartFile.
     */
    List<Droga> leerDrogasDesdeMultipart(MultipartFile multipartFile);
}

