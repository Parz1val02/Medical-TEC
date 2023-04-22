package com.example.medicaltec.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cuestionario")
public class cuestionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcuestionario")
    private int cuestionario;
    @Column(nullable = false)
    private String nombrecuestionario;
}
