package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Cita;
import com.example.medicaltec.Entity.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultorioRepository extends JpaRepository<Consultorio, Integer> {

}
