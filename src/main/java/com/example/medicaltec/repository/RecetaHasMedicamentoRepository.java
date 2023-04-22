package com.example.medicaltec.repository;

import com.example.medicaltec.entity.RecetaHasMedicamento;
import com.example.medicaltec.entity.RecetaHasMedicamentoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaHasMedicamentoRepository extends JpaRepository<RecetaHasMedicamento, RecetaHasMedicamentoId> {
}