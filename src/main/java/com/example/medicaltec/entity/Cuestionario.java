package com.example.medicaltec.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cuestionario")
public class Cuestionario {
    @Id
    @Column(name = "idcuestionario", nullable = false)
    private Integer id;

    @Column(name = "nombrecuestionario", nullable = false, length = 45)
    private String nombrecuestionario;

    @Column(name = "Activo", nullable = false, length = 45)
    private String activo;
}