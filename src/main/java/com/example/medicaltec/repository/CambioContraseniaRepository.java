package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.CambioContrasenia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CambioContraseniaRepository extends JpaRepository<CambioContrasenia,Integer> {

    @Query(nativeQuery = true,value="select codigo from cambio_contrasenia where fecha = ?1")
    String codigoByFecha(String fecha);
}
