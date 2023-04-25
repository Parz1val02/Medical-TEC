package com.example.medicaltec.repository;

import com.example.medicaltec.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    @Query("select u from Usuario u where u.nombre = ?1")
    List<Usuario> findByNombre(String nombre);


    @Query(nativeQuery = true, value = "select * from Usuario u where u.roles_idroles = 2 and u.sedes_idsedes = 1")
    List<Usuario> obtenerListaPacientes();

    @Query(nativeQuery = true,value = "select * from Usuario u where u.roles_idroles = 1 and u.sedes_idsedes = 1")
    List<Usuario> obtenerlistaDoctores();

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update usuario u set u.email=?1, u.nombre=?2, u.apellido=?3, u.telefono=?4, u.especialidades_id_especialidad=?5 where  u.dni = ?6 and u.sedes_idsedes = ?7")
    void editarDoctor(String email, String nombre, String apellido, String telefono, int especialidad, String id, int sede );

}