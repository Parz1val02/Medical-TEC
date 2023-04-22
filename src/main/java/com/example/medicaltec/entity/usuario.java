package com.example.medicaltec.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "usuario")
public class usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dni")
    private int dni;
    @Column(name = "contrasena", nullable = false)
    private String contrasena;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellido", nullable = false)
    private String apellido;
    @Column(name = "edad", nullable = false)
    private int edad;
    @Column(name = "telefono", nullable = false)
    private String telefono;
    @Column(name = "sexo", nullable = false)
    private String sexo;
    @Column(name = "direccion", nullable = false)
    private String direccion;

}
