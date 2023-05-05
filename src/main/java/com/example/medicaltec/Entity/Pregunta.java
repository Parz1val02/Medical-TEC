package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "preguntas")
public class Pregunta {
    @Id
    @Column(name = "idpreguntas", nullable = false)
    private Integer id;

    @Column(name = "pregunta", nullable = false, length = 200)
    private String pregunta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cuestionario_idcuestionario", nullable = false)
    private Cuestionario cuestionarioIdcuestionario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "especialidades_id_especialidad", nullable = false)
    private Especialidade especialidadesIdEspecialidad;

}