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
@Table(name = "api")
public class Api {
    @Id
    @Column(name = "dni", nullable = false, length = 45)
    private String id;

    @Column(name = "Nombres", length = 45)
    private String nombres;

    @Column(name = "Apellidos", length = 45)
    private String apellidos;

}