package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "medicamentos")
public class Medicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmedicamentos", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "precio", nullable = false)
    private Float precio;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "frecuencia", nullable = false, length = 45)
    private String frecuencia;

}