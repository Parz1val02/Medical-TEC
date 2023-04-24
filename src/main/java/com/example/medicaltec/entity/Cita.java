package com.example.medicaltec.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
@Entity
@Table(name = "cita")
public class Cita {

        @Id
        @Column(name = "idcita", nullable = false)
        private Integer id;

        @Column(name = "fechahora", nullable = false)
        private Instant fechahora;

        @Column(name = "citacancelada")
        private Boolean citacancelada;

        @ManyToOne(optional = false)
        @JoinColumn(name = "sedes_idsedes", nullable = false)
        private Sede sedesIdsedes;

        @ManyToOne( optional = false)
        @JoinColumn(name = "especialidades_id_especialidad", nullable = false)
        private Especialidades especialidadesIdEspecialidad;

        @ManyToOne
        @JoinColumn(name = "estadoscita_idestados")
        private Estadoscita estadoscitaIdestados;

        @ManyToOne
        @JoinColumn(name = "receta_idreceta")
        private Receta recetaIdreceta;

        //@ManyToOne
        //@JoinColumn(name = "tarjeta_idTarjetas")
        //private Tarjeta tarjetaIdtarjetas;

        @Column(name = "formapago", nullable = false, length = 45)
        private String formapago;

        @Column(name = "modalidad", nullable = false, length = 45)
        private String modalidad;

}
