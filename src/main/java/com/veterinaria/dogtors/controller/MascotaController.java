package com.veterinaria.dogtors.controller;

import com.veterinaria.dogtors.entities.Dueno;
import com.veterinaria.dogtors.entities.Mascota;
import com.veterinaria.dogtors.entities.Tratamiento;
import com.veterinaria.dogtors.service.DuenoService;
import com.veterinaria.dogtors.service.MascotaService;
import com.veterinaria.dogtors.service.TratamientoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de mascotas, búsqueda por nombre, cambio de estado
 * y consulta desacoplada de dueño y tratamientos (sin devolver objetos anidados).
 */
@RestController
@RequestMapping("/mascotas")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private TratamientoService tratamientoService;

    @Autowired
    private DuenoService duenoService;

    @GetMapping
    @Operation(summary = "Mostrar todas las mascotas")
    public List<Mascota> mostrarTodasLasMascotas() {
        return mascotaService.buscarTodas();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar mascotas por nombre")
    public List<Mascota> buscarPorNombre(@RequestParam(value = "nombre", required = false) String nombre) {
        return mascotaService.buscarPorNombre(nombre);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Mostrar detalle de una mascota")
    public Mascota mostrarDetalleMascota(@PathVariable("id") Long id) {
        return mascotaService.buscarPorId(id);
    }

    @GetMapping("/{id}/dueno")
    @Operation(summary = "Obtener el dueño asociado a una mascota (consulta paso a paso sin objetos anidados)")
    public Dueno mostrarDuenoDeMascota(@PathVariable("id") Long id) {
        Mascota mascota = mascotaService.buscarPorId(id);
        Long duenoId = mascota.getDuenoId();
        return duenoService.findById(duenoId);
    }

    @GetMapping("/dueno/{duenoId}")
    @Operation(summary = "Mostrar mascotas asociadas a un dueño")
    public List<Mascota> mostrarMascotasPorDueno(@PathVariable("duenoId") Long duenoId) {
        return mascotaService.buscarPorDuenoId(duenoId);
    }

    @GetMapping("/{id}/tratamientos")
    @Operation(summary = "Mostrar los tratamientos asignados a una mascota")
    public List<Tratamiento> mostrarTratamientosMascota(@PathVariable("id") Long id) {
        return tratamientoService.buscarPorMascotaId(id);
    }

    @PostMapping
    @Operation(summary = "Agregar una mascota")
    public void agregarMascota(@RequestBody Mascota mascota) {
        mascotaService.save(mascota);
    }

    @PutMapping
    @Operation(summary = "Actualizar una mascota")
    public void actualizarMascota(@RequestBody Mascota mascota) {
        mascotaService.save(mascota);
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de activa/inactiva de una mascota")
    public void cambiarEstadoMascota(@PathVariable("id") Long id) {
        mascotaService.cambiarEstado(id);
    }

    @GetMapping("/cambiar-estado/{id}")
    @Operation(summary = "Cambiar estado de activa/inactiva (GET alternativo)")
    public void cambiarEstadoMascotaGet(@PathVariable("id") Long id) {
        mascotaService.cambiarEstado(id);
    }
}
