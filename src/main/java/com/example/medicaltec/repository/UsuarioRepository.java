package com.example.medicaltec.repository;

import com.example.medicaltec.entity.Usuario;
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
    @Query(nativeQuery = true, value = "select * from Usuario u where u.roles_idroles = 1")
    List<Usuario> obtenerListaPacientes();
    @Query(nativeQuery = true, value = "select * from Usuario u where u.roles_idroles = 2")
    List<Usuario> obtenerListaDoctores();
    @Query(nativeQuery = true, value = "select * from Usuario u where u.roles_idroles = 3")
    List<Usuario> obtenerListaAdministrativos();
    @Query(nativeQuery = true, value = "select * from Usuario u where u.roles_idroles = 4")
    List<Usuario> obtenerListaAdministradores();


    @Query(nativeQuery = true,value = "update usuario u set u.email=?1, u.nombre=?2, u.apellido=?3, u.telefono=?4 where  u.dni = ?5 and u.sedes_idsedes = ?6")
    void editarAdministrativo(String email, String nombre, String apellido, String telefono,  String id, int sede );
    @Query(nativeQuery = true,value = "update usuario u set u.email=?1, u.nombre=?2, u.apellido=?3, u.telefono=?4 where  u.dni = ?5 and u.sedes_idsedes = ?6")
    void editarAdministradores(String email, String nombre, String apellido, String telefono,  String id, int sede );
    @Query(nativeQuery = true,value = "update usuario u set u.email=?1, u.nombre=?2, u.apellido=?3, u.telefono=?4 where  u.dni = ?5 and u.sedes_idsedes = ?6")
    void editarDoctor(String email, String nombre, String apellido, String telefono,  String id, int sede );
    @Query(nativeQuery = true,value = "update usuario u set u.email=?1, u.nombre=?2, u.apellido=?3, u.telefono=?4 where  u.dni = ?5 and u.sedes_idsedes = ?6")
    void editarPaciente(String email, String nombre, String apellido, String telefono, String id);


}