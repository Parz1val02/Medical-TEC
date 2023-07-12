package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "receta")
public class Receta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreceta", nullable = false)
    private Integer id;


    @Size(max = 500, message = "por favor sea breve")
    @Column(name = "comentario", length = 500)
    private String comentario;


    @ManyToOne
    @JoinColumn(name = "deliverymedicamentos_iddeliverymedicamentos")
    private Deliverymedicamento deliverymedicamento;

}