package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Horasdoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

public interface HorasDoctorRepository extends JpaRepository<Horasdoctor, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM telesystem_2.horasdoctor where doctor_dni=?1 and lower(mes)=?2")
    Horasdoctor obtenerHorasDoctorEnMes(String dni, String mes);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into horasdoctor (horainicio, horafin, horalibre, doctor_dni, dias, mes) values (?1, ?2, ?3, ?4, ?5, ?6)")
    void guardarHorasDoc(LocalTime horaIni, LocalTime horaFin, LocalTime horaLibre, String doctorDNI, String dias, String mes );


}



