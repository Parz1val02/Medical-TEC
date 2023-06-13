package com.example.medicaltec.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;


@Embeddable
public class RecetaHasMedicamentoId implements Serializable {

    @Column(name = "receta_idreceta", nullable = false)
    private Integer recetaIdreceta;

    @Column(name = "medicamentos_idmedicamentos", nullable = false)
    private Integer medicamentosIdmedicamentos;

    public Integer getRecetaIdreceta() {
        return recetaIdreceta;
    }

    public void setRecetaIdreceta(Integer recetaIdreceta) {
        this.recetaIdreceta = recetaIdreceta;
    }

    public Integer getMedicamentosIdmedicamentos() {
        return medicamentosIdmedicamentos;
    }

    public void setMedicamentosIdmedicamentos(Integer medicamentosIdmedicamentos) {
        this.medicamentosIdmedicamentos = medicamentosIdmedicamentos;
    }
}