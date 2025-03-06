package com.fgr.apirest.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionDTO {
    private Integer idEvaluacion;
    private Integer participanteId;
    private Integer pruebaId;
    private Integer userId;
    private Double notaFinal;
}

