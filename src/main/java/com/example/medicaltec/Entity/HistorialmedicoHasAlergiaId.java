package com.example.medicaltec.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class HistorialmedicoHasAlergiaId implements Serializable {
    @Column(name = "historialmedico_idhistorialmedico", nullable = false)
    private Integer historialmedicoIdhistorialmedico;

    @Column(name = "alergias_idalergias", nullable = false)
    private Integer alergiasIdalergias;

    public Integer getHistorialmedicoIdhistorialmedico(){return historialmedicoIdhistorialmedico;}
    public void setHistorialmedicoIdhistorialmedico(Integer historialmedicoIdhistorialmedico){this.historialmedicoIdhistorialmedico=historialmedicoIdhistorialmedico;}
    public Integer getAlergiasIdalergias(){return alergiasIdalergias;}
    public void setAlergiasIdalergias(Integer alergiasIdalergias){this.alergiasIdalergias=alergiasIdalergias;}
}