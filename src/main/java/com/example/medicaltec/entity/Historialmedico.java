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

    @Column(name = "tratamiento", nullable = false, length = 500)
    private String tratamiento;

    @Column(name = "validahistorial", nullable = false)
    private Boolean validahistorial = false;

    @ManyToOne( optional = false)
    @JoinColumn(name = "seguros_id_seguro", nullable = false)
    private Seguro segurosIdSeguro;

    @ManyToOne( optional = false)
    @JoinColumn(name = "cuestionario_idcuestionario", nullable = false)
    private Cuestionario cuestionarioIdcuestionario;

}