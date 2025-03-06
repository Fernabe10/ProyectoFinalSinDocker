package com.fgr.apirest.dto.converter;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.fgr.apirest.dto.EspecialidadDTO;
import com.fgr.apirest.entity.Especialidad;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EspecialidadDTOConverter {

    private final ModelMapper modelMapper;


    public EspecialidadDTO convertToDto(Especialidad especialidad) {
		return modelMapper.map(especialidad, EspecialidadDTO.class);
		
	}
}

