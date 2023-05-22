package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "seguros")
public class Seguro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguro", nullable = false)
    private Integer id;


    @Max(value = 45)
    @Min(value = 0)
    @Column(name = "nombre_seguro", nullable = false, length = 45)
    private String nombreSeguro;


    @Digits(integer = 1, fraction = 2)
    @Column(name = "porc_seguro", nullable = false, length = 45)
    private String porcSeguro;


    @Digits(integer = 1, fraction = 2)
    @Column(name = "porc_doctor", nullable = false, length = 45)
    private String porcDoctor;

}