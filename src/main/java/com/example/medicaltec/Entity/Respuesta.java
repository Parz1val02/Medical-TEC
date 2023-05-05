package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "respuestas")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrespuestas", nullable = false)
    private Integer id;

    @Column(name = "respuesta", nullable = false, length = 200)
    private String respuesta;

    @ManyToOne
    @JoinColumn(name = "preguntas_idpreguntas", nullable = false)
    private Pregunta preguntasIdpreguntas;

    @ManyToOne
    @JoinColumn(name = "historialmedico_idhistorialmedico", nullable = false)
    private Historialmedico historialmedicoIdhistorialmedico;

}