package com.example.medicaltec.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "examen_medico_has_cita")
public class ExamenMedicoHasCita {
    @EmbeddedId
    private ExamenMedicoHasCitaId examenMedicoHasCitaId;

    @MapsId("exMedicoId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "examen_medico_idexamen", nullable = false)
    private ExamenMedico examenMedicoIdexamen;

    @MapsId("citaMedicaId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "cita_idcita", nullable = false)
    private Cita citaIdcita;

}