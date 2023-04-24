package com.example.medicaltec.controller;

import com.example.medicaltec.entity.Cuestionarios;
import com.example.medicaltec.entity.Sede;
import com.example.medicaltec.repository.CuestionarioRepository;
import com.example.medicaltec.repository.SedeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    final SedeRepository sedeRepository;
    final CuestionarioRepository cuestionarioRepository;

    public DoctorController(SedeRepository sedeRepository, CuestionarioRepository cuestionarioRepository) {
        this.sedeRepository = sedeRepository;
        this.cuestionarioRepository = cuestionarioRepository;
    }

    @RequestMapping(value = "/principal", method = {RequestMethod.GET, RequestMethod.POST})
    public String pagPrincipalDoctor(@RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     Model model){

        return "doctor/principal";
    }

    @GetMapping("/historial")
    public String verHistorial(){return "doctor/historial";}

    @GetMapping("/notificaciones")
    public String verNotificaciones(){return "doctor/notificaciones";}

    @GetMapping("/calendario")
    public String verCalendario(){return "doctor/calendario";}

    @GetMapping("/mensajeria")
    public String verMensajes(){return "doctor/mensajeria";}

    @GetMapping("/pacientes")
    public String verPacientes(){return "doctor/pacientes";}

    @GetMapping("/cuestionarios")
    public String verCuestionarios(Model model){
        List<Cuestionarios> cuestionariosList = cuestionarioRepository.findAll();
        model.addAttribute("cuestionariosList",cuestionariosList);
        return "doctor/cuestionarios";
    }

    @GetMapping("/boletas")
    public String verBoletas(){return "doctor/boletas";}

    @GetMapping("/config")
    public String verConfiguracion(Model model){
        List<Sede> sedeList = sedeRepository.findAll();
        model.addAttribute("sedeList",sedeList);
        return "doctor/config";
    }




}
