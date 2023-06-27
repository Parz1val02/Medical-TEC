package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "informe_nuevo")
public class InformeNuevo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinforme", nullable = false)
    private Integer id;

    @Column(name = "diagnostico", length = 200)
    private String diagnostico;

    @Column(name = "bitacora", length = 500)
    private String bitacora;

    @Column(name = "tratamiento", length = 100)
    private String tratamiento;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "campos")
    private String campos;

}
