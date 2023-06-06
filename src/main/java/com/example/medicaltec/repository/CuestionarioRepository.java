package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Cuestionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CuestionarioRepository extends JpaRepository<Cuestionario, Integer> {

    //en superadmin
    @Transactional
    @Modifying
    @Query(value = "update Cuestionario c set c.activo = ?1 where c.id = ?2", nativeQuery = true)
    int updateActivoByActivo(Boolean activoNuevo, int id);


    //para paciente
    @Query(nativeQuery = true, value = "SELECT * FROM telesystem_2.cuestionario where paciente_dni=?1")
    Cuestionario cuestionaXPaciente(String dni);

}