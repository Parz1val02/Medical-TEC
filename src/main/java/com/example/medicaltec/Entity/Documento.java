package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "documentos")
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddocumentos", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "historialmedico_idhistorialmedico", nullable = false)
    private Historialmedico historialmedicoIdhistorialmedico;

}