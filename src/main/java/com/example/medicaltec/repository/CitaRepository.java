package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Cita;
import com.example.medicaltec.dto.CitaxReunionDto;
import com.example.medicaltec.dto.ValidarCitaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    @Query(value = "SELECT * FROM telesystem_2.cita", nativeQuery = true)
    List<Cita> listarCitas();

    @Query(nativeQuery = true, value = "SELECT * FROM telesystem_2.cita where str_to_date(fecha, '%d-%m-%Y') < current_date() and citacancelada=0 and pagada=1 and estadoscita_idestados=3 and paciente_dni=?1")
    List<Cita> historialCitas2(String dniPaciente);

    @Query(value = "SELECT c.* FROM telesystem_2.cita c INNER JOIN telesystem_2.usuario u ON c.paciente_dni = u.dni WHERE c.doctor_dni1=?1 " +
            "AND c.estadoscita_idestados=3 AND u.enabled = 1 ORDER BY fecha DESC, hora DESC;",
            nativeQuery = true)
    List<Cita> pacientesAtendidos(String dni);

    @Query(value = "SELECT c.paciente_dni\n" +
            "    FROM telesystem_2.cita c\n" +
            "    JOIN telesystem_2.usuario u ON c.paciente_dni = u.dni\n" +
            "    WHERE c.doctor_dni1 = ?1\n" +
            "    AND c.estadoscita_idestados = 3\n" +
            "    AND u.enabled = 1\n" +
            "    GROUP BY c.paciente_dni",nativeQuery = true)
    List<String> pacientesdeldoctor(String dni);

    @Query(value = "SELECT * FROM telesystem_2.cita WHERE doctor_dni1=?1 " +
            "AND estadoscita_idestados=1 OR estadoscita_idestados=2 OR estadoscita_idestados=4 OR estadoscita_idestados=5 ORDER BY fecha DESC, hora DESC;",
            nativeQuery = true)
    List<Cita> citasParaVer(String dni);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update cita set citacancelada=1 where idcita=?1")
    void cancelarCita(Integer id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update cita set pagada=1 where idcita=?1")
    void pagarCita(Integer id);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update cita set estadoscita_idestados=2 where idcita=?1")
    void estadoPagada(Integer id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update cita set estadoscita_idestados=?1 where idcita=?2 ")
    void cambiarEstadoCita( int idEstado , int idCita);

    //para ver cual es la cita en transcurso
    @Query(nativeQuery = true, value = "select idcita, paciente_dni from telesystem_2.cita where estadoscita_idestados=2")
    Cita citaEnTranscurso();


    //cambair a estado de cita en transcurso
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update cita set estadoscita_idestados=2 where idcita=?1 and fecha=current_date() and hora=?2 and pagada=1 and citacancelada=0 ")
    void cambiarEstadoCitaEnTranscurso(int idCita, LocalTime hora);

    //para validar cita

    @Query(nativeQuery = true,value="SELECT \n" +
            "    c.idcita,\n" +
            "    CONCAT(p.nombre, \" \", p.apellido) AS `fullname`,\n" +
            "    CONCAT(c.fecha, \" \", c.hora) AS `fecha`,\n" +
            "    t.tipo_cita, \n" +
            "    e.nombre_especialidad,\n" +
            "    c.sedes_idsedes,\n" +
            "    c.pagada,\n" +
            "    CONCAT(d.nombre, \" \", d.apellido) AS `fullnameDoc`,\n" +
            "    c.citacancelada,\n" +
            "    s.nombre_seguro,\n" +
            "    em.nombre AS `ExamenMedico`,\n" +
            "    CASE\n" +
            "        WHEN t.tipo_cita = 'Consulta médica' THEN\n" +
            "            CASE\n" +
            "                WHEN s.nombre_seguro = 'rimac-EPS' THEN 60 * 0.85\n" +
            "                WHEN s.nombre_seguro = 'Rimac seguros' THEN 60 * 0.75\n" +
            "                WHEN s.nombre_seguro = 'Pacifico EPS' THEN 60 * 0.9\n" +
            "                WHEN s.nombre_seguro = 'Pacifico Seguros' THEN 60 * 0.8\n" +
            "                WHEN s.nombre_seguro = 'Mapfre' THEN 60 * 0.6\n" +
            "                WHEN s.nombre_seguro = 'Plan Salud' THEN 60 * 0.9\n" +
            "                ELSE 60\n" +
            "            END\n" +
            "        WHEN t.tipo_cita = 'Examen médico' THEN\n" +
            "            CASE\n" +
            "                WHEN em.nombre IN ('examen fisico', 'pruebas de orina') THEN\n" +
            "                    CASE\n" +
            "                        WHEN s.nombre_seguro = 'rimac-EPS' THEN 30 * 0.85\n" +
            "                        WHEN s.nombre_seguro = 'Rimac seguros' THEN 30 * 0.75\n" +
            "                        WHEN s.nombre_seguro = 'Pacifico EPS' THEN 30 * 0.9\n" +
            "                        WHEN s.nombre_seguro = 'Pacifico Seguros' THEN 30 * 0.8\n" +
            "                        WHEN s.nombre_seguro = 'Mapfre' THEN 30 * 0.6\n" +
            "                        WHEN s.nombre_seguro = 'Plan Salud' THEN 30 * 0.9\n" +
            "                        ELSE 30\n" +
            "                    END\n" +
            "                WHEN em.nombre IN ('analisis de sangre') THEN\n" +
            "                    CASE\n" +
            "                        WHEN s.nombre_seguro = 'rimac-EPS' THEN 50 * 0.85\n" +
            "                        WHEN s.nombre_seguro = 'Rimac seguros' THEN 50 * 0.75\n" +
            "                        WHEN s.nombre_seguro = 'Pacifico EPS' THEN 50 * 0.9\n" +
            "                        WHEN s.nombre_seguro = 'Pacifico Seguros' THEN 50 * 0.8\n" +
            "                        WHEN s.nombre_seguro = 'Mapfre' THEN 50 * 0.6\n" +
            "                        WHEN s.nombre_seguro = 'Plan Salud' THEN 50 * 0.9\n" +
            "                        ELSE 50\n" +
            "                    END\n" +
            "                WHEN em.nombre IN ('radiografia', 'ecografia', 'electrocardiograma (ECG)') THEN\n" +
            "                    CASE\n" +
            "                        WHEN s.nombre_seguro = 'Rimac-EPS' THEN 100 * 0.85\n" +
            "                        WHEN s.nombre_seguro = 'Rimac seguros' THEN 100 * 0.75\n" +
            "                        WHEN s.nombre_seguro = 'Pacifico EPS' THEN 100 * 0.9\n" +
            "                        WHEN s.nombre_seguro = 'Pacifico Seguros' THEN 100 * 0.8\n" +
            "                        WHEN s.nombre_seguro = 'Mapfre' THEN 100 * 0.6\n" +
            "                        WHEN s.nombre_seguro = 'Plan Salud' THEN 100 * 0.9\n" +
            "                        ELSE 100\n" +
            "                    END\n" +
            "                WHEN em.nombre IN ('mamografía') THEN\n" +
            "                    CASE\n" +
            "                        WHEN s.nombre_seguro = 'Rimac-EPS' THEN 125 * 0.85\n" +
            "                        WHEN s.nombre_seguro = 'Rimac seguros' THEN 125 * 0.75\n" +
            "                        WHEN s.nombre_seguro = 'Pacifico EPS' THEN 125 * 0.9\n" +
            "                        WHEN s.nombre_seguro = 'Pacifico Seguros' THEN 125 * 0.8\n" +
            "                        WHEN s.nombre_seguro = 'Mapfre' THEN 125 * 0.6\n" +
            "                        WHEN s.nombre_seguro = 'Plan Salud' THEN 125 * 0.9\n" +
            "                        ELSE 125\n" +
            "                    END\n" +
            "                WHEN em.nombre IN ('colonoscopia') THEN\n" +
            "                    CASE\n" +
            "                        WHEN s.nombre_seguro = 'Rimac-EPS' THEN 200 * 0.85\n" +
            "                        WHEN s.nombre_seguro = 'Rimac seguros' THEN 200 * 0.75\n" +
            "                        WHEN s.nombre_seguro = 'Pacifico EPS' THEN 200 * 0.9\n" +
            "                        WHEN s.nombre_seguro = 'Pacifico Seguros' THEN 200 * 0.8\n" +
            "                        WHEN s.nombre_seguro = 'Mapfre' THEN 200 * 0.6\n" +
            "                        WHEN s.nombre_seguro = 'Plan Salud' THEN 200 * 0.9\n" +
            "                        ELSE 200\n" +
            "                    END\n" +
            "                WHEN em.nombre IN ('prueba de esfuerzo') THEN\n" +
            "                    CASE\n" +
            "                        WHEN s.nombre_seguro = 'Rimac-EPS' THEN 75 * 0.85\n" +
            "                        WHEN s.nombre_seguro = 'Rimac seguros' THEN 75 * 0.75\n" +
            "                        WHEN s.nombre_seguro = 'Pacifico EPS' THEN 75 * 0.9\n" +
            "                        WHEN s.nombre_seguro = 'Pacifico Seguros' THEN 75 * 0.8\n" +
            "                        WHEN s.nombre_seguro = 'Mapfre' THEN 75 * 0.6\n" +
            "                        WHEN s.nombre_seguro = 'Plan Salud' THEN 75 * 0.9\n" +
            "                        ELSE 75\n" +
            "                    END\n" +
            "                WHEN em.nombre IN ('examen de vista') THEN\n" +
            "                    CASE\n" +
            "                        WHEN s.nombre_seguro = 'Rimac-EPS' THEN 50 * 0.85\n" +
            "                        WHEN s.nombre_seguro = 'Rimac seguros' THEN 50 * 0.75\n" +
            "                        WHEN s.nombre_seguro = 'Pacifico EPS' THEN 50 * 0.9\n" +
            "                        WHEN s.nombre_seguro = 'Pacifico Seguros' THEN 50 * 0.8\n" +
            "                        WHEN s.nombre_seguro = 'Mapfre' THEN 50 * 0.6\n" +
            "                        WHEN s.nombre_seguro = 'Plan Salud' THEN 50 * 0.9\n" +
            "                        ELSE 50\n" +
            "                    END\n" +
            "                ELSE 0\n" +
            "            END\n" +
            "        ELSE 0\n" +
            "    END AS precio_calculado\n" +
            "FROM cita c\n" +
            "INNER JOIN usuario p ON p.dni = c.paciente_dni\n" +
            "INNER JOIN seguros s ON s.id_seguro = p.seguros_id_seguro\n" +
            "INNER JOIN usuario d ON d.dni = c.doctor_dni1\n" +
            "LEFT JOIN tipocita t ON t.idtipocita = c.tipocita_idtipocita\n" +
            "LEFT JOIN especialidades e ON c.especialidades_id_especialidad = e.id_especialidad\n" +
            "LEFT JOIN examen_medico em ON em.idexamen = c.examen_medico_idexamen")
            List<ValidarCitaDto> validarCitasList();





    @Modifying
    @Transactional
    @Query(value = "update cita set receta_idreceta = ?1, informe_idinforme=?2 where idcita = ?3",nativeQuery = true)
    void informeRecetaCita (Integer idreceta, Integer idinforme, Integer idcita);

}