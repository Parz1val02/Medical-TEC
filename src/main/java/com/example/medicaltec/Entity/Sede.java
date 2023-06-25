package com.example.medicaltec.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "sedes")
public class Sede implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsedes", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "latitud", nullable = false)
    private Double latitud;

    @Column(name = "longitud", nullable = false)
    private Double longitud;

    @Column(name = "torres", nullable = false)
    private Integer torres;

    @Column(name = "pisos", nullable = false)
    private Integer pisos;

    @Column(name = "consultorios", nullable = false)
    private Integer consultorios;

    @Column(name = "direccion", nullable = false, length = 90)
    private String direccion;
}