package com.example.medicaltec;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "receta_has_medicamentos")
public class RecetaHasMedicamento {
    @EmbeddedId
    private RecetaHasMedicamentoId id;

    @MapsId("recetaIdreceta")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receta_idreceta", nullable = false)
    private Receta recetaIdreceta;

    @MapsId("medicamentosIdmedicamentos")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medicamentos_idmedicamentos", nullable = false)
    private Medicamento medicamentosIdmedicamentos;

}