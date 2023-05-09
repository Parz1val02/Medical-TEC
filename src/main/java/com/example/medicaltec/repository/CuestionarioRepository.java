package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Cuestionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuestionarioRepository extends JpaRepository<Cuestionario, Integer> {
}