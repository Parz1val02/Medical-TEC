package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @ManyToOne
    @JoinColumn(name = "receta_idreceta", nullable = false)
    private Receta recetaIdreceta;

    @MapsId("medicamentosIdmedicamentos")
    @ManyToOne
    @JoinColumn(name = "medicamentos_idmedicamentos", nullable = false)
    private Medicamento medicamentosIdmedicamentos;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;


    @Size(max = 500, message = "por favor sea breve")
    @Column(name = "observaciones", nullable = false, length = 500)
    private String observaciones;
}