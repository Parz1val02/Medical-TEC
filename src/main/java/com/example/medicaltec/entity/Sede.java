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
@Table(name = "sedes")
public class Sede {

        @Id
        @Column(name = "idsedes", nullable = false)
        private Integer id;

        @Column(name = "nombre", nullable = false, length = 45)
        private String nombre;

        @Column(name = "color", length = 45)
        private String color;

        @Column(name = "latitud", nullable = false)
        private Double latitud;

        @Column(name = "longitud", nullable = false)
        private Double longitud;

        @Column(name = "torre", length = 45)
        private String torre;

        @Column(name = "piso")
        private Integer piso;

}
