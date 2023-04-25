package com.example.medicaltec.repository;

import com.example.medicaltec.entity.Seguro;
import com.example.medicaltec.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    Usuario findBydni(String dni);
    @Query(nativeQuery = true,value = "select * from usuario u where u.roles_idroles = 1 and u.sedes_idsedes = 1")
    List<Usuario> obtenerlistaDoctores();

}
