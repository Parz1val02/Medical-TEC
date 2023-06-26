package com.example.medicaltec.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cuestionarios_usuarios")
public class CuestionariosUsuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcuestionario", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "dnipaciente", nullable = false)
    private Usuario dnipaciente;
    @ManyToOne
    @JoinColumn(name = "dnidoctor", nullable = false)
    private Usuario dnidoctor;

    @Column(name = "respondido", nullable = false)
    private Boolean respondido;

    @Column(name = "respuestas",nullable = false)
    private String respuestas;

    @Transient
    private List<String> listarespuestas;
}
