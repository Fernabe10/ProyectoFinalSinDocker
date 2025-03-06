package com.fgr.apirest.service;

import com.fgr.apirest.dto.EvaluacionDTO;
import com.fgr.apirest.entity.Evaluacion;
import com.fgr.apirest.entity.Participante;
import com.fgr.apirest.entity.Prueba;
import com.fgr.apirest.entity.User;
import com.fgr.apirest.exception.RecursoNoEncontradoException;
import com.fgr.apirest.repository.EvaluacionRepository;
import com.fgr.apirest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final UserRepository userRepository;

    public EvaluacionService(EvaluacionRepository evaluacionRepository, UserRepository userRepository) {
        this.evaluacionRepository = evaluacionRepository;
        this.userRepository = userRepository;
    }

    // traer todas las evaluaciones
    public List<EvaluacionDTO> findAll() {
        return evaluacionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Traer una evaluación por ID
    public EvaluacionDTO findById(Integer id) {
        return evaluacionRepository.findById(id)
            .map(this::convertToDTO)  // Convierte el resultado en un DTO
            .orElseThrow(() -> new RecursoNoEncontradoException("Evaluación con ID " + id + " no encontrada"));
    }


    // insertar una nueva evaluación
    public EvaluacionDTO save(EvaluacionDTO dto) {
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setNotaFinal(dto.getNotaFinal());

        Participante participante = new Participante();
        participante.setIdParticipante(dto.getParticipanteId());
        evaluacion.setParticipante(participante);

        Prueba prueba = new Prueba();
        prueba.setIdPrueba(dto.getPruebaId());
        evaluacion.setPrueba(prueba);

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            evaluacion.setUser(user);
        }

        Evaluacion saved = evaluacionRepository.save(evaluacion);
        return convertToDTO(saved);
    }

    // actualizar evaluación existente
    public Optional<EvaluacionDTO> update(Integer id, EvaluacionDTO dto) {
        Optional<Evaluacion> optionalEvaluacion = evaluacionRepository.findById(id);
        if (optionalEvaluacion.isPresent()) {
            Evaluacion evaluacion = optionalEvaluacion.get();
            evaluacion.setNotaFinal(dto.getNotaFinal());

            Participante participante = new Participante();
            participante.setIdParticipante(dto.getParticipanteId());
            evaluacion.setParticipante(participante);

            Prueba prueba = new Prueba();
            prueba.setIdPrueba(dto.getPruebaId());
            evaluacion.setPrueba(prueba);

            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                evaluacion.setUser(user);
            }

            Evaluacion updated = evaluacionRepository.save(evaluacion);
            return Optional.of(convertToDTO(updated));
        }
        return Optional.empty();
    }

    // eliminar evaluación
    public boolean delete(Integer id) {
        if (evaluacionRepository.existsById(id)) {
            evaluacionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // convertidor a DTO
    private EvaluacionDTO convertToDTO(Evaluacion evaluacion) {
        EvaluacionDTO dto = new EvaluacionDTO();
        dto.setIdEvaluacion(evaluacion.getIdEvaluacion());
        dto.setNotaFinal(evaluacion.getNotaFinal());
        dto.setParticipanteId(evaluacion.getParticipante().getIdParticipante());
        dto.setPruebaId(evaluacion.getPrueba().getIdPrueba());
        if (evaluacion.getUser() != null) {
            dto.setUserId(evaluacion.getUser().getIdUser());
        }
        return dto;
    }

    // traer la última evaluación (el último ID generado)
    public Optional<EvaluacionDTO> getLastEvaluacion() {
        Optional<Evaluacion> lastEvaluacion = evaluacionRepository.findTopByOrderByIdEvaluacionDesc();
        return lastEvaluacion.map(this::convertToDTO);
    }
}
