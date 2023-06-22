package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sedes_has_especialidades")
public class SedeHasEspecialidades{
    @EmbeddedId
    private SedeHasEspecialidadesId id;

    @MapsId("sedesIdSedes")
    @ManyToOne
    @JoinColumn(name = "sedes_idsedes", nullable = false)
    private Sede sede;

    @MapsId("especialidadesIdEspecialidades")
    @ManyToOne
    @JoinColumn(name = "especialidades_id_especialidad", nullable = false)
    private Especialidade especialidade;

}
