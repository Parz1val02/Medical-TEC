package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "formularios_registro")
public class FormulariosRegistro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idformularios", nullable = false)
    private Integer id;

    @Column(name = "nombrecompleto", nullable = false, length = 45)
    private String nombrecompleto;

    @Column(name = "pais", nullable = false, length = 45)
    private String pais;

    @Column(name = "correo", nullable = false, length = 45)
    private String correo;

    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @Column(name = "respondido")
    private Boolean respondido;
    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "nombre", length = 45)
    private String nomrbe;

    @Column(name = "preguntas")
    private String preguntas;

    @Column(name = "respuestas")
    private String respuestas;
}