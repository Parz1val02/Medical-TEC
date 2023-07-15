package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Alergia;
import com.example.medicaltec.Entity.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {

    @Query(value = "SELECT * FROM telesystem_2.medicamentos where idmedicamentos=?1", nativeQuery = true)
    Medicamento obtenerMedicamento(Integer id);


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into medicamentos (nombre, precio) values ('?1',?2)")
    void insertMedicamento(String nombre, Double precio);
}
