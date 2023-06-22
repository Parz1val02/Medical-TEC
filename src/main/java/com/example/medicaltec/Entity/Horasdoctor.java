package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "horasdoctor")
public class Horasdoctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhorasdoctor", nullable = false)
    private Integer id;


    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "horainicio", nullable = false)
    private LocalTime horainicio;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "horafin", nullable = false)
    private LocalTime horafin;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "horalibre", nullable = false)
    private LocalTime horalibre;

    @Column(name = "dias", nullable = false, length = 100)
    private String dias;

    @Column(name = "mes", nullable = false,length = 45)
    private String mes;

    @ManyToOne
    @JoinColumn(name = "doctor_dni", nullable = false)
    private Usuario doctor_dni;

}