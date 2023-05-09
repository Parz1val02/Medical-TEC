package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.RecetaHasMedicamento;
import com.example.medicaltec.Entity.RecetaHasMedicamentoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaHasMedicamentoRepository extends JpaRepository<RecetaHasMedicamento, RecetaHasMedicamentoId> {
}