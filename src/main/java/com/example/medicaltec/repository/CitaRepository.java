package com.example.medicaltec.repository;

import com.example.medicaltec.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
}