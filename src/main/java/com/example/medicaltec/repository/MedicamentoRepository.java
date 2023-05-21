package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Alergia;
import com.example.medicaltec.Entity.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {

    @Query(value = "SELECT * FROM telesystem.medicamentos where idmedicamentos=?1", nativeQuery = true)
    Medicamento obtenerMedicamento(Integer id);


}
