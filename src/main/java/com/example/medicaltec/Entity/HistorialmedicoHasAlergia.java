package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "historialmedico_has_alergias")
public class HistorialmedicoHasAlergia {
    @EmbeddedId
    private HistorialmedicoHasAlergiaId id;

    @MapsId("historialmedicoIdhistorialmedico")
    @ManyToOne
    @JoinColumn(name = "historialmedico_idhistorialmedico", nullable = false)
    private Historialmedico historialmedicoIdhistorialmedico;

    @MapsId("alergiasIdalergias")
    @ManyToOne
    @JoinColumn(name = "alergias_idalergias", nullable = false)
    private Alergia alergiasIdalergias;

}