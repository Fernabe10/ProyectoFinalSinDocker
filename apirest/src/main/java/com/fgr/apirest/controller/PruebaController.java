package com.fgr.apirest.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fgr.apirest.dto.EspecialidadDTO;
import com.fgr.apirest.dto.PruebaDTO;
import com.fgr.apirest.service.PruebaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.core.io.Resource;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/prueba")
@Tag(name = "Pruebas", description = "API para gestionar pruebas")
public class PruebaController {

    private final PruebaService pruebaService;

    public PruebaController(PruebaService pruebaService) {
        this.pruebaService = pruebaService;
    }

    @GetMapping
    @Operation(summary = "Traer todas las pruebas", description = "Devuelve una lista de todas las pruebas registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
    })
    public ResponseEntity<List<PruebaDTO>> getAll() {
        List<PruebaDTO> pruebas = pruebaService.findAll();
        return ResponseEntity.ok(pruebas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Traer una prueba por ID", description = "Devuelve los detalles de una prueba específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prueba encontrada"),
        @ApiResponse(responseCode = "404", description = "Prueba no encontrada")
    })
    public ResponseEntity<PruebaDTO> getById(
            @Parameter(description = "ID de la prueba a obtener", required = true)
            @PathVariable Integer id) {
        return pruebaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Insertar una nueva prueba", description = "Permite registrar una nueva prueba con archivo adjunto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Prueba creada con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PruebaDTO> createWithFile(
            @Parameter(description = "Archivo PDF asociado a la prueba", required = true)
            @RequestParam("file") MultipartFile file, 
            @Parameter(description = "Puntuación máxima de la prueba", required = true)
            @RequestParam("puntuacionMaxima") Integer puntuacionMaxima, 
            @Parameter(description = "ID de la especialidad asociada", required = true)
            @RequestParam("idEspecialidad") Integer idEspecialidad) {
        try {
            // Guardar el archivo y obtener la ruta
            String filePath = pruebaService.saveFile(file);

            PruebaDTO pruebaDTO = new PruebaDTO();
            pruebaDTO.setEnunciado(filePath);  // Asigno el archivo a enunciado
            pruebaDTO.setPuntuacionMaxima(puntuacionMaxima);
            pruebaDTO.setEspecialidad(new EspecialidadDTO(idEspecialidad)); 

            // Guardar la prueba en la base de datos
            PruebaDTO createdPrueba = pruebaService.save(pruebaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPrueba);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una prueba existente", description = "Permite actualizar una prueba por ID, incluyendo el archivo adjunto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prueba actualizada con éxito"),
        @ApiResponse(responseCode = "404", description = "Prueba no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PruebaDTO> update(
            @Parameter(description = "ID de la prueba a actualizar", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Archivo PDF asociado a la prueba", required = false)
            @RequestParam(value = "file", required = false) MultipartFile file, 
            @Parameter(description = "Puntuación máxima de la prueba", required = true)
            @RequestParam("puntuacionMaxima") Integer puntuacionMaxima, 
            @Parameter(description = "ID de la especialidad asociada", required = true)
            @RequestParam("idEspecialidad") Integer idEspecialidad) {

        try {
            // Buscar si la prueba existe
            PruebaDTO pruebaDTO = pruebaService.findById(id).orElse(null);
            if (pruebaDTO == null) {
                return ResponseEntity.notFound().build();
            }

            if (file != null && !file.isEmpty()) {
                String filePath = pruebaService.saveFile(file);
                pruebaDTO.setEnunciado(filePath);
            }

            pruebaDTO.setPuntuacionMaxima(puntuacionMaxima);
            pruebaDTO.setEspecialidad(new EspecialidadDTO(idEspecialidad));

            // Actualizar la prueba
            PruebaDTO updatedPrueba = pruebaService.update(id, pruebaDTO)
                .orElseThrow(() -> new RuntimeException("No se pudo actualizar la prueba"));

            return ResponseEntity.ok(updatedPrueba);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una prueba", description = "Permite eliminar una prueba por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Prueba eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Prueba no encontrada")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la prueba a eliminar", required = true)
            @PathVariable Integer id) {
        return pruebaService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/file")
    @Operation(summary = "Traer archivo asociado a la prueba", description = "Permite descargar el archivo asociado a una prueba dada su ruta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Archivo descargado con éxito"),
        @ApiResponse(responseCode = "404", description = "Archivo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Resource> getFile(
            @Parameter(description = "Ruta del archivo a obtener", required = true)
            @RequestParam("path") String filePath) {
        try {
            Path path = Paths.get(filePath);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
