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
    @Query(nativeQuery = true, value = "update usuario u set u.estados_idestado=?1")
    void editarEstado(String estado);

}