package com.example.medicaltec.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "formularios_registro")
public class FormulariosRegistro {
    @Id
    @Column(name = "idformularios", nullable = false)
    private Integer id;

    @Column(name = "nombrecompleto", nullable = false, length = 45)
    private String nombrecompleto;

    @Column(name = "pais", nullable = false, length = 45)
    private String pais;

    @Column(name = "correo", nullable = false, length = 45)
    private String correo;

    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @Column(name = "firma", nullable = false)
    private byte[] firma;

}