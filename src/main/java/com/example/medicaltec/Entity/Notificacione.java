package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "notificaciones")
public class Notificacione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnotificaciones", nullable = false)
    private Integer id;

    @Column(name = "contenido", length = 200)
    private String contenido;

    @ManyToOne
    @JoinColumn(name = "usuario_dni", nullable = false)
    private Usuario usuarioDni;

    @Column(name = "fecha", length = 200)
    private LocalDate fecha;

    @Column(name = "hora", length = 200)
    private LocalTime hora;

}