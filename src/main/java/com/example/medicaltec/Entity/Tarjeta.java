package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "tarjeta")
public class Tarjeta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtarjetas", nullable = false)
    private Integer id;

    @Column(name = "numero", nullable = false)
    private Long numero;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

}