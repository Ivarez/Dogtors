package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.entities.Droga;
import com.veterinaria.dogtors.service.DrogaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/drogas")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
public class DrogaController {

    @Autowired
    private DrogaService drogaService;

    @GetMapping
    @Operation(summary = "Listar todos los medicamentos")
    public List<Droga> listarTodas() {
        return drogaService.buscarTodas();
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Listar únicamente medicamentos con unidades disponibles (> 0)")
    public List<Droga> listarDisponibles() {
        return drogaService.buscarDisponibles();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un medicamento por ID")
    public Droga buscarPorId(@PathVariable Long id) {
        return drogaService.buscarPorId(id);
    }

    @PostMapping(value = "/cargar-excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Cargar medicamentos desde un archivo Excel (.xlsx)")
    public ResponseEntity<Map<String, Object>> cargarDesdeExcel(@RequestParam("archivo") MultipartFile archivo) {
        int cargados = drogaService.cargarDesdeExcel(archivo);
        Map<String, Object> resp = new HashMap<>();
        resp.put("status", 200);
        resp.put("mensaje", "Archivo Excel procesado correctamente.");
        resp.put("medicamentosCargados", cargados);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/cargar-excel-default")
    @Operation(summary = "Cargar automáticamente el Excel por defecto del servidor (other/Medicamentos.xlsx)")
    public ResponseEntity<Map<String, Object>> cargarExcelServidor() {
        int cargados = drogaService.cargarDesdeExcelServidor("other/Medicamentos.xlsx");
        Map<String, Object> resp = new HashMap<>();
        resp.put("status", 200);
        resp.put("mensaje", "Medicamentos cargados desde el archivo del servidor con éxito.");
        resp.put("medicamentosCargados", cargados);
        return ResponseEntity.ok(resp);
    }
}

