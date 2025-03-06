package com.fgr.apirest.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PruebaDTO {
    private Integer idPrueba;
    private String enunciado;
    private EspecialidadDTO especialidad;
    private Integer puntuacionMaxima;

    //metodo para obtener el enunciado de una prueba
    public String getEnunciadoUrl() {
        return "http://localhost:9000/api/prueba/file?path=" + enunciado;
    }
}

