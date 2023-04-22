package com.example.medicaltec.repository;

import com.example.medicaltec.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    @Query("select u from Usuario u where u.nombre = ?1")
    List<Usuario> findByNombre(String nombre);


}