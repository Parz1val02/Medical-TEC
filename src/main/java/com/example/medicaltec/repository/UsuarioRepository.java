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