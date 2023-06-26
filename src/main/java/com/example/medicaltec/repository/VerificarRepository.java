package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Verificar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface VerificarRepository extends JpaRepository<Verificar,String> {


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="update verificar set valido = \"1\",fecha=?1 where dni = ?2")
    void verifyRegistro (String fecha,String dni);



    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="INSERT INTO `telesystem_2`.`verificar` (`dni`, `fecha`) VALUES (?1, ?2);\n")
    void crearInicioInvitacion (String id,String fecha);

}
