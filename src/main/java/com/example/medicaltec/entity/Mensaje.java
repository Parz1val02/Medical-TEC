package com.example.medicaltec.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mensajes")
public class Mensaje {
    @Id
    @Column(name = "idmensajes", nullable = false)
    private Integer id;

    @Column(name = "contenido", nullable = false, length = 500)
    private String contenido;

    @Column(name = "fecha", nullable = false, length = 45)
    private String fecha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conversaciones_idconversaciones", nullable = false)
    private Conversacione conversacionesIdconversaciones;

}