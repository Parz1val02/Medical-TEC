package com.example.medicaltec.Entity;

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
public class ExamenMedicoHasCitaId implements Serializable {
    private static final long serialVersionUID = -8976112421600759694L;
    @Column(name = "examen_medico_idexamen", nullable = false)
    private Integer examenMedicoIdexamen;

    @Column(name = "cita_idcita", nullable = false)
    private Integer citaIdcita;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ExamenMedicoHasCitaId entity = (ExamenMedicoHasCitaId) o;
        return Objects.equals(this.examenMedicoIdexamen, entity.examenMedicoIdexamen) &&
                Objects.equals(this.citaIdcita, entity.citaIdcita);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examenMedicoIdexamen, citaIdcita);
    }

}