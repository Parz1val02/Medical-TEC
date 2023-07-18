package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Tipocita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TipoCitaRepository extends JpaRepository<Tipocita, Integer> {
    @Query(nativeQuery = true, value = "SELECT idtipocita FROM telesystem_2.tipocita where idtipocita=?1")
    String verificarTipoCita(String id);

}