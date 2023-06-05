package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje,Integer> {

    @Query(value = "SELECT * FROM telesystem_2.mensajes WHERE receptorDNI = ?1 ORDER BY fecha DESC, hora DESC;",
    nativeQuery = true)
    List<Mensaje> listarMensajesPorReceptor(String receptorDNI);
}
