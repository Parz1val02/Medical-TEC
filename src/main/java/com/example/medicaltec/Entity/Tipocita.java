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
@Table(name = "tipocita")
public class Tipocita {
    @Id
    @Column(name = "idtipocita", nullable = false)
    private Integer id;

    @Column(name = "tipo_cita", nullable = false, length = 45)
    private String tipoCita;

}