package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.FormInvitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FormInvitationRepository extends JpaRepository<FormInvitacion,Integer> {

    @Query(nativeQuery = true,value="select * from form_invitacion where dni = ?")
    FormInvitacion findFormbyPacient(String dni);

}
