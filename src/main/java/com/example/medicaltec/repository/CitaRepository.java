package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

   @Query(nativeQuery = true, value = "SELECT * FROM telesystem.cita where citacancelada=0 and paciente_dni=?1")
   List<Cita> historialCitas(String dniPaciente);

   @Query(nativeQuery = true, value = "SELECT * FROM telesystem.cita where fecha < current_date() and citacancelada=0 and paciente_dni=?1")
   List<Cita> historialCitas2(String dniPaciente);

   @Query(nativeQuery = true, value = "SELECT * FROM telesystem.cita where fecha >= current_date() and citacancelada=0 and paciente_dni=?1")
   List<Cita> historialCitasAgendadas(String dniPaciente);

}
