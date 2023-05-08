package com.example.medicaltec.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class HistorialmedicoHasAlergiaId implements Serializable {
    private static final long serialVersionUID = 6527313889927306511L;
    @Column(name = "historialmedico_idhistorialmedico", nullable = false)
    private Integer historialmedicoIdhistorialmedico;

    @Column(name = "alergias_idalergias", nullable = false)
    private Integer alergiasIdalergias;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HistorialmedicoHasAlergiaId entity = (HistorialmedicoHasAlergiaId) o;
        return Objects.equals(this.historialmedicoIdhistorialmedico, entity.historialmedicoIdhistorialmedico) &&
                Objects.equals(this.alergiasIdalergias, entity.alergiasIdalergias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(historialmedicoIdhistorialmedico, alergiasIdalergias);
    }

}