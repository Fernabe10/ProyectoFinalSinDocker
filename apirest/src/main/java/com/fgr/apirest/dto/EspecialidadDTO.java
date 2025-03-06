package com.fgr.apirest.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EspecialidadDTO {
    private Integer idEspecialidad;
    private String codigo;
    private String nombre;

    public EspecialidadDTO(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }
}

