package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    @Transactional
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


}
