package com.example.medicaltec.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    /*final SedeRepository sedeRepository;

    /*public PacienteController(SedeRepository sedeRepository) {
        this.sedeRepository = sedeRepository;
    }*/

    @RequestMapping(value = "/principal")
    public String paginaprincipal(Model model){

       //model.addAttribute("sedes",sedeRepository.findAll());
       return "paciente/principal";
   }

    @RequestMapping("/perfil")
    public String perfilpaciente(Model model){

        return "paciente/perfil";
    }

    @GetMapping("/password")
    public String cambiarContra(Model model){

        return "paciente/cambioContrasena";
    }

    @RequestMapping("/consultas")
    public String citas(){
        return "paciente/consultas";
    }

    @RequestMapping("/notificaciones")
    public String notificaciones(){
       return "paciente/notificaciones";
    }
    @RequestMapping("/mensajeria")
    public String mensajeria(){
       return "paciente/mensajeria";
    }

    @RequestMapping("/pagos")
    public String pagos(){
       return "paciente/pagos";
    }
    @RequestMapping("cuestionarios")
    public String cuestionarios(){
       return "paciente/cuestionarios";
    }

}
