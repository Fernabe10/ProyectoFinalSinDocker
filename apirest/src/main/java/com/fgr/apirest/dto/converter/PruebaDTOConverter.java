package com.fgr.apirest.dto.converter;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.fgr.apirest.dto.PruebaDTO;
import com.fgr.apirest.entity.Prueba;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PruebaDTOConverter {

    private final ModelMapper modelMapper;

    public PruebaDTO convertToDto(Prueba prueba) {
        return modelMapper.map(prueba, PruebaDTO.class);
    }

    public Prueba convertToEntity(PruebaDTO pruebaDTO) {
        return modelMapper.map(pruebaDTO, Prueba.class);
    }
}

