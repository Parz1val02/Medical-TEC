package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @Column(name = "dni", nullable = false)

    @Size(max = 8)
    private String id;

    @Size(max = 16)
    @Size(min = 8)
    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 45)
    private String apellido;

    @Column(name = "edad", nullable = false)
    @Min(value = 0)
    private Integer edad;

    @Column(name = "telefono", nullable = false, length = 9)
    private String telefono;

    @Column(name = "sexo", nullable = false, length = 45)
    private String sexo;

    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "sedes_idsedes")
    private Sede sedesIdsedes;

    @ManyToOne
    @JoinColumn(name = "roles_idroles", nullable = false)
    private Role rolesIdroles;

    @ManyToOne
    @JoinColumn(name = "historialmedico_idhistorialmedico")
    private Historialmedico historialmedicoIdhistorialmedico;

    @ManyToOne
    @JoinColumn(name = "especialidades_id_especialidad")
    private Especialidade especialidadesIdEspecialidad;

    @ManyToOne
    @JoinColumn(name = "seguros_id_seguro")
    private Seguro segurosIdSeguro;

    @ManyToOne
    @JoinColumn(name = "estados_idestado")
    private Estado estadosIdestado;

    @Column(name = "modooscuro")
    private Boolean modooscuro;

    @Column(name = "modoregistro", length = 45)
    private String modoregistro;

    @Column(name = "ceduladoctor", length = 45)
    private String ceduladoctor;

    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "fotonombre")
    private String fotonombre;
    @Column(name = "fotocontenttype")
    private String fotocontenttype;
}