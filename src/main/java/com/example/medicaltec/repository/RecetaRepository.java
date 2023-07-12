package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Receta;
import com.example.medicaltec.dto.RecetaMedicamentoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecetaRepository extends JpaRepository<Receta, Integer> {


    @Query(nativeQuery = true, value = "SELECT m.idmedicamentos, m.nombre, m.precio, m.cantidad, m.frecuencia FROM telesystem_2.receta r\n" +
            "inner join receta_has_medicamentos rhm on r.idreceta=rhm.receta_idreceta\n" +
            "inner join medicamentos m on m.idmedicamentos=rhm.medicamentos_idmedicamentos\n" +
            "where idreceta=?1")
    List<RecetaMedicamentoDto> RecetasxMedicam(int idReceta);


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into receta (comentario) values ('?1')")
    void crearReceta( String comentario);
}