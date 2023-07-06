package com.example.medicaltec.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "cita")
public class Cita implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcita", nullable = false)
    private Integer id;

    @Column(name = "citacancelada", nullable = false)
    private Boolean citacancelada;

    @ManyToOne
    @JoinColumn(name = "sedes_idsedes", nullable = false)
    private Sede sedesIdsedes;

    @ManyToOne
    @JoinColumn(name = "especialidades_id_especialidad")
    private Especialidade especialidadesIdEspecialidad;

    @ManyToOne
    @JoinColumn(name = "examen_medico_idexamen")
    private ExamenMedico examenMedico;
    @ManyToOne
    @JoinColumn(name = "estadoscita_idestados", nullable = false)
    private Estadoscita estadoscitaIdestados;

    @ManyToOne
    @JoinColumn(name = "receta_idreceta")
    private Receta recetaIdreceta;

    @Size(max=45)
    @NotEmpty
    @Column(name = "formapago", nullable = false, length = 45)
    private String formapago;

    @Size(max=45)
    @NotEmpty
    @Column(name = "modalidad", nullable = false)
    private String modalidad;

    @ManyToOne
    @JoinColumn(name = "tipocita_idtipocita", nullable = false)
    private Tipocita tipocitaIdtipocita;

    @Future
    @NotNull
    @Column(name = "fecha", nullable = false)
    private String fecha;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "hora", nullable = false)
    private LocalTime hora;


    @Column(name = "pagada", nullable = false)
    private Boolean pagada;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paciente_dni", referencedColumnName = "dni", nullable = false)
    private Usuario paciente;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_dni1", referencedColumnName = "dni", nullable = false)
    private Usuario doctor;

    @Column(name = "informe_nuevo_idinforme")
    private Integer idinforme;

}