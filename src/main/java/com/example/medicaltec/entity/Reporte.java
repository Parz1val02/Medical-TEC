package com.example.medicaltec.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reporte")
public class Reporte {
    @Id
    @Column(name = "idreporte", nullable = false)
    private Integer id;

    @Column(name = "diagnostico", nullable = false, length = 200)
    private String diagnostico;

    @Column(name = "firma", nullable = false)
    private byte[] firma;

    @Column(name = "bitacora", length = 500)
    private String bitacora;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "historialmedico_idhistorialmedico", nullable = false)
    private Historialmedico historialmedicoIdhistorialmedico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cita_idcita", nullable = false)
    private Cita citaIdcita;

}