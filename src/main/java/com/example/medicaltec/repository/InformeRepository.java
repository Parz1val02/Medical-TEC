package com.example.medicaltec.repository;

import com.example.medicaltec.entity.Informe;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InformeRepository extends JpaRepository<Informe,Integer> {

    @Query(value = "SELECT * FROM telesystem.informe WHERE cita_idcita=?1;",
            nativeQuery = true)
    Informe listarInformePorCita(int id_cita);
}
