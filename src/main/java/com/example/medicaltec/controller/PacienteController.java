package com.example.medicaltec.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

   @RequestMapping(value = {"", "/principal"})
    public String paginaprincipal(){
       return "paciente/principal";
   }

    @RequestMapping("/perfil")
    public String perfilpaciente(){
        return "paciente/perfil";
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
