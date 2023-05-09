package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.HistorialmedicoHasAlergia;
import com.example.medicaltec.Entity.HistorialmedicoHasAlergiaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistorialMedicoHasAlergiaRepository2 extends JpaRepository<HistorialmedicoHasAlergia, HistorialmedicoHasAlergiaId>{

    @Query(nativeQuery = true, value = "select alergias_idalergias from historialmedico_has_alergias where historialmedico_idhistorialmedico=?1")
    List<Integer> listarAlergiasPorId(Integer id);

}
