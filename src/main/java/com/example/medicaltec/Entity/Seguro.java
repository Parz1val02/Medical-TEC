package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "seguros")
public class Seguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguro", nullable = false)
    private Integer id;

    @Column(name = "nombre_seguro", nullable = false, length = 45)
    private String nombreSeguro;

    @Column(name = "porc_seguro", nullable = false, length = 45)
    private String porcSeguro;

    @Column(name = "porc_doctor", nullable = false, length = 45)
    private String porcDoctor;

}