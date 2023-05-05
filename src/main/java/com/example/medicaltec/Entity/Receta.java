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
@Table(name = "receta")
public class Receta {
    @Id
    @Column(name = "idreceta", nullable = false)
    private Integer id;

    @Column(name = "observaciones", length = 100)
    private String observaciones;

}