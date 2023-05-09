package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.HistorialmedicoHasAlergia;
import com.example.medicaltec.Entity.HistorialmedicoHasAlergiaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HistorialMedicoHasAlergiaRepository extends JpaRepository<HistorialmedicoHasAlergia, HistorialmedicoHasAlergiaId> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into historialmedico_has_alergias(historialmedico_idhistorialmedico, alergias_idalergias) values(?1,?2);")
    void aea(Integer id1, Integer id2);

    @Query(nativeQuery = true, value = "select alergias_idalergias from historialmedico_has_alergias where historialmedico_idhistorialmedico=?1")
    List<Integer> listarAlergiasPorId(Integer id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM telesystem.historialmedico_has_alergias WHERE (historialmedico_idhistorialmedico = ?1) and (alergias_idalergias = ?2)")
    void borrarweas(String id1, String id2);
}
