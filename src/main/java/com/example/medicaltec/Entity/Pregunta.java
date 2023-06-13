package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "preguntas")
public class Pregunta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpreguntas", nullable = false)
    private Integer id;

    @Column(name = "pregunta", nullable = false, length = 200)
    private String pregunta;

    @ManyToOne
    @JoinColumn(name = "cuestionario_idcuestionario", nullable = false)
    private Cuestionario cuestionarioIdcuestionario;

    @ManyToOne
    @JoinColumn(name = "especialidades_id_especialidad", nullable = false)
    private Especialidade especialidadesIdEspecialidad;

}