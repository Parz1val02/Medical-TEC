package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.FormInvitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FormInvitationRepository extends JpaRepository<FormInvitacion,Integer> {

    @Query(nativeQuery = true,value="select * from form_invitacion where dni = ?")
    FormInvitacion findFormbyPacient(String dni);


    /*QUERYS USADOOS POR ADMINISTRADOR*/
    @Query(nativeQuery = true,value="select * from form_invitacion where id_sede = ?1 and pendiente = 1")
    List<FormInvitacion> findFormbySede(Integer sede);


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE form_invitacion SET pendiente = 0 WHERE idform_invitacion = ?1 ")
    void actualizarEstadoFormRegistroRevisado(Integer idform_invitacion);
    /*FIN QUERYS USADOOS POR ADMINISTRADOR*/
    /* ************************************ */
    /* ************************************ */
}
