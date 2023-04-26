package com.example.medicaltec.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "receta")
public class Receta {
    @Id
    @Column(name = "idreceta", nullable = false)
    private Integer id;

    @Column(name = "observaciones", length = 100)
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receta_idreceta", nullable = false)
    private Receta recetaIdreceta;

}