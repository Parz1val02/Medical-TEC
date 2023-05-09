package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
    @Query(value = "SELECT * FROM telesystem.cita WHERE doctor_dni1=\"12345678\" " +
            "AND estadoscita_idestados=3 ORDER BY fecha DESC, hora DESC;",
            nativeQuery = true)
    List<Cita> pacientesAtendidos();

    @Query(value = "SELECT * FROM telesystem.cita WHERE doctor_dni1=\"12345678\" " +
            "AND estadoscita_idestados=1 ORDER BY fecha DESC, hora DESC;",
            nativeQuery = true)
    List<Cita> proximasCitasAgendadas();

    @Query(value = "SELECT * FROM telesystem.cita WHERE paciente_dni=?1 AND fecha<now() ORDER BY fecha DESC, hora DESC;",
            nativeQuery = true)
    List<Cita> citasPorUsuario(String id_paciente);
}