package com.veterinaria.dogtors.service;

import com.veterinaria.dogtors.entities.Droga;
import com.veterinaria.dogtors.errors.NotFoundException;
import com.veterinaria.dogtors.repository.DrogaRepository;
import com.veterinaria.dogtors.repository.TratamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class DrogaServiceImpl implements DrogaService {

    @Autowired
    private DrogaRepository drogaRepository;

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private ExcelService excelService;

    @Override
    public List<Droga> buscarTodas() {
        return drogaRepository.findAll();
    }

    @Override
    public List<Droga> buscarDisponibles() {
        return drogaRepository.findByUnidadesDisponiblesGreaterThan(0);
    }

    @Override
    public Droga buscarPorId(Long id) {
        return drogaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medicamento no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Droga guardar(Droga droga) {
        return drogaRepository.save(droga);
    }

    @Override
    @Transactional
    public int cargarDesdeExcel(MultipartFile archivo) {
        return cargarDesdeExcel(archivo, false);
    }

    @Override
    @Transactional
    public int cargarDesdeExcel(MultipartFile archivo, boolean sobrescribir) {
        if (sobrescribir) {
            limpiarCatalogo();
        }
        List<Droga> drogasLeidas = excelService.leerDrogasDesdeMultipart(archivo);
        return persistirDrogas(drogasLeidas);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Droga droga = drogaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medicamento no encontrado con ID: " + id));
        List<com.veterinaria.dogtors.entities.Tratamiento> tratamientos = tratamientoRepository.findByDrogaId(id);
        for (com.veterinaria.dogtors.entities.Tratamiento t : tratamientos) {
            t.setDroga(null);
            tratamientoRepository.save(t);
        }
        drogaRepository.delete(droga);
    }

    @Override
    @Transactional
    public void limpiarCatalogo() {
        List<com.veterinaria.dogtors.entities.Tratamiento> tratamientos = tratamientoRepository.findAll();
        for (com.veterinaria.dogtors.entities.Tratamiento t : tratamientos) {
            if (t.getDroga() != null) {
                t.setDroga(null);
                tratamientoRepository.save(t);
            }
        }
        drogaRepository.deleteAll();
    }

    @Override
    @Transactional
    public int cargarDesdeExcelServidor(String ruta) {
        String rutaFinal = (ruta != null && !ruta.trim().isEmpty()) ? ruta : "other/Medicamentos.xlsx";
        File archivo = new File(rutaFinal);
        if (!archivo.exists()) {
            throw new NotFoundException("No se encontró el archivo de medicamentos en la ruta del servidor: " + archivo.getAbsolutePath());
        }
        List<Droga> drogasLeidas = excelService.leerDrogasDesdeArchivo(archivo);
        return persistirDrogas(drogasLeidas);
    }

    private int persistirDrogas(List<Droga> drogas) {
        int contador = 0;
        for (Droga d : drogas) {
            if (d.getNombre() == null || d.getNombre().trim().isEmpty()) {
                continue;
            }
            Optional<Droga> existente = drogaRepository.findByNombreIgnoreCase(d.getNombre().trim());
            if (existente.isPresent()) {
                Droga actual = existente.get();
                actual.setPrecioVenta(d.getPrecioVenta());
                actual.setPrecioCompra(d.getPrecioCompra());
                actual.setUnidadesDisponibles(d.getUnidadesDisponibles());
                actual.setUnidadesVendidas(d.getUnidadesVendidas());
                drogaRepository.save(actual);
            } else {
                drogaRepository.save(d);
            }
            contador++;
        }
        return contador;
    }
}

