package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "form_autoregistro")
public class FormAutoregistro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idautoregistro", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "dni", length = 45)
    private String dni;

    @Size(max = 45)
    @Column(name = "nombres", length = 45)
    private String nombres;

    @Size(max = 45)
    @Column(name = "apellidos", length = 45)
    private String apellidos;

    @Column(name = "edad")
    private Integer edad;

    @Size(max = 45)
    @Column(name = "domicilio", length = 45)
    private String domicilio;

    @Size(max = 45)
    @Column(name = "sexo", length = 45)
    private String sexo;

    @Size(max = 45)
    @Column(name = "celular", length = 45)
    private String celular;

    @Column(name = "seguroid")
    private Integer seguroid;

    @Column(name = "sedeid")
    private Integer sedeid;

    @Size(max = 45)
    @Column(name = "correo", length = 45)
    private String correo;

}