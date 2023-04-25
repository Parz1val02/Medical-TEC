package com.example.medicaltec.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tipocita")
public class TipoCita {

    @Id
    @Column(name = "idtipocita", nullable = false)
    private Integer id;

    @Column(name = "tipo_cita", nullable = false, length = 45)
    private String tipoCita;
}
