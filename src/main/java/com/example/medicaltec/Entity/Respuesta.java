package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "respuestas")
public class Respuesta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrespuestas", nullable = false)
    private Integer id;

    @Size(max = 200, message = "por favor, la respuesta debe ser menos de 200 caracteres")
    @Column(name = "respuesta", nullable = false)
    private String respuesta;

    @ManyToOne
    @JoinColumn(name = "preguntas_idpreguntas", nullable = false)
    private Pregunta preguntasIdpreguntas;

    @ManyToOne
    @JoinColumn(name = "historialmedico_idhistorialmedico", nullable = false)
    private Historialmedico historialmedicoIdhistorialmedico;

}