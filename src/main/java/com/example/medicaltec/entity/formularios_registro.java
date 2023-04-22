package com.example.medicaltec.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "formularios_registro")
public class formularios_registro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idformularios")
    private int idformularios;
    @Column(nullable = false)
    private String nombrecompleto;
    @Column(nullable = false)
    private String fechanacimiento;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false)
    private String vacunacovid;
    @Column(nullable = false)
    private String pais;
    @Column(nullable = false)
    private String estadocivil;
    private String responsablemenores;
}
