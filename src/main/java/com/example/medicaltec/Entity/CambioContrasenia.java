package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cambioContrasenia")
public class CambioContrasenia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "correo", nullable = false, length = 45)
    private String correo;

    @Column(name = "fecha", nullable = false, length = 45)
    private String fecha;

}
