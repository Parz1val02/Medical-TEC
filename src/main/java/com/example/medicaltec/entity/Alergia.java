package com.example.medicaltec.entity;
import jakarta.persistence.*;
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

        @ManyToOne(optional = false)
        @JoinColumn(name = "historialmedico_idhistorialmedico", nullable = false)
        private Historialmedico historialmedicoIdhistorialmedico;

}
