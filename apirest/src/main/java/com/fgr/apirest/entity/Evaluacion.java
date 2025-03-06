package com.fgr.apirest.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvaluacion;

    @ManyToOne
    @JoinColumn(name = "Participante_id", referencedColumnName = "idParticipante", nullable = false)
    private Participante participante;

    @ManyToOne
    @JoinColumn(name = "Prueba_id", referencedColumnName = "idPrueba", nullable = false)
    private Prueba prueba;

    @ManyToOne
    @JoinColumn(name = "User_id", referencedColumnName = "idUser", nullable = true)
    private User user;
    
    private Double notaFinal;
}

