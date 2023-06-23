package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Horasdoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HorasDoctorRepository extends JpaRepository<Horasdoctor, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM telesystem_2.horasdoctor where doctor_dni=?1 and lower(mes)=?2")
    Horasdoctor DniMes(String dni, String mes);
}
