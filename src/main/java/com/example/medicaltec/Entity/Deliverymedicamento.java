package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "deliverymedicamentos")
public class Deliverymedicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddeliverymedicamentos", nullable = false)
    private Integer id;

    @Column(name = "latitudactual", nullable = false)
    private Float latitudActual;

    @Column(name = "longitudactual", nullable = false)
    private Float longitudActual;

    @Column(name = "latitudfinal", nullable = false)
    private Float latitudfinal;

    @Column(name = "longitudfinal", nullable = false)
    private Float longitudfinal;

    @Column(name = "estado", nullable = false, length = 45)
    private String estado;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idreceta", referencedColumnName = "idreceta")
    private Receta recetaidreceta;
}