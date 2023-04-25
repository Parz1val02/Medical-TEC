package com.example.medicaltec.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "historialmedico_has_alergias")
public class HistorialMedicoAlergias {
    @EmbeddedId
    private HistorialMedicoAlergiasId id;

    @MapsId("idHistorialMedico")
    @ManyToOne
    @JoinColumn(name = "historialmedico_idhistorialmedico")
    private Historialmedico historialmedico;

    @MapsId("idAlergia")
    @ManyToOne
    @JoinColumn(name = "alergias_idalergias")
    private Alergia alergia;
}
