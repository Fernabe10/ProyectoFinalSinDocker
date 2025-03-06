package com.fgr.apirest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Prueba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPrueba;

    @Column(nullable = false, length = 400)
    private String enunciado;

    @ManyToOne
    @JoinColumn(name = "especialidad_id", referencedColumnName = "idEspecialidad")
    private Especialidad especialidad;


    @Column(nullable = false)
    private Integer puntuacionMaxima;
}

