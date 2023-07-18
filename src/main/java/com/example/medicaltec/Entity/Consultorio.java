package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consultorio")
public class Consultorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idconsultorio", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sedes_idsedes", nullable = false)
    private Sede sedesIdsedes;

    @ManyToOne
    @JoinColumn(name = "dni")
    private Usuario paciente_dni;

    @Column(name = "nombreconsultorio", nullable = false, length = 5)
    private String nombreConsultorio;
}