package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "especialidades")
public class Especialidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidad", nullable = false)
    private Integer id;

    @Column(name = "nombre_especialidad", length = 45)
    private String nombreEspecialidad;

}