package com.example.medicaltec.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "historialmedico")
public class Historialmedico {
    @Id
    @Column(name = "idhistorialmedico", nullable = false)
    private Integer id;

    @Column(name = "tratamiento", nullable = false, length = 200)
    private String tratamiento;

    @Column(name = "validahistorial", nullable = false)
    private Boolean validahistorial = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seguros_id_seguro", nullable = false)
    private Seguro segurosIdSeguro;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cuestionario_idcuestionario", nullable = false)
    private Cuestionario cuestionarioIdcuestionario;

}