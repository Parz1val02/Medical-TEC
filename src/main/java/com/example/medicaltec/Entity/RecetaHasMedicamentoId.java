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
public class RecetaHasMedicamentoId implements Serializable {
    private static final long serialVersionUID = 7364726641003743565L;
    @Column(name = "receta_idreceta", nullable = false)
    private Integer recetaIdreceta;

    @Column(name = "medicamentos_idmedicamentos", nullable = false)
    private Integer medicamentosIdmedicamentos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RecetaHasMedicamentoId entity = (RecetaHasMedicamentoId) o;
        return Objects.equals(this.medicamentosIdmedicamentos, entity.medicamentosIdmedicamentos) &&
                Objects.equals(this.recetaIdreceta, entity.recetaIdreceta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicamentosIdmedicamentos, recetaIdreceta);
    }

}