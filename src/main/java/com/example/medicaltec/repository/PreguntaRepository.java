package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Pregunta;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PreguntaRepository extends JpaRepository<Pregunta, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM telesystem_2.preguntas where cuestionario_idcuestionario=?1")
    List<Pregunta> obtenerPreguntas(int id);
}
