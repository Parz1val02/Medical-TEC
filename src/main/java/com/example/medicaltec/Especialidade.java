package com.example.medicaltec;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "especialidades")
public class Especialidade {
    @Id
    @Column(name = "id_especialidad", nullable = false)
    private Integer id;

    @Column(name = "nombre_especialidad", length = 45)
    private String nombreEspecialidad;

}