package com.fgr.apirest.controller;

import com.fgr.apirest.dto.EvaluacionItemDTO;
import com.fgr.apirest.service.EvaluacionItemService;
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
@RequestMapping("/api/evaluacionitem")
@Tag(name = "EvaluacionItem", description = "API para gestionar los ítems de evaluación")
public class EvaluacionItemController {

    private final EvaluacionItemService evaluacionItemService;

    public EvaluacionItemController(EvaluacionItemService evaluacionItemService) {
        this.evaluacionItemService = evaluacionItemService;
    }

    @GetMapping
    @Operation(summary = "Traer todos los ítems de evaluación", description = "Devuelve una lista de todos los ítems de evaluación registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = EvaluacionItemDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<EvaluacionItemDTO> getAll() {
        return evaluacionItemService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Traer un ítem de evaluación por ID", description = "Devuelve un ítem de evaluación específico según su ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Ítem de evaluación obtenido con éxito",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = EvaluacionItemDTO.class))),
    @ApiResponse(responseCode = "404", description = "Ítem de evaluación no encontrado")
    })
    public ResponseEntity<EvaluacionItemDTO> getById(
        @Parameter(description = "ID del ítem de evaluación a obtener", required = true)
        @PathVariable Integer id) {
    
    EvaluacionItemDTO evaluacionItem = evaluacionItemService.findById(id);
    return ResponseEntity.ok(evaluacionItem);
}


    @PostMapping
    @Operation(summary = "Insertar un nuevo ítem de evaluación", description = "Registra un nuevo ítem de evaluación en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ítem de evaluación creado con éxito",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = EvaluacionItemDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<EvaluacionItemDTO> create(
            @Parameter(description = "Datos del ítem de evaluación a crear", required = true)
            @RequestBody EvaluacionItemDTO dto) {
        EvaluacionItemDTO created = evaluacionItemService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un ítem de evaluación", description = "Modifica un ítem de evaluación existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ítem de evaluación actualizado con éxito",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = EvaluacionItemDTO.class))),
        @ApiResponse(responseCode = "404", description = "Ítem de evaluación no encontrado")
    })
    public ResponseEntity<EvaluacionItemDTO> update(
            @Parameter(description = "ID del ítem de evaluación a actualizar", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Datos actualizados del ítem de evaluación", required = true)
            @RequestBody EvaluacionItemDTO dto) {
        Optional<EvaluacionItemDTO> updated = evaluacionItemService.update(id, dto);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un ítem de evaluación", description = "Elimina un ítem de evaluación específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ítem de evaluación eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Ítem de evaluación no encontrado")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del ítem de evaluación a eliminar", required = true)
            @PathVariable Integer id) {
        return evaluacionItemService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
