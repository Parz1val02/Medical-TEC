package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Boleta;
import com.example.medicaltec.Entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoletaRepository extends JpaRepository<Boleta,Integer> {

    @Query(value = "select * from boletas where cita_idcita=?1", nativeQuery = true)
    Boleta obtenerCitaxBoleta(Integer id);
}
