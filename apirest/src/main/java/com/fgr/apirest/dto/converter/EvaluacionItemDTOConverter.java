package com.fgr.apirest.dto.converter;



import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.fgr.apirest.dto.EvaluacionItemDTO;
import com.fgr.apirest.entity.EvaluacionItem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EvaluacionItemDTOConverter {

    private final ModelMapper modelMapper;

    public EvaluacionItemDTO convertToDto(EvaluacionItem evaluacionItem) {
        return modelMapper.map(evaluacionItem, EvaluacionItemDTO.class);
    }

    public EvaluacionItem convertToEntity(EvaluacionItemDTO evaluacionItemDTO) {
        return modelMapper.map(evaluacionItemDTO, EvaluacionItem.class);
    }
}

