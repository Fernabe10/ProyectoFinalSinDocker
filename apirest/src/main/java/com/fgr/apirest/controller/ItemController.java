package com.fgr.apirest.controller;

import com.fgr.apirest.dto.ItemDTO;
import com.fgr.apirest.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/item")
@Tag(name = "Ítems", description = "API para gestionar ítems")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    @Operation(summary = "Traer todos los ítems", description = "Devuelve una lista de todos los ítems registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
    })
    public List<ItemDTO> getAll() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Traer un ítem por ID", description = "Devuelve un ítem específico según su ID")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Ítem encontrado",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = ItemDTO.class))),
    @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    public ResponseEntity<ItemDTO> getById(
        @Parameter(description = "ID del ítem a obtener", required = true)
        @PathVariable Integer id) {
    
    ItemDTO item = itemService.findById(id);
    return ResponseEntity.ok(item); 
    }


    @PostMapping
    @Operation(summary = "Insertar un nuevo ítem", description = "Registra un nuevo ítem en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ítem creado con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<?> create(
            @Parameter(description = "Datos del nuevo ítem", required = true)
            @Valid @RequestBody ItemDTO itemDTO) {
        Optional<ItemDTO> savedItem = itemService.save(itemDTO);
    
        if (savedItem.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo crear el ítem porque la prueba asociada no existe.");
        }
    
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem.get());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un ítem", description = "Actualiza un ítem existente por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ítem actualizado con éxito"),
        @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID del ítem a actualizar", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Datos actualizados del ítem", required = true)
            @Valid @RequestBody ItemDTO itemDTO) {
        Optional<ItemDTO> updatedItem = itemService.update(id, itemDTO);
    
        if (updatedItem.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el ítem o la prueba asociada no existe.");
        }

        return ResponseEntity.ok(updatedItem.get());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un ítem", description = "Elimina un ítem específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ítem eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del ítem a eliminar", required = true)
            @PathVariable Integer id) {
        return itemService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/prueba/{pruebaId}")
    @Operation(summary = "Traer ítems por ID de prueba", description = "Devuelve una lista de ítems asociados a una prueba específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ítems obtenida con éxito"),
        @ApiResponse(responseCode = "204", description = "No se encontraron ítems para la prueba")
    })
    public ResponseEntity<List<ItemDTO>> getByPruebaId(
            @Parameter(description = "ID de la prueba a obtener los ítems", required = true)
            @PathVariable("pruebaId") Integer pruebaId) {
        List<ItemDTO> items = itemService.findByPruebaId(pruebaId);
        return items.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(items);
    }
}
