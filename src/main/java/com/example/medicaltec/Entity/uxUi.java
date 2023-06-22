package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ux/ui")
public class uxUi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logonombre")
    private String logoNombre;
    @Column(name = "logocontenttype")
    private String logoContentType;

    @Column(name = "zonahoraria")
    private String zonaHoraria;

    @Column(name = "colorsidebar")
    private String colorSidebar;

    @Column(name = "colortopbar")
    private String coorTopbar;
}
