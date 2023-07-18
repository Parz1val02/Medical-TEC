package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "informe")
public class Informe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinforme", nullable = false)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_dni", referencedColumnName = "dni")
    private Usuario paciente;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cita_idcita", referencedColumnName = "idcita")
    private Cita cita;
    @Column(name = "diagnostico", length = 200)
    private String diagnostico;

    @Column(name = "bitacora", length = 500)
    private String bitacora;

    @Column(name = "tratamiento", length = 100)
    private String tratamiento;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "camposllenados")
    private String campos;

    @Transient
    private List<String> listacamposllenados;

    @ManyToOne
    @JoinColumn(name = "informe_nuevo_idinforme")
    private InformeNuevo informeNuevo;



}