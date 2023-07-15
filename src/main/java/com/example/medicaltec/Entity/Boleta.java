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

    @Column(name = "conceptopago", length = 100, nullable = false)
    private String conceptopago;
    @Column(name = "monto_cita", nullable = false)
    private Double montoCita;

    @ManyToOne
    @JoinColumn(name = "receta_idreceta")
    private Receta recetaIdreceta;

    @ManyToOne
    @JoinColumn(name = "cita_idcita")
    private Cita citaIdcita;

    @Column(name = "monto_receta")
    private Double montoReceta;
    @ManyToOne
    @JoinColumn(name = "seguros_id_seguro")
    private Seguro seguro;
}