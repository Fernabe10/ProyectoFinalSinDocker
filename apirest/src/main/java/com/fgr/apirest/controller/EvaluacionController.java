package com.fgr.apirest.controller;

import com.fgr.apirest.dto.EvaluacionDTO;
import com.fgr.apirest.service.EvaluacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evaluacion")
@Tag(name = "Evaluaciones", description = "API para gestionar las evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    public EvaluacionController(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    @GetMapping
    @Operation(summary = "Traer todas las evaluaciones", description = "Devuelve una lista de todas las evaluaciones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de evaluaciones obtenida con éxito",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = EvaluacionDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<EvaluacionDTO> getAll() {
        return evaluacionService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Traer una evaluación por ID", description = "Devuelve una evaluación específica según su ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Evaluación obtenida con éxito",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = EvaluacionDTO.class))),
    @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    public ResponseEntity<EvaluacionDTO> getById(
        @Parameter(description = "ID de la evaluación a obtener", required = true)
        @PathVariable Integer id) {
    
    EvaluacionDTO evaluacion = evaluacionService.findById(id);
    return ResponseEntity.ok(evaluacion);
    }


    @PostMapping
    @Operation(summary = "Insertar una nueva evaluación", description = "Registra una nueva evaluación en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Evaluación creada con éxito",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = EvaluacionDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<EvaluacionDTO> create(
            @Parameter(description = "Datos de la evaluación a crear", required = true)
            @RequestBody EvaluacionDTO dto) {
        EvaluacionDTO created = evaluacionService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una evaluación", description = "Modifica una evaluación existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evaluación actualizada con éxito",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = EvaluacionDTO.class))),
        @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    public ResponseEntity<EvaluacionDTO> update(
            @Parameter(description = "ID de la evaluación a actualizar", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Datos actualizados de la evaluación", required = true)
            @RequestBody EvaluacionDTO dto) {
        Optional<EvaluacionDTO> updated = evaluacionService.update(id, dto);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una evaluación", description = "Elimina una evaluación específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Evaluación eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la evaluación a eliminar", required = true)
            @PathVariable Integer id) {
        return evaluacionService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/ultimo")
    @Operation(summary = "Traer la última evaluación registrada", description = "Devuelve la última evaluación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Última evaluación obtenida con éxito",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = EvaluacionDTO.class))),
        @ApiResponse(responseCode = "404", description = "No hay evaluaciones registradas")
    })
    public ResponseEntity<EvaluacionDTO> getLastEvaluacion() {
        Optional<EvaluacionDTO> lastEvaluacion = evaluacionService.getLastEvaluacion();
        return lastEvaluacion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
