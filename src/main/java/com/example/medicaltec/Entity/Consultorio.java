package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consultorio")
public class Consultorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idconsultorio", nullable = false, length = 5)
    private String id;

    @ManyToOne
    @JoinColumn(name = "sedes_idsedes", nullable = false)
    private Sede sedesIdsedes;

}