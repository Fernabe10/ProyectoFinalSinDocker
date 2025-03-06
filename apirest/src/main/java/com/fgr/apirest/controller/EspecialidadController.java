package com.fgr.apirest.controller;

import com.fgr.apirest.dto.EspecialidadDTO;
import com.fgr.apirest.service.EspecialidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/especialidad")
@Tag(name = "Especialidades", description = "API para gestionar especialidades")
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @GetMapping
    @Operation(summary = "Traer todas las especialidades", description = "Devuelve una lista de todas las especialidades registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
    })
    public List<EspecialidadDTO> getAll() {
        return especialidadService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Traer una especialidad por ID", description = "Devuelve una especialidad específica según su ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Especialidad encontrada"),
    @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })
    public ResponseEntity<EspecialidadDTO> getById(
        @Parameter(description = "ID de la especialidad a obtener", required = true)
        @PathVariable Integer id) {
    
        EspecialidadDTO especialidad = especialidadService.findById(id);
        return ResponseEntity.ok(especialidad);
}


    @PostMapping
    @Operation(summary = "Insertar una nueva especialidad", description = "Registra una nueva especialidad en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Especialidad creada con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public EspecialidadDTO create(
            @Parameter(description = "Datos de la nueva especialidad", required = true)
            @RequestBody EspecialidadDTO especialidadDTO) {
        return especialidadService.save(especialidadDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una especialidad", description = "Actualiza una especialidad existente por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especialidad actualizada con éxito"),
        @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })
    public ResponseEntity<EspecialidadDTO> update(
            @Parameter(description = "ID de la especialidad a actualizar", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Datos actualizados de la especialidad", required = true)
            @RequestBody EspecialidadDTO especialidadDTO) {
        Optional<EspecialidadDTO> updated = especialidadService.update(id, especialidadDTO);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una especialidad", description = "Elimina una especialidad específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Especialidad eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Especialidad no encontrada")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la especialidad a eliminar", required = true)
            @PathVariable Integer id) {
        return especialidadService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
