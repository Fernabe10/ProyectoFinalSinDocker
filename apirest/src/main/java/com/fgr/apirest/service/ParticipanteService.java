package com.fgr.apirest.service;

import com.fgr.apirest.dto.EspecialidadDTO;
import com.fgr.apirest.dto.ParticipanteDTO;
import com.fgr.apirest.entity.Especialidad;
import com.fgr.apirest.entity.Participante;
import com.fgr.apirest.exception.RecursoNoEncontradoException;
import com.fgr.apirest.repository.EspecialidadRepository;
import com.fgr.apirest.repository.ParticipanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;
    private final EspecialidadRepository especialidadRepository;

    public ParticipanteService(ParticipanteRepository participanteRepository, EspecialidadRepository especialidadRepository) {
        this.participanteRepository = participanteRepository;
        this.especialidadRepository = especialidadRepository;
    }

    // traer todos los participantes
    public List<ParticipanteDTO> findAll() {
        return participanteRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Traer participante por ID
    public ParticipanteDTO findById(Integer id) {
        return participanteRepository.findById(id)
            .map(this::convertToDTO)
            .orElseThrow(() -> new RecursoNoEncontradoException("Participante con ID " + id + " no encontrado"));
    }


    // insertar un nuevo participante
    public ParticipanteDTO save(ParticipanteDTO participanteDTO) {
        Participante participante = new Participante();
        participante.setNombre(participanteDTO.getNombre());
        participante.setApellidos(participanteDTO.getApellidos());
        participante.setCentro(participanteDTO.getCentro());

        if (participanteDTO.getEspecialidad() != null) {
            Especialidad especialidad = especialidadRepository.findById(participanteDTO.getEspecialidad().getIdEspecialidad())
                    .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
            participante.setEspecialidad(especialidad);
        }

        Participante savedParticipante = participanteRepository.save(participante);
        return convertToDTO(savedParticipante);
    }

    // actualizar participante existente
    public Optional<ParticipanteDTO> update(Integer id, ParticipanteDTO participanteDTO) {
        return participanteRepository.findById(id).map(existingParticipante -> {
            existingParticipante.setNombre(participanteDTO.getNombre());
            existingParticipante.setApellidos(participanteDTO.getApellidos());
            existingParticipante.setCentro(participanteDTO.getCentro());

            if (participanteDTO.getEspecialidad() != null) {
                Especialidad especialidad = especialidadRepository.findById(participanteDTO.getEspecialidad().getIdEspecialidad())
                        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
                existingParticipante.setEspecialidad(especialidad);
            } else {
                existingParticipante.setEspecialidad(null);
            }

            Participante updatedParticipante = participanteRepository.save(existingParticipante);
            return convertToDTO(updatedParticipante);
        });
    }

    // eliminar participante
    public boolean delete(Integer id) {
        if (participanteRepository.existsById(id)) {
            participanteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // convertidor a DTO
    private ParticipanteDTO convertToDTO(Participante participante) {
        ParticipanteDTO dto = new ParticipanteDTO();
        dto.setIdParticipante(participante.getIdParticipante());
        dto.setNombre(participante.getNombre());
        dto.setApellidos(participante.getApellidos());
        dto.setCentro(participante.getCentro());

        if (participante.getEspecialidad() != null) {
            dto.setEspecialidad(new EspecialidadDTO(
                    participante.getEspecialidad().getIdEspecialidad(),
                    participante.getEspecialidad().getCodigo(),
                    participante.getEspecialidad().getNombre()
            ));
        } else {
            dto.setEspecialidad(null);
        }

        return dto;
    }


    //obtener un listado de participantes por la id de especialidad
    public List<ParticipanteDTO> obtenerPorEspecialidad(Integer especialidadId) {
        return participanteRepository.findByEspecialidad_IdEspecialidad(especialidadId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
}
