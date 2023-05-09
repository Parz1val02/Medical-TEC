package com.example.medicaltec.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cuestionario")
public class Cuestionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcuestionario", nullable = false)
    private Integer id;

    @Column(name = "nombrecuestionario", nullable = false, length = 45)
    private String nombrecuestionario;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

}