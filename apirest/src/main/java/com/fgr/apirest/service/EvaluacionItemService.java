package com.fgr.apirest.service;

import com.fgr.apirest.dto.EvaluacionItemDTO;
import com.fgr.apirest.entity.Evaluacion;
import com.fgr.apirest.entity.EvaluacionItem;
import com.fgr.apirest.entity.Item;
import com.fgr.apirest.exception.RecursoNoEncontradoException;
import com.fgr.apirest.repository.EvaluacionItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionItemService {

    private final EvaluacionItemRepository evaluacionItemRepository;

    public EvaluacionItemService(EvaluacionItemRepository evaluacionItemRepository) {
        this.evaluacionItemRepository = evaluacionItemRepository;
    }

    // traer todas las evaluaciones de ítems
    public List<EvaluacionItemDTO> findAll() {
        return evaluacionItemRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar evaluación de ítem por ID
    public EvaluacionItemDTO findById(Integer id) {
        return evaluacionItemRepository.findById(id)
            .map(this::convertToDTO)
            .orElseThrow(() -> new RecursoNoEncontradoException("Evaluación de ítem con ID " + id + " no encontrada"));
    }

    // insertar una nueva evaluación de ítem
    public EvaluacionItemDTO save(EvaluacionItemDTO dto) {
        EvaluacionItem evaluacionItem = new EvaluacionItem();
        evaluacionItem.setValoracion(dto.getValoracion());
        evaluacionItem.setExplicacion(dto.getExplicacion());

        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setIdEvaluacion(dto.getEvaluacionId());
        evaluacionItem.setEvaluacion(evaluacion);

        Item item = new Item();
        item.setIdItem(dto.getItemId());
        evaluacionItem.setItem(item);

        EvaluacionItem saved = evaluacionItemRepository.save(evaluacionItem);
        return convertToDTO(saved);
    }

    // actualizar evaluación de ítem existente
    public Optional<EvaluacionItemDTO> update(Integer id, EvaluacionItemDTO dto) {
        Optional<EvaluacionItem> optionalEvaluacionItem = evaluacionItemRepository.findById(id);
        if (optionalEvaluacionItem.isPresent()) {
            EvaluacionItem evaluacionItem = optionalEvaluacionItem.get();
            evaluacionItem.setValoracion(dto.getValoracion());
            evaluacionItem.setExplicacion(dto.getExplicacion());

            Evaluacion evaluacion = new Evaluacion();
            evaluacion.setIdEvaluacion(dto.getEvaluacionId());
            evaluacionItem.setEvaluacion(evaluacion);

            Item item = new Item();
            item.setIdItem(dto.getItemId());
            evaluacionItem.setItem(item);

            EvaluacionItem updated = evaluacionItemRepository.save(evaluacionItem);
            return Optional.of(convertToDTO(updated));
        }
        return Optional.empty();
    }

    // eliminar evaluación de ítem
    public boolean delete(Integer id) {
        if (evaluacionItemRepository.existsById(id)) {
            evaluacionItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // convertidor a DTO
    private EvaluacionItemDTO convertToDTO(EvaluacionItem evaluacionItem) {
        EvaluacionItemDTO dto = new EvaluacionItemDTO();
        dto.setIdEvaluacionItem(evaluacionItem.getIdEvaluacionItem());
        dto.setEvaluacionId(evaluacionItem.getEvaluacion().getIdEvaluacion());
        dto.setItemId(evaluacionItem.getItem().getIdItem());
        dto.setValoracion(evaluacionItem.getValoracion());
        dto.setExplicacion(evaluacionItem.getExplicacion());
        return dto;
    }
}
