package com.example.medicaltec.controller;

import com.example.medicaltec.entity.Cuestionario;
import com.example.medicaltec.entity.Sede;
import com.example.medicaltec.entity.Usuario;
import com.example.medicaltec.repository.CuestionarioRepository;
import com.example.medicaltec.repository.SedeRepository;
import com.example.medicaltec.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    final SedeRepository sedeRepository;
    final CuestionarioRepository cuestionarioRepository;
    final UsuarioRepository usuarioRepository;

    public DoctorController(SedeRepository sedeRepository, CuestionarioRepository cuestionarioRepository, UsuarioRepository usuarioRepository) {
        this.sedeRepository = sedeRepository;
        this.cuestionarioRepository = cuestionarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @RequestMapping(value = "/principal", method = {RequestMethod.GET,RequestMethod.POST})
    public String pagPrincipalDoctor(Model model){

        List<Usuario> userPacienteList = usuarioRepository.listarPacientes();
        model.addAttribute("listaPacientes",userPacienteList);
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
    public String verPacientes(Model model){
        List<Usuario> listaPacientes = usuarioRepository.listarPacientes();
        model.addAttribute("pacientes", listaPacientes);
        return "doctor/pacientes";
    }

    @GetMapping("/cuestionarios")
    public String verCuestionarios(Model model){
        List<Cuestionario> cuestionariosList = cuestionarioRepository.findAll();
        model.addAttribute("cuestionariosList",cuestionariosList);
        return "doctor/cuestionarios";
    }

    @GetMapping("/boletas")
    public String verBoletas(){return "doctor/boletas";}

    @GetMapping("/config")
    public String verConfiguracion(Model model){
        List<Sede> sedeList = sedeRepository.sedesMenosActual();
        model.addAttribute("sedeList",sedeList);
        return "doctor/config";
    }

    @PostMapping("/cambiarSede")
    public String cambiarSede(Model model,
                              @RequestParam("id") int id){
        Optional<Sede> optionalSede = sedeRepository.findById(id);

        if (optionalSede.isPresent()) {
            usuarioRepository.actualizarSede(3,id);
        }
        return "redirect:/doctor/config";
    }




}
