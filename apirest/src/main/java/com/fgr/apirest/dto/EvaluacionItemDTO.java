package com.fgr.apirest.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionItemDTO {
    private Integer idEvaluacionItem;
    private Integer evaluacionId;
    private Integer itemId;
    private Integer valoracion;
    private String explicacion;
}

