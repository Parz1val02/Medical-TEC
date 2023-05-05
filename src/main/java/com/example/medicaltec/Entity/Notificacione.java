package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notificaciones")
public class Notificacione {
    @Id
    @Column(name = "idnotificaciones", nullable = false)
    private Integer id;

    @Column(name = "contenido", length = 200)
    private String contenido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_dni", nullable = false)
    private Usuario usuarioDni;

}