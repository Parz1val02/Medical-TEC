package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Cuestionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CuestionarioRepository extends JpaRepository<Cuestionario, Integer> {

    //en superadmin
    @Transactional
    @Modifying
    @Query("update Cuestionario c set c.activo = ?1 where c.id = ?2")
    int updateActivoByActivo(Boolean activoNuevo, int id);

}