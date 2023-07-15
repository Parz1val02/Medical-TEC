package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.ReunionVirtual;
import com.example.medicaltec.dto.CitaxReunionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReunionVirtualRepository extends JpaRepository<ReunionVirtual, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM telesystem_2.reunion_virtual where cita_idcita=?1")
    ReunionVirtual ReuPorCita(Integer idCita);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO reunion_virtual (enlace, room, cita_idcita)\n" +
            "values(?1,null,?2)")
    void guardarReunion(String token, Integer idcita);

    //@Query(nativeQuery = true, value = "SELECT rv.idreunion_virtual, rv.enlace, c.idcita FROM telesystem_2.reunion_virtual rv inner join cita c on rv.cita_idcita=c.idcita\n" +
            //"inner join usuario u on c.paciente_dni=u.dni where rv.cita_idcita=?1")
    //CitaxReunionDto enlacexCita();
}
