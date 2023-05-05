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
@Table(name = "estadoscita")
public class Estadoscita {
    @Id
    @Column(name = "idestados", nullable = false)
    private Integer id;

    @Column(name = "tipo", nullable = false, length = 45)
    private String tipo;

}