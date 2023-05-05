package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "examen_medico_has_cita")
public class ExamenMedicoHasCita {
    @EmbeddedId
    private ExamenMedicoHasCitaId id;

    @MapsId("examenMedicoIdexamen")
    @ManyToOne
    @JoinColumn(name = "examen_medico_idexamen", nullable = false)
    private ExamenMedico examenMedicoIdexamen;

    @MapsId("citaIdcita")
    @ManyToOne
    @JoinColumn(name = "cita_idcita", nullable = false)
    private Cita citaIdcita;

}