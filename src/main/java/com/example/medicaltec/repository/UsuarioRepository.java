package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Seguro;
import com.example.medicaltec.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    Usuario findByid(String id);
    @Query(nativeQuery = true,value = "select * from usuario u where u.roles_idroles = 1 and u.sedes_idsedes = ?1")
    List<Usuario> obtenerlistaDoctores(Integer idSede);



}
