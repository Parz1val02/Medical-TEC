package com.example.medicaltec.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @GetMapping("/principal")
    public String pagprincipal(){

        return "administrador/principal";
    }

    @GetMapping("/usuarios")
    public String pagusuarios(){

        return "administrador/usuarios";
    }

    @GetMapping("/calendario")
    public String calendario(){

        return "administrador/calendario";
    }

    @GetMapping("/finanzas")
    public String finanzas(){

        return "administrador/finanzas";
    }

    @GetMapping("/mensajeria")
    public String mensajeria(){

        return "administrador/mensajeria";
    }


    @GetMapping("/formatos")
    public String formatos(){

        return "administrador/formatos";
    }

    @GetMapping("/notificaciones")
    public String notificaciones(){

        return "administrador/notificaciones";
    }

    @GetMapping("/settings")
    public String settings(){

        return "administrador/settings";
    }



}
