package com.fgr.apirest.controller;

import com.fgr.apirest.dto.UserDTO;
import com.fgr.apirest.service.UserService;
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
@RequestMapping("/api/experto")
@Tag(name = "Experto", description = "API para gestionar los expertos")
public class ExpertoController {

    private final UserService userService;

    public ExpertoController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Traer todos los expertos", description = "Devuelve una lista de todos los usuarios con rol EXPERTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<UserDTO> getAllExpertos() {
        return userService.findAllByRole("EXPERTO");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Traer un experto por ID", description = "Devuelve un usuario con rol EXPERTO según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Experto obtenido con éxito",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "404", description = "Experto no encontrado")
    })
    public ResponseEntity<UserDTO> getExpertoById(
            @Parameter(description = "ID del experto a obtener", required = true)
            @PathVariable Integer id) {
        Optional<UserDTO> experto = userService.findById(id);
        return experto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un experto", description = "Modifica un usuario con rol EXPERTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Experto actualizado con éxito",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "404", description = "Experto no encontrado")
    })
    public ResponseEntity<UserDTO> updateExperto(
            @Parameter(description = "ID del experto a actualizar", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Datos actualizados del experto", required = true)
            @RequestBody UserDTO userDTO) {
        Optional<UserDTO> updated = userService.update(id, userDTO);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un experto", description = "Elimina un usuario con rol EXPERTO por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Experto eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Experto no encontrado")
    })
    public ResponseEntity<Void> deleteExperto(
            @Parameter(description = "ID del experto a eliminar", required = true)
            @PathVariable Integer id) {
        return userService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
