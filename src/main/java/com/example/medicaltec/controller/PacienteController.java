package com.example.medicaltec.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

   @RequestMapping(value = {"/", "/principal"})
    public String paginaprincipal(){
       return "paciente/principal";
   }

    @RequestMapping("/perfil")
    public String perfilpaciente(){


        return "paciente/perfil";

    }

    @RequestMapping("/citas")
    public String citas(){


        return "paciente/citas";

    }
}
