package com.example.medicaltec.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmedicamentos")
    private Integer id;

    private String nombre;

    private String frecuencia;

    private Double precio;

    private Integer cantidad;
}
