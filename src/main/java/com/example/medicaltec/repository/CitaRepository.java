package com.example.medicaltec.repository;

import com.example.medicaltec.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
   @Transactional
   @Modifying
   @Query(nativeQuery = true, value = "insert into cita (citacancelada,sedes_idsedes,especialidades_id_especialidad,estadoscita_idestados,formapago,modalidad,tipocita_idtipocita,fecha,hora) values (0,?1,?2,1,?3,?4,?5,?6,?7)")
   void guardarCita(String idSede, String idEspecialidad, String formaPago, String modadlidad, String idTipoCita, LocalDate fecha, LocalTime hora);
}
