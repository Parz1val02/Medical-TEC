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
public class SedesHasEspecialidadeId implements Serializable {
    private static final long serialVersionUID = -6428263464772765520L;
    @Column(name = "especialidades_id_especialidad", nullable = false)
    private Integer especialidadesIdEspecialidad;

    @Column(name = "sedes_idsedes", nullable = false)
    private Integer sedesIdsedes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SedesHasEspecialidadeId entity = (SedesHasEspecialidadeId) o;
        return Objects.equals(this.sedesIdsedes, entity.sedesIdsedes) &&
                Objects.equals(this.especialidadesIdEspecialidad, entity.especialidadesIdEspecialidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sedesIdsedes, especialidadesIdEspecialidad);
    }

}