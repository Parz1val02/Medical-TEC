package com.example.medicaltec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @GetMapping("/principal")
    public String pagPrincipalDoctor(){return "doctor/principal";}

    @GetMapping("/historial")
    public String verHistorialClinico(){return "doctor/historial";}

    @GetMapping("/notificaciones")
    public String verNotificaciones(){return "doctor/notificaciones";}

    @GetMapping("/calendario")
    public String verCalendario(){return "doctor/calendario";}

    @GetMapping("/mensajeria")
    public String verMensajes(){return "doctor/mensajeria";}

    @GetMapping("/pacientes")
    public String verPacientes(){return "doctor/pacientes";}

    @GetMapping("/cuestionarios")
    public String verCuestionarios(){return "doctor/cuestionarios";}

    @GetMapping("/boletas")
    public String verBoletas(){return "doctor/boletas";}

    @GetMapping("/config")
    public String verConfiguracion(){return "doctor/config";}


}
