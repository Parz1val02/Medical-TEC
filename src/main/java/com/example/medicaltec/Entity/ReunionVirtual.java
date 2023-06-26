package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "reunion_virtual")
public class ReunionVirtual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreunion_virtual", nullable = false)
    private Integer id;


    @Column(name = "fecha", length = 45)
    private String fecha;


    @Column(name = "horainicio")
    private Date horaInicio;

    @Column(name = "horafin")
    private Date horaFin;


    @Column(name = "token",  length = 500)
    private String token;


    @Column(name = "room",  length = 200)
    private String appID;

    @ManyToOne
    @JoinColumn(name = "usuario_dni", nullable = false)
    private Usuario doctorDNI;


    @ManyToOne
    @JoinColumn(name = "usuario_dni1", nullable = false)
    private Usuario pacienteDNI;
}
