package com.example.medicaltec.repository;

import com.example.medicaltec.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaRepository extends JpaRepository<Receta, Integer> {
}