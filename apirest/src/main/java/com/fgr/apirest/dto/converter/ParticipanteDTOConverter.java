package com.fgr.apirest.dto.converter;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.fgr.apirest.dto.ParticipanteDTO;
import com.fgr.apirest.entity.Participante;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ParticipanteDTOConverter {

    private final ModelMapper modelMapper;

    public ParticipanteDTO convertToDto(Participante participante) {
        return modelMapper.map(participante, ParticipanteDTO.class);
    }

    public Participante convertToEntity(ParticipanteDTO participanteDTO) {
        return modelMapper.map(participanteDTO, Participante.class);
    }
}
