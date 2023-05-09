package com.example.medicaltec.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @Column(name = "dni", nullable = false, length = 8)
    @NotBlank(message = "Es un campo obligatorio")
    @Size(max = 8, message = "Deber se un numero de DNI valido de maximo 8 digitos")
    private String id;

    @Column(name = "contrasena", nullable = false, length = 45)
    private String contrasena;

   @Column(name = "email", nullable = false, length = 100)
   @NotBlank(message = "Es un campo obligatorio")
   @Size(max = 100, message = "El correo ingresado es muy extenso. Maximo 100 caracteres")
   private String email;


    @Column(name = "nombre", nullable = false, length = 45)
    @NotBlank(message = "Es un campo obligatorio")
    @Size(max = 45, message = "El nombre ingresado es muy extenso. Maximo 45 caracteres")
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 45)
    @NotBlank(message = "Es un campo obligatorio")
    @Size(max = 45, message = "El apellido ingresado es muy extenso. Maximo 45 caracteres")
    private String apellido;

    @Column(name = "edad", nullable = false)
    @NotNull(message = "Es un campo obligatorio")
    @Digits(integer= 3, fraction= 0, message = "La edad debe ser un numero entero positivo")
    @Max(value = 120,message = "La edad debe ser un numero entero positivo")
    @Min(value = 0, message = "La edad debe ser un numero entero positivo")
    private Integer edad;

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

    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "modoregistro", length = 45)
    private String modoregistro;

    @Column(name = "ceduladoctor", length = 45)
    private String ceduladoctor;

}