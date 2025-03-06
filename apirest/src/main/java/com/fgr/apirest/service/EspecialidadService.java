package com.fgr.apirest.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fgr.apirest.dto.EspecialidadDTO;
import com.fgr.apirest.entity.Especialidad;
import com.fgr.apirest.exception.RecursoNoEncontradoException;
import com.fgr.apirest.repository.EspecialidadRepository;


@Service
public class EspecialidadService {
    
    private final EspecialidadRepository repository;

    public EspecialidadService(EspecialidadRepository repository){
        this.repository = repository;
    }

    // traer todas las especialidades
    public List<EspecialidadDTO> findAll() {
        List<Especialidad> especialidades = repository.findAll();
        return especialidades.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // buscar especialidad por ID
    public EspecialidadDTO findById(Integer id) {
        return repository.findById(id)
        .map(this::convertToDTO)  // Convierte el resultado en un DTO
        .orElseThrow(() -> new RecursoNoEncontradoException("Especialidad con ID " + id + " no encontrada"));
    }


    // insertar una nueva especialidad
    public EspecialidadDTO save(EspecialidadDTO especialidadDTO) {
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(especialidadDTO.getNombre());
        especialidad.setCodigo(especialidadDTO.getCodigo());

        Especialidad savedEspecialidad = repository.save(especialidad);
        return convertToDTO(savedEspecialidad);
    }

    // actualizar especialidad existente
    public Optional<EspecialidadDTO> update(Integer id, EspecialidadDTO especialidadDTO) {
        Optional<Especialidad> optionalEspecialidad = repository.findById(id);
        if (optionalEspecialidad.isPresent()) {
            Especialidad especialidad = optionalEspecialidad.get();
            especialidad.setNombre(especialidadDTO.getNombre());
            especialidad.setCodigo(especialidadDTO.getCodigo());

            Especialidad updatedEspecialidad = repository.save(especialidad);
            return Optional.of(convertToDTO(updatedEspecialidad));
        }
        return Optional.empty();
    }

    // eliminar especialidad
    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // convertidor a DTO
    private EspecialidadDTO convertToDTO(Especialidad especialidad) {
        EspecialidadDTO dto = new EspecialidadDTO();
        dto.setIdEspecialidad(especialidad.getIdEspecialidad());
        dto.setNombre(especialidad.getNombre());
        dto.setCodigo(especialidad.getCodigo());
        return dto;
    }
}
