package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Cita;
import com.example.medicaltec.Entity.Consultorio;
import com.example.medicaltec.dto.ConsultorioxDoctorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConsultorioRepository extends JpaRepository<Consultorio, Integer> {


    @Query(nativeQuery = true, value = "SELECT consul.idconsultorio, consul.nombreconsultorio, s.direccion, s.nombre, s.torres, s.pisos ,c.paciente_dni, c.doctor_dni1, u.nombre FROM telesystem_2.consultorio consul inner join sedes s on consul.sedes_idsedes = s.idsedes inner join cita c on c.sedes_idsedes=s.idsedes  inner join usuario u on consul.dni=u.dni where c.idcita=?1 and u.dni=?2 order by idconsultorio")
    ConsultorioxDoctorDto infoConsultorio(Integer idCita, String dniDoc);

}
