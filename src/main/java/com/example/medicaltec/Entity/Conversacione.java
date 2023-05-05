package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "conversaciones")
public class Conversacione {
    @Id
    @Column(name = "idconversaciones", nullable = false)
    private Integer id;

    @Column(name = "fechacreacion", nullable = false)
    private Instant fechacreacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_dni", nullable = false)
    private Usuario usuarioDni;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_dni1", nullable = false)
    private Usuario usuarioDni1;

}