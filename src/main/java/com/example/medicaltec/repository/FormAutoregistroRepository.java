package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.FormAutoregistro;
import com.example.medicaltec.Entity.FormInvitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FormAutoregistroRepository extends JpaRepository<FormAutoregistro,Integer> {

    @Query(nativeQuery = true, value = "select dni from form_autoregistro")
    List<String> ListaDnis ();

    /*QUERYS USADOOS POR ADMINISTRADOR*/
    @Query(nativeQuery = true,value="select * from form_autoregistro where sedeid = ?1 and pendiente = 1")
    List<FormAutoregistro> findFormAutoRegistroBySede(Integer sede);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE form_autoregistro SET pendiente = 0 WHERE idautoregistro = ?1 ")
    void actualizarEstadoFormAutoRegistroRevisado(Integer idform_autoregistro);
    /*FIN QUERYS USADOOS POR ADMINISTRADOR*/
    /* ************************************ */
    /* ************************************ */

}
