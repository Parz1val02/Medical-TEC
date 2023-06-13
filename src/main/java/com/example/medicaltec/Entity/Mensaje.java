package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "mensajes")
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmensajes", nullable = false)
    private Integer id;

    @Column(name = "contenido", nullable = false, length = 500)
    private String contenido;

    @Column(name = "fecha", length = 200)
    private LocalDate fecha;

    @Column(name = "hora", length = 200)
    private LocalTime hora;

    @ManyToOne
    @JoinColumn(name = "conversaciones_idconversaciones", nullable = false)
    private Conversacione conversacionesIdconversaciones;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "emisorDNI", referencedColumnName = "dni", nullable = false)
    private Usuario emisorDNI;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receptorDNI", referencedColumnName = "dni", nullable = false)
    private Usuario receptorDNI;

}