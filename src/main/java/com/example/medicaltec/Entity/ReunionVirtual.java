package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "reunion_virtual")
public class ReunionVirtual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreunion_virtual", nullable = false)
    private Integer id;



    @Column(name = "enlace",  length = 500)
    private String enlace;


    @Column(name = "room",  length = 200)
    private String room;
}
