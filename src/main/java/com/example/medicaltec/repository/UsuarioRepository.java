package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "select * from Usuario u where u.roles_idroles = 2")
    List<Usuario> obtenerListaPacientes();
    @Query(nativeQuery = true, value = "select * from Usuario u where u.roles_idroles = 1")
    List<Usuario> obtenerListaDoctores();
    @Query(nativeQuery = true, value = "select * from Usuario u where u.roles_idroles = 3")
    List<Usuario> obtenerListaAdministrativos();
    @Query(nativeQuery = true, value = "select * from Usuario u where u.roles_idroles = 4")
    List<Usuario> obtenerListaAdministradores();

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update usuario u set u.email= ?1, u.nombre= ?2, u.apellido= ?3, u.sedes_idsedes = ?4, u.telefono= ?5, u.estados_idestado = ?6, u.especialidades_id_especialidad = ?7 where  u.dni = ?8")
    void editarAdministrativo(String email, String nombre, String apellido, int sede, String telefono,  int estado, int especialidad, String dni);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update usuario u set u.email= ?1, u.nombre= ?2, u.apellido= ?3, u.sedes_idsedes = ?4, u.telefono= ?5, u.estados_idestado = ?6 where  u.dni = ?7")
    void editarAdministradores(String email, String nombre, String apellido, int sede, String telefono,  int estado,  String dni);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update usuario u set u.email= ?1, u.nombre= ?2, u.apellido= ?3, u.sedes_idsedes = ?4, u.telefono= ?5, u.estados_idestado = ?6, u.especialidades_id_especialidad = ?7 where  u.dni = ?8")
    void editarDoctor(String email, String nombre, String apellido, int sede, String telefono,  int estado, int especialidad, String dni);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update usuario u set u.email= ?1, u.nombre= ?2, u.apellido= ?3, u.sedes_idsedes = ?4, u.telefono= ?5, u.estados_idestado = ?6 where  u.dni = ?7 ")
    void editarPaciente(String email, String nombre, String apellido, int sede, String telefono,  int estado,  String dni);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `telesystem`.`usuario` (`dni`, `contrasena`, `email`, `nombre`, `apellido`, `edad`, `telefono`, `sexo`, `direccion`, `sedes_idsedes`, `roles_idroles`) " +
            "VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, 4);",nativeQuery = true)
    void crearAdmSede(String dni, String password,String email,String nombre, String apellido,int edad, String telefono, String sexo, String direccion, int sede);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `telesystem`.`usuario` (`dni`, `contrasena`, `email`, `nombre`, `apellido`, `edad`, `telefono`, `sexo`, `direccion`, `sedes_idsedes`, `roles_idroles`) " +
            "VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, 3);",nativeQuery = true)
    void crearAdmT(String dni, String password,String email,String nombre,String apellido, int edad, String telefono, String sexo, String direccion, int sede);


    @Transactional
    @Modifying
    @Query(value = "update `telesystem`.`usuario` set nombre = ?1, apellido=?2, email=?3, telefono=?4, sedes_idsedes =?5, estados_idestado=?6 where dni = ?7",nativeQuery = true)
    void editAdmS(String nombre, String apellido,String email,String telefono, int sede, int estado, String dni);

    @Transactional
    @Modifying
    @Query(value = "update `telesystem`.`usuario` set nombre = ?1, apellido=?2, email=?3, telefono=?4, dni =?5 where roles_idroles = 5",nativeQuery = true)
    void editSuperAdmin(String nombre, String apellido,String email,String telefono, String dni);


}