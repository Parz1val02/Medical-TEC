package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.RecetaHasMedicamento;
import com.example.medicaltec.Entity.RecetaHasMedicamentoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecetaHasMedicamentoRepository extends JpaRepository<RecetaHasMedicamento, RecetaHasMedicamentoId> {


    @Query(nativeQuery = true, value = "SELECT * FROM telesystem_2.receta_has_medicamentos inner join medicamentos on medicamentos_idmedicamentos=medicamentos.idmedicamentos where receta_idreceta=?1")
    List<RecetaHasMedicamento> listarMedxId(Integer id);


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value ="insert into receta_has_medicamentos (receta_idreceta, medicamentos_idmedicamentos, cantidad, observaciones) values (?1,?2,?3,?4)" )
    void llenarRecMed(Integer idReceta,Integer idMedicam, Integer cantidad, String observaciones );

}
