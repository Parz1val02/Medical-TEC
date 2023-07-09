package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "boletas")
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idboletas", nullable = false)
    private Integer id;

    @Column(name = "conceptopago", length = 100)
    private String conceptopago;
    @Column(name = "montototal", nullable = false)
    private Double montototal;

    @ManyToOne
    @JoinColumn(name = "receta_idreceta")
    private Receta recetaIdreceta;

    @ManyToOne
    @JoinColumn(name = "cita_idcita")
    private Cita citaIdcita;

    @Column(name = "pagocompletado")
    private Boolean pagoCompletado;
}