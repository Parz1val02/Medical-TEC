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

    @Size(max = 45)
    @Column(name = "color", nullable = false, length = 45)
    private String color;

    @Column(name = "latitud", nullable = false)
    private Double latitud;

    @Column(name = "longitud", nullable = false)
    private Double longitud;

    @Column(name = "torre", nullable = false, length = 45)
    private String torre;


    @Min(value = 0)
    @Max(value = 5)
    @Column(name = "piso", nullable = false)
    private Integer piso;

    @Column(name = "zona_horaria", nullable = false, length = 45)
    private String zonaHoraria;

    @Column(name = "nombre_logo", nullable = false, length = 45)
    private String nombreLogo;

    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logonombre")
    private String logonombre;

    @Column(name = "logocontenttype")
    private String logocontenttype;

}