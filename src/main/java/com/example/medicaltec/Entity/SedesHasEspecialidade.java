package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sedes_has_especialidades")
public class SedesHasEspecialidade {
    @EmbeddedId
    private SedesHasEspecialidadeId id;

    @MapsId("especialidadesIdEspecialidad")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "especialidades_id_especialidad", nullable = false)
    private Especialidade especialidadesIdEspecialidad;

    @MapsId("sedesIdsedes")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sedes_idsedes", nullable = false)
    private Sede sedesIdsedes;

}