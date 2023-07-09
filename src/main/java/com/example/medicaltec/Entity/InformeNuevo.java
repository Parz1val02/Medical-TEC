package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "informe_nuevo")
public class InformeNuevo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinforme", nullable = false)
    private Integer id;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "campos")
    private String campos;

    @Transient
    private List<String> listacampos;

}
