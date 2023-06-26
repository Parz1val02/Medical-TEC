package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.CuestionariosUsuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CuestionariosUsuariosRepository extends JpaRepository<CuestionariosUsuarios,Integer> {

    //para paciente
    @Query(nativeQuery = true, value = "SELECT cu.*\n" +
            "FROM telesystem_2.cuestionarios_usuarios cu\n" +
            "JOIN telesystem_2.cuestionarios c ON cu.idcuestionario = c.idcuestionario\n" +
            "WHERE cu.dnipaciente = ?1\n" +
            "  AND c.activo = 1;\n")
    List<CuestionariosUsuarios> cuestionarioXPaciente(String dni);


    @Transactional
    @Modifying
    @Query(value = "update `telesystem_2`.`cuestionarios_usuarios` set respuestas = ?1,respondido = 1 where id = ?2", nativeQuery = true)
    void responderCuestionario(String respuestas, int id);
}
