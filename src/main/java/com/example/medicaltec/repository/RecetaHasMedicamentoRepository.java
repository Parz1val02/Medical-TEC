package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.RecetaHasMedicamento;
import com.example.medicaltec.Entity.RecetaHasMedicamentoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecetaHasMedicamentoRepository extends JpaRepository<RecetaHasMedicamento, RecetaHasMedicamentoId> {


    @Query(nativeQuery = true, value = "SELECT medicamentos_idmedicamentos FROM telesystem.receta_has_medicamentos where receta_idreceta=?1")
    List<Integer> listarMedxId(Integer id);

}
