package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "cuestionario")
public class Cuestionario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcuestionario", nullable = false)
    private Integer id;

    @Column(name = "nombrecuestionario", nullable = false, length = 45)
    private String nombrecuestionario;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paciente_dni", referencedColumnName = "dni", nullable = false)
    private Usuario paciente;
}