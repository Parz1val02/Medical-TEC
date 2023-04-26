package com.example.medicaltec.repository;

import com.example.medicaltec.entity.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {
}