package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Historialmedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HistorialMedicoRepository extends JpaRepository<Historialmedico, Integer> {
    @Query(nativeQuery = true, value = "SELECT idhistorialmedico FROM telesystem.historialmedico where idhistorialmedico=?1")
    String verificaridHistorialMedico(String id);
}