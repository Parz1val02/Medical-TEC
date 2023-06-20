package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cuestionarios")
public class Cuestionarios implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcuestionario", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "preguntas",nullable = false)
    private String preguntas;

    @Transient
    private List<String> listapreguntas;
}