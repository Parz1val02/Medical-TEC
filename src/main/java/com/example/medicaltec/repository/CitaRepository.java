package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
}