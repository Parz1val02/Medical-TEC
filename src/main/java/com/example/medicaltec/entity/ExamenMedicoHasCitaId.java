package com.example.medicaltec.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class ExamenMedicoHasCitaId implements Serializable {
    @Column(name = "examen_medico_idexamen", nullable = false)
    private Integer exMedicoId;
    @Column(name = "cita_idcita", nullable = false)
    private Integer citaMedicaId;
}
