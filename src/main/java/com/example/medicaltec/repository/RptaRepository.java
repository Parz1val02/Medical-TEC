package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RptaRepository extends JpaRepository<Respuesta, Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into respuestas (respuesta, preguntas_idpreguntas, historialmedico_idhistorialmedico) values (?1, 1, 1)", nativeQuery = true)
    void guardarRptas(String respuesta);

}
