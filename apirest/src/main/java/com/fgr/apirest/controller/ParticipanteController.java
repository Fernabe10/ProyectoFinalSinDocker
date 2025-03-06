package com.fgr.apirest.controller;

import com.fgr.apirest.dto.ParticipanteDTO;
import com.fgr.apirest.service.ParticipanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/participante")
@Tag(name = "Participantes", description = "API para gestionar participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;

    public ParticipanteController(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    @GetMapping
    @Operation(summary = "Traer todos los participantes", description = "Devuelve una lista de todos los participantes registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
    })
    public List<ParticipanteDTO> getAll() {
        return participanteService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Traer un participante por ID", description = "Devuelve un participante específico según su ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Participante encontrado",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = ParticipanteDTO.class))),
    @ApiResponse(responseCode = "404", description = "Participante no encontrado")
    })
    public ResponseEntity<ParticipanteDTO> getById(
        @Parameter(description = "ID del participante a obtener", required = true)
        @PathVariable Integer id) {
    
    ParticipanteDTO participante = participanteService.findById(id);
    return ResponseEntity.ok(participante);
}


    @PostMapping
    @Operation(summary = "Insertar un nuevo participante", description = "Registra un nuevo participante en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Participante creado con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ParticipanteDTO create(
            @Parameter(description = "Datos del nuevo participante", required = true)
            @RequestBody ParticipanteDTO participanteDTO) {
        return participanteService.save(participanteDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un participante", description = "Actualiza un participante existente por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Participante actualizado con éxito"),
        @ApiResponse(responseCode = "404", description = "Participante no encontrado")
    })
    public ResponseEntity<ParticipanteDTO> update(
            @Parameter(description = "ID del participante a actualizar", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Datos actualizados del participante", required = true)
            @RequestBody ParticipanteDTO participanteDTO) {
        Optional<ParticipanteDTO> updated = participanteService.update(id, participanteDTO);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un participante", description = "Elimina un participante específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Participante eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Participante no encontrado")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del participante a eliminar", required = true)
            @PathVariable Integer id) {
        return participanteService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/especialidad/{especialidadId}")
    @Operation(summary = "Traer participantes por especialidad", description = "Devuelve una lista de participantes asociados a una especialidad")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito"),
        @ApiResponse(responseCode = "204", description = "No hay participantes para esta especialidad")
    })
    public ResponseEntity<List<ParticipanteDTO>> obtenerPorEspecialidad(
            @Parameter(description = "ID de la especialidad", required = true)
            @PathVariable Integer especialidadId) {
        List<ParticipanteDTO> participantes = participanteService.obtenerPorEspecialidad(especialidadId);
        if (participantes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(participantes);
    }
}
