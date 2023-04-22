package com.example.medicaltec;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "respuestas")
public class Respuesta {
    @Id
    @Column(name = "idrespuestas", nullable = false)
    private Integer id;

    @Column(name = "respuesta", nullable = false, length = 200)
    private String respuesta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "preguntas_idpreguntas", nullable = false)
    private preguntas preguntasIdpreguntas;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "historialmedico_idhistorialmedico", nullable = false)
    private Historialmedico historialmedicoIdhistorialmedico;

}