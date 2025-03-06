package com.fgr.apirest.dto.converter;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.fgr.apirest.dto.EvaluacionDTO;
import com.fgr.apirest.entity.Evaluacion;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EvaluacionDTOConverter {

    private final ModelMapper modelMapper;

    public EvaluacionDTO convertToDto(Evaluacion evaluacion) {
        return modelMapper.map(evaluacion, EvaluacionDTO.class);
    }

    public Evaluacion convertToEntity(EvaluacionDTO evaluacionDTO) {
        return modelMapper.map(evaluacionDTO, Evaluacion.class);
    }
}

