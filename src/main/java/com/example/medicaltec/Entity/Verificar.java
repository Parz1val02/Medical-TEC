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
@Table(name="verificar")
public class Verificar {

    @Id
    @Column(name="dni",nullable = false)
    private String dni;


    @Column(name="codigo")
    private String codigo;

    @Column(name="valido")
    private String valido;


}
