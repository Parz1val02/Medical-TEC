package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Cuestionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CuestionarioRepository extends JpaRepository<Cuestionario, Integer> {

}
