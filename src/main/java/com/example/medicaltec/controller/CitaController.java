package com.example.medicaltec.controller;

import com.example.medicaltec.Entity.*;
import com.example.medicaltec.repository.*;
import org.springframework.stereotype.Controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class CitaController {

    final CitaRepository citaRepository;

    public CitaController(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }
    //5:00pm
    public void cambiarEstadosCitas(){
        //Actualizar estados de las citas
        LocalDate fechaActual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        List<Cita> citas = citaRepository.listarCitas();

        for (Cita cita : citas) {
            LocalDate citaFecha = LocalDate.parse(cita.getFecha());
            LocalTime citaHoras = cita.getHora();

            if (fechaActual == citaFecha){
                Duration diferencia = Duration.between(citaHoras,horaActual); //citasHora - variableHoras

                long horas = diferencia.toHours();
                long minutos = diferencia.toMinutes() % 60;
                long segundos = diferencia.getSeconds() % 60;

                if (horas == 0 && minutos == 10 && segundos == 0){
                    // Estado 4: En espera
                    citaRepository.cambiarEstadoCita(4,cita.getId());
                }else if (horas == 0 && minutos == -30 && segundos == 0){
                    // Estado 3: Culminada
                    citaRepository.cambiarEstadoCita(3,cita.getId());
                }
                // Estado 5: En consulta
                // Se cambia al dar clic en iniciar video

            }
        }
    }


}
