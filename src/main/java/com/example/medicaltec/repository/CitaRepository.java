package com.example.medicaltec.repository;

import com.example.medicaltec.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    @Query(value = "SELECT * FROM telesystem.cita WHERE doctor_dni1=\"12345678\" " +
                    "AND estadoscita_idestados=3 ORDER BY fecha DESC, hora DESC;",
                    nativeQuery = true)
    List<Cita> pacientesAtendidos();
}