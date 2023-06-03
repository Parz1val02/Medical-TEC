package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje,Integer> {

    @Query(value = "SELECT * FROM telesystem_2.mensajes order by fecha DESC;",
    nativeQuery = true)
    List<Mensaje> listarMensajesMasActuales();
}
