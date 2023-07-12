package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Boleta;
import com.example.medicaltec.Entity.Receta;
import com.example.medicaltec.dto.FinanzasDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoletaRepository extends JpaRepository<Boleta,Integer> {

    @Query(value = "select * from boletas where cita_idcita=?1", nativeQuery = true)
    Boleta obtenerCitaxBoleta(Integer id);


    //paciente
    @Transactional
    @Modifying
    @Query(value = "insert into boletas (conceptopago, monto_cita, cita_idcita, seguros_id_seguro, receta_idreceta, monto_Receta) values (?1,?2,?3,?4,null,null)", nativeQuery = true)
    void crearBoletaCita(String concepto, Double monto, Integer idcita, Integer idseguro);

    //querys administrador  finanzas

    @Query(nativeQuery = true, value = "SELECT b.idboletas, b.conceptopago, b.monto_cita, c.formapago, s.nombre_seguro, e.nombre_especialidad,  c.fecha,\n" +
            "paciente.dni as 'dnipaciente', paciente.nombre as 'nombrepaciente', paciente.apellido as 'apellidopaciente',\n" +
            "doctor.dni as 'dnidoctor', doctor.nombre as 'nombredoctor', doctor.apellido as 'apellidodoctor'\n" +
            "FROM telesystem_2.boletas b \n" +
            "left join cita c on (b.cita_idcita = c.idcita)\n" +
            "left join seguros s on (s.id_seguro = b.seguros_id_seguro) \n" +
            "left join receta r on (r.idreceta = b.receta_idreceta)\n" +
            "left join especialidades e on (e.id_especialidad = c.especialidades_id_especialidad) \n" +
            "left join usuario AS paciente on (paciente.dni = c.paciente_dni)\n" +
            "left join usuario AS doctor on (doctor.dni = c.doctor_dni1)\n" +
            "order by b.idboletas ")
    List<FinanzasDto> tablaFinanzasPrincipal();

    @Query(nativeQuery = true, value = "SELECT b.idboletas, b.conceptopago, b.monto_cita, c.formapago, s.nombre_seguro, e.nombre_especialidad,  c.fecha,\n" +
            "paciente.dni as 'dnipaciente', paciente.nombre as 'nombrepaciente', paciente.apellido as 'apellidopaciente',\n" +
            "doctor.dni as 'dnidoctor', doctor.nombre as 'nombredoctor', doctor.apellido as 'apellidodoctor'\n" +
            "FROM telesystem_2.boletas b \n" +
            "left join cita c on (b.cita_idcita = c.idcita)\n" +
            "left join seguros s on (s.id_seguro = b.seguros_id_seguro) \n" +
            "left join receta r on (r.idreceta = b.receta_idreceta)\n" +
            "left join especialidades e on (e.id_especialidad = c.especialidades_id_especialidad) \n" +
            "left join usuario AS paciente on (paciente.dni = c.paciente_dni)\n" +
            "left join usuario AS doctor on (doctor.dni = c.doctor_dni1)\n" +
            "WHERE e.id_especialidad = ?1 \n" +
            "order by b.idboletas ")
    List<FinanzasDto> tablaFinanzasEspecialidad(int idEspecialidad);


    @Query(nativeQuery = true, value = "SELECT b.idboletas, b.conceptopago, b.monto_cita, c.formapago, s.nombre_seguro, e.nombre_especialidad,  c.fecha,\n" +
            "paciente.dni as 'dnipaciente', paciente.nombre as 'nombrepaciente', paciente.apellido as 'apellidopaciente',\n" +
            "doctor.dni as 'dnidoctor', doctor.nombre as 'nombredoctor', doctor.apellido as 'apellidodoctor'\n" +
            "FROM telesystem_2.boletas b \n" +
            "left join cita c on (b.cita_idcita = c.idcita)\n" +
            "left join seguros s on (s.id_seguro = b.seguros_id_seguro) \n" +
            "left join receta r on (r.idreceta = b.receta_idreceta)\n" +
            "left join especialidades e on (e.id_especialidad = c.especialidades_id_especialidad) \n" +
            "left join usuario AS paciente on (paciente.dni = c.paciente_dni)\n" +
            "left join usuario AS doctor on (doctor.dni = c.doctor_dni1)\n" +
            "WHERE s.id_seguro = ?1 \n" +
            "order by b.idboletas ")
    List<FinanzasDto> tablaFinanzasSeguro(int idSeguro);


    //fin querys administrador  finanzas

}
