package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {


    @Query(nativeQuery = true, value = "select * from Usuario u where u.roles_idroles = 2 and u.sedes_idsedes = 1")
    List<Usuario> obtenerListaPacientes();

    @Query(nativeQuery = true,value = "select * from Usuario u where u.roles_idroles = 1 and u.sedes_idsedes = 1")
    List<Usuario> obtenerlistaDoctores();

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update usuario u set u.email=?1, u.nombre=?2, u.apellido=?3, u.telefono=?4, u.especialidades_id_especialidad=?5 where  u.dni = ?6 and u.sedes_idsedes = ?7")
    void editarDoctor(String email, String nombre, String apellido, String telefono, int especialidad, String dni, int sede );

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update usuario u set u.email=?1, u.nombre=?2, u.apellido=?3, u.telefono=?4 where  u.dni = ?5 and u.sedes_idsedes = ?6")
    void editarPaciente(String email, String nombre, String apellido, String telefono, String dni, int sede );



    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "INSERT INTO usuario (email, nombre, apellido, telefono, especialidades_id_especialidad, dni, sedes_idsedes, edad, direccion, sexo, contrasena, roles_idroles, estados_idestado) VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,1,1)")
    void crearDoctor(String email, String nombre, String apellido, String telefono, int especialidad, String dni, int sede, int edad, String direccion, String sexo, String contrasena  );

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "INSERT INTO usuario (email, nombre, apellido, telefono, dni, sedes_idsedes, edad, direccion, sexo, contrasena, roles_idroles, estados_idestado) VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,2,1)")
    void crearPaciente(String email, String nombre, String apellido, String telefono, String dni, int sede, int edad, String direccion, String sexo, String contrasena  );


    @Modifying
    @Query(nativeQuery = true, value="UPDATE usuario SET `contrasena` = ?1 WHERE (`dni` = '34185296')")
    void cambiarContra(String pass);

    @Query(value="select contrasena from usuario where dni=\"34185296\"",nativeQuery = true)
    String passAdmv();
    Usuario findByid(String id);
    @Query(nativeQuery = true,value = "select * from usuario u where u.roles_idroles = 1 and u.sedes_idsedes = ?1")
    List<Usuario> obtenerlistaDoctores(Integer idSede);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value ="update usuario set seguros_id_seguro=?1 where dni=\"22647853\";" )
    Boolean cambiarSeguro(String idSede);


    @Query(value = "select * from telesystem.usuario u where u.roles_idroles = 1", nativeQuery = true)
    List<Usuario> listarDoctores();

    @Query(value = "select * from telesystem.usuario u where u.roles_idroles = 2", nativeQuery = true)
    List<Usuario> listarPacientes();

    @Query("select u from Usuario u where u.nombre = ?1")
    List<Usuario> findByNombre(String nombre);
    @Modifying
    @Transactional
    @Query(value = "UPDATE telesystem.usuario u SET u.sedes_idsedes = ?1 WHERE dni=\"12345678\"", nativeQuery = true)
    void actualizarSede(int id_nuevo);
}

