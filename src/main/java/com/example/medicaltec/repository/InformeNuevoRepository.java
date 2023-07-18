package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.InformeNuevo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface InformeNuevoRepository extends JpaRepository<InformeNuevo,Integer> {


    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `telesystem_2`.`informe_nuevo` (`nombre`,`activo`, `campos`) " +
            "VALUES (?1,1,?2);",nativeQuery = true)
    void crearInforme(String nombre,String campos);
}
