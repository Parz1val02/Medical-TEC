package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.ExamenMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExamenMedicoRepository extends JpaRepository<ExamenMedico, Integer> {

    @Query(nativeQuery = true, value = "SELECT idexamen FROM telesystem_2.examen_medico where idexamen=?1")
    String verificarExamen(String id);
}