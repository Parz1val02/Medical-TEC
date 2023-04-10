package com.example.medicaltec.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @RequestMapping("/principal")
    public String pagprincipal(){

        return "administrador/principal";
    }

    @RequestMapping("/usuarios")
    public String pagusuarios(){

        return "administrador/usuarios";
    }

    @RequestMapping("/calendario")
    public String calendario(){

        return "administrador/calendario";
    }

    @RequestMapping("/finanzas")
    public String finanzas(){

        return "administrador/finanzas";
    }

    @RequestMapping("/settings")
    public String settings(){

        return "administrador/settings";
    }



}
