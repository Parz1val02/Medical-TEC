package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "receta")
public class Receta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreceta", nullable = false)
    private Integer id;


    @Size(max = 100, message = "por favor sea breve")
    @Column(name = "observaciones", length = 100)
    private String observaciones;

}