package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    @Id
    @Column(name = "dni", nullable = false, length = 8)
    @NotBlank(message = "Es un campo obligatorio")
    @Size(max = 8, message = "Deber ser un número de DNI valido de máximo 8 digitos")
    private String id;

    @Column(name = "contrasena", nullable = false, length = 100)
    private String contrasena;

    @Column(name = "email", nullable = false, length = 100)
    @NotBlank(message = "Es un campo obligatorio")
    @Size(max = 100, message = "El correo ingresado es muy extenso. Máximo 100 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "El correo ingresado no es válido")
    private String email;


    @Column(name = "nombre", nullable = false, length = 45)
    @NotBlank(message = "Es un campo obligatorio")
    @Size(max = 45, message = "El nombre ingresado es muy extenso. Maximo 45 caracteres")
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 45)
    @NotBlank(message = "Es un campo obligatorio")
    @Size(max = 45, message = "El apellido ingresado es muy extenso. Maximo 45 caracteres")
    private String apellido;

    @Column(name = "fechanacimiento", nullable = false)
    @NotNull(message = "Es un campo obligatorio")
    private String fechaNacimiento;

    @Column(name = "telefono", nullable = false, length = 9)
    @NotBlank(message = "Es un campo obligatorio")
    @Size(min = 7,max = 9, message = "El telefono debe ser un numero entero de máximo 9 digitos")
    private String telefono;


    @Column(name = "sexo", nullable = false, length = 45)
    @NotBlank(message = "Es un campo obligatorio")
    @Size(max = 45, message = "Valor ingresado debe tener 45 caracteres como maximo. ")
    private String sexo;

    @Column(name = "direccion", nullable = false, length = 200)
    @NotBlank(message = "Es un campo obligatorio")
    @Size(max = 200, message = "Valor ingresado debe tener 45 caracteres como maximo. ")
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "sedes_idsedes")
    private Sede sedesIdsedes;

    @ManyToOne
    @JoinColumn(name = "roles_idroles", nullable = false)
    private Role rolesIdroles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "historialmedico_idhistorialmedico", referencedColumnName = "idhistorialmedico")
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


    @Column(name = "enabled")
    private Boolean enabled;

}