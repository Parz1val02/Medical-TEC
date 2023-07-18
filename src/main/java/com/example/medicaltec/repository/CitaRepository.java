package com.example.medicaltec.repository;

import com.example.medicaltec.Entity.Cita;
import com.example.medicaltec.dto.CitaxReunionDto;
import com.example.medicaltec.dto.RecetaValidarDto;
import com.example.medicaltec.dto.ValidarCitaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    @Query(value = "SELECT * FROM telesystem_2.cita WHERE doctor_dni1=?1", nativeQuery = true)
    List<Cita> listarCitas(String dnidoctor);

    @Query(value = "SELECT * FROM telesystem_2.cita WHERE tipocita_idtipocita=1 AND doctor_dni1=?1 AND paciente_dni=?2 ORDER BY fecha DESC, hora DESC LIMIT 1;", nativeQuery = true)
    Cita obtenerCitaPorPacienteyDoctor(String dnidoctor, String dnipaciente);

    @Query(value = "SELECT * FROM telesystem_2.cita WHERE tipocita_idtipocita=2 AND  examen_medico_idexamen=?1 AND paciente_dni=?2 ORDER BY fecha DESC, hora DESC LIMIT 1;", nativeQuery = true)
    Cita obtenerExamenPorPacienteyDoctor(String examenmedico, String dnipaciente);

    @Query(value = "UPDATE `telesystem_2`.`cita` SET `pagada` = '1' WHERE (`idcita` = ?1);",nativeQuery = true)
    void cambiarPagadoCita(int idcita);

    @Query(value = "UPDATE `telesystem_2`.`cita` SET `pagada` = '0' WHERE (`idcita` = ?1);",nativeQuery = true)
    void cambiarNoPagadoCita(int idcita);

    @Query(nativeQuery = true, value = "SELECT * FROM telesystem_2.cita where str_to_date(fecha, '%d-%m-%Y') < current_date() and citacancelada=0 and pagada=1 and estadoscita_idestados=3 and paciente_dni=?1 and tipocita_idtipocita=1 and receta_idreceta is not null")
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

    @Query(value = "SELECT * FROM telesystem_2.cita WHERE doctor_dni1= ?1 and str_to_date(fecha, '%d-%m-%Y')>= current_date() and citacancelada=0 and estadoscita_idestados in (1,2,4,5)",
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

    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "update cita set examen_medico_idexamen=?1 where idcita =?2")
    void updateExamenCita(Integer examen, Integer idcita);


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
            "    p.dni,\n" +
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

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value="update boletas set receta_idreceta = ?1, monto_receta = ?2 where cita_idcita=?3")
    void updateBoletaReceta (Integer idreceta, Double precio, Integer idCita);


        //Para validar receta
        @Query(nativeQuery = true, value="SELECT\n" +
                "    c.idcita,\n" +
                "    r.idreceta,\n" +
                "    r.comentario,\n" +
                "    rm.observaciones,\n" +
                "    rm.cantidad,\n" +
                "    m.precio,\n" +
                "    m.nombre,\n" +
                "    p.seguros_id_seguro,\n" +
                "    s.nombre_seguro,\n" +
                "    CONCAT(p.nombre, \" \", p.apellido) AS `fullname`,\n" +
                "    p.email,\n" +
                "    ROUND(\n" +
                "    CASE\n" +
                "        WHEN s.nombre_seguro = 'Rimac-EPS' THEN m.precio * rm.cantidad * 0.85\n" +
                "        WHEN s.nombre_seguro = 'Rimac seguros' THEN m.precio * rm.cantidad * 0.75\n" +
                "        WHEN s.nombre_seguro = 'Pacifico EPS' THEN m.precio * rm.cantidad * 0.9\n" +
                "        WHEN s.nombre_seguro = 'Pacifico Seguros' THEN m.precio * rm.cantidad * 0.8\n" +
                "        WHEN s.nombre_seguro = 'Mapfre' THEN m.precio * rm.cantidad * 0.6\n" +
                "        WHEN s.nombre_seguro = 'Plan Salud' THEN m.precio * rm.cantidad * 0.9\n" +
                "        ELSE m.precio * rm.cantidad -- Si no hay seguro o el seguro no está en la lista, no hay descuento\n" +
                "    END,2) AS precio_calculado\n" +
                "FROM cita c \n" +
                "INNER JOIN receta r ON c.receta_idreceta = r.idreceta\n" +
                "LEFT JOIN receta_has_medicamentos rm ON rm.receta_idreceta = r.idreceta\n" +
                "LEFT JOIN medicamentos m ON m.idmedicamentos = rm.medicamentos_idmedicamentos\n" +
                "INNER JOIN usuario p ON p.dni = c.paciente_dni\n" +
                "LEFT JOIN seguros s ON p.seguros_id_seguro = s.id_seguro ")
    List<RecetaValidarDto> listaValidReceta();

}