package com.example.medicaltec.repository;

import com.example.medicaltec.entity.Usuario;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,String> {



}
