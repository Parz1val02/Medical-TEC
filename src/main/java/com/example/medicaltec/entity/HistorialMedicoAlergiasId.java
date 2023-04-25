package com.example.medicaltec.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class HistorialMedicoAlergiasId implements Serializable {
    @Column(name = "historialmedico_idhistorialmedico", nullable = false)
    private Integer idHistorialMedico;

    @Column(name = "alergias_idalergias", nullable = false)
    private Integer idAlergia;
}
