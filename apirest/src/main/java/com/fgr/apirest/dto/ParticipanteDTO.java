package com.fgr.apirest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipanteDTO {
    private Integer idParticipante;
    private String nombre;
    private String apellidos;
    private String centro;
    private EspecialidadDTO especialidad;
}

