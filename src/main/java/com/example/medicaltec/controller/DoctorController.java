package com.example.medicaltec.controller;

import com.example.medicaltec.entity.Cuestionario;
import com.example.medicaltec.entity.Sede;
import com.example.medicaltec.entity.Usuario;
import com.example.medicaltec.repository.CuestionarioRepository;
import com.example.medicaltec.repository.SedeRepository;
import com.example.medicaltec.repository.UsuarioRepository;
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
    final UsuarioRepository usuarioRepository;

    public DoctorController(SedeRepository sedeRepository, CuestionarioRepository cuestionarioRepository, UsuarioRepository usuarioRepository) {
        this.sedeRepository = sedeRepository;
        this.cuestionarioRepository = cuestionarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @RequestMapping(value = "/principal", method = {RequestMethod.GET, RequestMethod.POST})
    public String pagPrincipalDoctor(@RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     Model model){
        int i=0;
        Usuario user1 = new Usuario();
        List<Usuario> userList = usuarioRepository.findAll();
        for(Usuario u : userList){
            if(u.getEmail().equals(email) && u.getContrasena().equals(password)) {
                i=1;
                user1 = u;
                break;
            }
        }
        if (i==1){
            model.addAttribute("user", user1);
            return "doctor/principal";
        }else {
            return "redirect:/";
        }
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

        List listaPacientes = usuarioRepository.obtenerPacientes();
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
        List<Sede> sedeList = sedeRepository.findAll();
        model.addAttribute("sedeList",sedeList);
        return "doctor/config";
    }




}
