package com.example.medicaltec.repository;

import com.example.medicaltec.entity.Usuario;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioRepository extends JpaRepository<Usuario,String> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value="UPDATE usuario SET `contrasena` = ?1 WHERE (`dni` = '34185296')")
    void cambiarContra(String pass);




}
