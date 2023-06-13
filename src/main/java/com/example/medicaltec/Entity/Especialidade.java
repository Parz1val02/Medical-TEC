package com.example.medicaltec.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "especialidades")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Especialidade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidad", nullable = false)
    private Integer id;

    @Column(name = "nombre_especialidad", length = 45)
    private String nombreEspecialidad;

}