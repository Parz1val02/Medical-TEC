package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estadoscita")
public class Estadoscita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestados", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;

}