package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "cita")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcita", nullable = false)
    private Integer id;

    @Column(name = "citacancelada")
    private Boolean citacancelada;

    @ManyToOne
    @JoinColumn(name = "sedes_idsedes", nullable = false)
    private Sede sedesIdsedes;

    @ManyToOne
    @JoinColumn(name = "especialidades_id_especialidad", nullable = false)
    private Especialidade especialidadesIdEspecialidad;

    @ManyToOne
    @JoinColumn(name = "estadoscita_idestados")
    private Estadoscita estadoscitaIdestados;

    @ManyToOne
    @JoinColumn(name = "receta_idreceta")
    private Receta recetaIdreceta;

    @ManyToOne
    @JoinColumn(name = "tarjeta_idtarjetas")
    private Tarjeta tarjetaIdtarjetas;

    @Column(name = "formapago", nullable = false, length = 45)
    private String formapago;

    @Column(name = "modalidad", nullable = false, length = 45)
    private String modalidad;

    @ManyToOne
    @JoinColumn(name = "tipocita_idtipocita", nullable = false)
    private Tipocita tipocitaIdtipocita;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @ManyToOne
    @JoinColumn(name = "paciente_dni", nullable = false)
    private Usuario pacienteDni;

    @ManyToOne
    @JoinColumn(name = "doctor_dni1", nullable = false)
    private Usuario doctorDni1;

}