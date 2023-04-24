package com.example.medicaltec.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "alergias")
public class Alergia {
    @Id
    @Column(name = "idalergias", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 100)
    private String nombre;

}