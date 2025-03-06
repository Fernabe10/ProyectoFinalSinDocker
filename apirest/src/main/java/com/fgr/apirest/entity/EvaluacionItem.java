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
public class EvaluacionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvaluacionItem")
    private Integer idEvaluacionItem;

    @Column(nullable = false)
    private Integer valoracion;

    @Column(length = 200)
    private String explicacion;

    @ManyToOne
    @JoinColumn(name = "Evaluacion_id", referencedColumnName = "idEvaluacion", nullable = false)
    private Evaluacion evaluacion;

    @ManyToOne
    @JoinColumn(name = "Item_id", referencedColumnName = "idItem", nullable = false)
    private Item item;
}
