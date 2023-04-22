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
@Table(name = "formularios_registro")
public class FormulariosRegistro {
    @Id
    @Column(name = "idformularios", nullable = false)
    private Integer id;

    @Column(name = "nombrecompleto", nullable = false, length = 45)
    private String nombrecompleto;

    @Column(name = "fechanacimiento", nullable = false, length = 45)
    private String fechanacimiento;

    @Column(name = "telefono", nullable = false, length = 45)
    private String telefono;

    @Column(name = "vacunacovid", nullable = false, length = 45)
    private String vacunacovid;

    @Column(name = "pais", nullable = false, length = 45)
    private String pais;

    @Column(name = "estadocivil", nullable = false, length = 45)
    private String estadocivil;

    @Column(name = "responsablemenores", length = 45)
    private String responsablemenores;

}