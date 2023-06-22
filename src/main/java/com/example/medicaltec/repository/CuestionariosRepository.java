package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Cuestionario;
import com.example.medicaltec.Entity.Cuestionarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CuestionariosRepository extends JpaRepository<Cuestionarios,Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `telesystem_2`.`cuestionarios` (`nombre`, `activo`, `preguntas`) " +
            "VALUES (?1, ?2, ?3);",nativeQuery = true)
    void crearCuestionarios(String nombre, int activo,String preguntas);


    @Transactional
    @Modifying
    @Query(value = "update `telesystem_2`.`cuestionarios` set activo = ?1 where idcuestionario = ?2", nativeQuery = true)
    int updateActivoByActivo(Boolean activoNuevo, int id);


    //para paciente
    @Query(nativeQuery = true, value = "SELECT * FROM telesystem_2.cuestionario where paciente_dni=?1 AND activo=1")
    Cuestionario cuestionaXPaciente(String dni);

}
