package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "horasdoctor")
public class Horasdoctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhorasdoctor", nullable = false)
    private Integer id;

    @Column(name = "horainicio", nullable = false)
    private LocalDateTime horainicio;

    @Column(name = "horafin", nullable = false)
    private LocalDateTime horafin;

    @Column(name = "horalibre", nullable = false)
    private LocalDateTime horalibre;

    @ManyToOne
    @JoinColumn(name = "usuario_dni", nullable = false)
    private Usuario usuarioDni;

}