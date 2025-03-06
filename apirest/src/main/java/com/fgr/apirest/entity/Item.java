package com.fgr.apirest.entity;


import java.util.ArrayList;
import java.util.List;

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
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItem;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(nullable = false)
    private Integer peso;

    @Column(nullable = false)
    private Integer gradosConsecucion;

    @ManyToOne
    @JoinColumn(name = "Prueba_id", nullable = false)
    private Prueba prueba;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluacionItem> evaluacionItems = new ArrayList<>();
}
