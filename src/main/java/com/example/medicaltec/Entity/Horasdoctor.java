package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "horasdoctor")
public class Horasdoctor {
    @Id
    @Column(name = "idhorasdoctor", nullable = false)
    private Integer id;

    @Column(name = "horainicio", nullable = false)
    private Instant horainicio;

    @Column(name = "horafin", nullable = false)
    private Instant horafin;

    @Column(name = "horalibre", nullable = false)
    private Instant horalibre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_dni", nullable = false)
    private Usuario usuarioDni;

}