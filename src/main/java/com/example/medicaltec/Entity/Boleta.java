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

    @Column(name = "conceptopago", nullable = false, length = 100)
    private String conceptopago;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @ManyToOne
    @JoinColumn(name = "seguros_id_seguro", nullable = false)
    private Seguro segurosIdSeguro;

    @ManyToOne
    @JoinColumn(name = "receta_idreceta")
    private Receta recetaIdreceta;

    @ManyToOne
    @JoinColumn(name = "cita_idcita", nullable = false)
    private Cita citaIdcita;

}