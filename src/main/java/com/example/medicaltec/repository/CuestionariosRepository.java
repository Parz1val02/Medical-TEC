package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Cuestionario;
import com.example.medicaltec.Entity.Cuestionarios;
import com.example.medicaltec.Entity.CuestionariosUsuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CuestionariosRepository extends JpaRepository<Cuestionarios,Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `telesystem_2`.`cuestionarios` (`nombre`, `activo`, `preguntas`) " +
            "VALUES (?1, ?2, ?3);",nativeQuery = true)
    void crearCuestionarios(String nombre, int activo,String preguntas);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `telesystem_2`.`cuestionarios_usuarios` (`idcuestionario`, `dnipaciente`, `respuestas`,`respondido`,`dnidoctor`) " +
            "VALUES (?1, ?2, ?3,?4,?5);",nativeQuery = true)
    void asignarCuestionario(int cuestionario, String paciente,String respuestas,int respondido,String doctor);

    @Transactional
    @Modifying
    @Query(value = "update `telesystem_2`.`cuestionarios` set activo = ?1 where idcuestionario = ?2", nativeQuery = true)
    int updateActivoByActivo(Boolean activoNuevo, int id);



}
