package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "conversaciones")
public class Conversacione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idconversaciones", nullable = false)
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fechacreacion", nullable = false)
    private Instant fechacreacion;

    @ManyToOne
    @JoinColumn(name = "usuario_dni", nullable = false)
    private Usuario usuarioDni;

    @ManyToOne
    @JoinColumn(name = "usuario_dni1", nullable = false)
    private Usuario usuarioDni1;

}