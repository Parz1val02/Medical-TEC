package com.example.medicaltec.entity;

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

    @Column(name = "contenido", length = 500)
    private String contenido;

    @ManyToOne( optional = false)
    @JoinColumn(name = "usuario_dni", nullable = false)
    private Usuario usuarioDni;

}