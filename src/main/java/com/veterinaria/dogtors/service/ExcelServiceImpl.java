package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Droga;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public List<Droga> leerDrogasDesdeMultipart(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("El archivo Excel subido está vacío o no se ha proporcionado.");
        }
        try {
            File tempFile = File.createTempFile("medicamentos-upload-", ".xlsx");
            try {
                multipartFile.transferTo(tempFile);
                return leerDrogasDesdeArchivo(tempFile);
            } finally {
                if (tempFile.exists()) {
                    tempFile.delete();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo Excel subido: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Droga> leerDrogasDesdeExcel(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("El stream del archivo Excel no puede ser nulo.");
        }
        try {
            File tempFile = File.createTempFile("medicamentos-stream-", ".xlsx");
            try (OutputStream out = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            try {
                return leerDrogasDesdeArchivo(tempFile);
            } finally {
                if (tempFile.exists()) {
                    tempFile.delete();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo Excel: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Droga> leerDrogasDesdeArchivo(File archivo) {
        if (archivo == null || !archivo.exists()) {
            throw new IllegalArgumentException("El archivo Excel no existe: " + (archivo != null ? archivo.getAbsolutePath() : "null"));
        }

        List<Droga> drogas = new ArrayList<>();
        List<String> sharedStrings = new ArrayList<>();

        try (ZipFile zip = new ZipFile(archivo)) {
            // 1. Leer sharedStrings.xml si existe
            ZipEntry sstEntry = zip.getEntry("xl/sharedStrings.xml");
            if (sstEntry != null) {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(zip.getInputStream(sstEntry));
                NodeList siNodes = doc.getElementsByTagName("si");
                for (int i = 0; i < siNodes.getLength(); i++) {
                    Element si = (Element) siNodes.item(i);
                    NodeList tNodes = si.getElementsByTagName("t");
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < tNodes.getLength(); j++) {
                        sb.append(tNodes.item(j).getTextContent());
                    }
                    sharedStrings.add(sb.toString().trim());
                }
            }

            // 2. Leer primera hoja de cálculo
            ZipEntry sheetEntry = zip.getEntry("xl/worksheets/sheet1.xml");
            if (sheetEntry == null) {
                throw new RuntimeException("No se encontró la hoja de cálculo 'sheet1.xml' dentro del archivo .xlsx");
            }

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(zip.getInputStream(sheetEntry));
            NodeList rowNodes = doc.getElementsByTagName("row");

            for (int i = 0; i < rowNodes.getLength(); i++) {
                Element rowElem = (Element) rowNodes.item(i);
                NodeList cellNodes = rowElem.getElementsByTagName("c");
                if (cellNodes.getLength() == 0) {
                    continue;
                }

                Map<Integer, String> cellValues = new HashMap<>();

                for (int j = 0; j < cellNodes.getLength(); j++) {
                    Element cell = (Element) cellNodes.item(j);
                    String ref = cell.getAttribute("r"); // Ej: "A2", "B2"
                    String type = cell.getAttribute("t"); // "s" para shared string
                    NodeList vNodes = cell.getElementsByTagName("v");
                    String rawVal = (vNodes.getLength() > 0) ? vNodes.item(0).getTextContent().trim() : "";

                    if ("s".equals(type) && !rawVal.isEmpty()) {
                        try {
                            int sIndex = Integer.parseInt(rawVal);
                            if (sIndex >= 0 && sIndex < sharedStrings.size()) {
                                rawVal = sharedStrings.get(sIndex);
                            }
                        } catch (NumberFormatException ignored) {
                        }
                    }

                    int colIndex = extraerIndiceColumna(ref);
                    cellValues.put(colIndex, rawVal);
                }

                String nombre = cellValues.get(0);
                // Si es la fila de encabezado o el nombre está vacío, saltar
                if (nombre == null || nombre.isEmpty() || nombre.equalsIgnoreCase("NOMBRE")) {
                    continue;
                }

                Double precioVenta = parseDoubleSeguro(cellValues.get(1));
                Double precioCompra = parseDoubleSeguro(cellValues.get(2));
                Integer unidadesDisponibles = parseIntSeguro(cellValues.get(3));
                Integer unidadesVendidas = parseIntSeguro(cellValues.get(4));

                Droga droga = new Droga(
                        nombre,
                        precioVenta != null ? precioVenta : 0.0,
                        precioCompra != null ? precioCompra : 0.0,
                        unidadesDisponibles != null ? unidadesDisponibles : 0,
                        unidadesVendidas != null ? unidadesVendidas : 0
                );
                drogas.add(droga);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al analizar el contenido del archivo Excel: " + e.getMessage(), e);
        }

        return drogas;
    }

    private int extraerIndiceColumna(String ref) {
        if (ref == null || ref.isEmpty()) {
            return 0;
        }
        int col = 0;
        for (int i = 0; i < ref.length(); i++) {
            char ch = ref.charAt(i);
            if (Character.isLetter(ch)) {
                col = col * 26 + (Character.toUpperCase(ch) - 'A' + 1);
            } else {
                break;
            }
        }
        return col - 1; // 0-based
    }

    private Double parseDoubleSeguro(String val) {
        if (val == null || val.trim().isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(val.replace(",", "."));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private Integer parseIntSeguro(String val) {
        if (val == null || val.trim().isEmpty()) {
            return 0;
        }
        try {
            // Manejar si viene como decimal "4.0"
            if (val.contains(".")) {
                return (int) Double.parseDouble(val);
            }
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

