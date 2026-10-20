package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Droga;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Implementación del servicio de lectura de archivos Excel (.xlsx) utilizando FastExcel Reader.
 * FastExcel utiliza un enfoque de streaming ultraligero que reduce el consumo de memoria
 * y simplifica drásticamente el código frente a soluciones complejas como Apache POI.
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public List<Droga> leerDrogasDesdeMultipart(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("El archivo Excel subido está vacío o no se ha proporcionado.");
        }
        try (InputStream is = multipartFile.getInputStream();
             ReadableWorkbook workbook = new ReadableWorkbook(is)) {
            return extraerDrogas(workbook);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo Excel subido con FastExcel: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Droga> leerDrogasDesdeExcel(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("El stream del archivo Excel no puede ser nulo.");
        }
        try (ReadableWorkbook workbook = new ReadableWorkbook(inputStream)) {
            return extraerDrogas(workbook);
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo Excel con FastExcel: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Droga> leerDrogasDesdeArchivo(File archivo) {
        if (archivo == null || !archivo.exists()) {
            throw new IllegalArgumentException("El archivo Excel no existe: " + (archivo != null ? archivo.getAbsolutePath() : "null"));
        }
        try (ReadableWorkbook workbook = new ReadableWorkbook(archivo)) {
            return extraerDrogas(workbook);
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo Excel físico con FastExcel: " + e.getMessage(), e);
        }
    }

    /**
     * Extrae las drogas leyendo fila por fila mediante el Stream eficiente de FastExcel.
     */
    private List<Droga> extraerDrogas(ReadableWorkbook workbook) throws IOException {
        List<Droga> drogas = new ArrayList<>();
        Sheet sheet = workbook.getFirstSheet();
        if (sheet == null) {
            return drogas;
        }

        try (Stream<Row> rows = sheet.openStream()) {
            rows.forEach(row -> {
                // Saltar la fila 1 (encabezados de columnas)
                if (row.getRowNum() == 1) {
                    return;
                }

                String nombre = obtenerTexto(row, 0);
                if (nombre == null || nombre.trim().isEmpty() || nombre.equalsIgnoreCase("NOMBRE")) {
                    return;
                }

                Double precioVenta = parseDoubleSeguro(obtenerTexto(row, 1));
                Double precioCompra = parseDoubleSeguro(obtenerTexto(row, 2));
                Integer unidadesDisponibles = parseIntSeguro(obtenerTexto(row, 3));
                Integer unidadesVendidas = parseIntSeguro(obtenerTexto(row, 4));

                Droga droga = new Droga(
                        nombre.trim(),
                        precioVenta,
                        precioCompra,
                        unidadesDisponibles,
                        unidadesVendidas
                );
                drogas.add(droga);
            });
        }

        return drogas;
    }

    private String obtenerTexto(Row row, int index) {
        if (index >= row.getCellCount()) {
            return "";
        }
        Cell cell = row.getCell(index);
        return (cell != null && cell.getText() != null) ? cell.getText().trim() : "";
    }

    private Double parseDoubleSeguro(String val) {
        if (val == null || val.isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(val.replace(",", "."));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private Integer parseIntSeguro(String val) {
        if (val == null || val.isEmpty()) {
            return 0;
        }
        try {
            if (val.contains(".")) {
                return (int) Double.parseDouble(val);
            }
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
