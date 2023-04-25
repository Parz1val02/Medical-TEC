package com.example.medicaltec.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consentimientos")
public class Consentimiento {
    @Id
    @Column(name = "idconsentimientos", nullable = false)
    private Integer id;

    @Column(name = "consentimiento", nullable = false)
    private byte[] consentimiento;

    @ManyToOne( optional = false)
    @JoinColumn(name = "historialmedico_idhistorialmedico", nullable = false)
    private Historialmedico historialmedicoIdhistorialmedico;

}