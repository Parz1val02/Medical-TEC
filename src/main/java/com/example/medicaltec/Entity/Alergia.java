package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "alergias")
public class Alergia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idalergias", nullable = false)
    private Integer id;

    @Size(max = 100, message = "Maximo 100 caracteres")
    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

}