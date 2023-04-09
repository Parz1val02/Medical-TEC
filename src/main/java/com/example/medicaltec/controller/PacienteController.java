package com.example.medicaltec.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

   @RequestMapping("/principal")
    public String pagprincipal(){


       return "paciente/paginaprincipal";

   }

    @RequestMapping("/doctor")
    public String perfilpaciente(){


        return "paciente/perfildoctor";

    }

    @RequestMapping("/historialcita")
    public String historial(){


        return "paciente/historialcita";

    }


    @RequestMapping("/agendarcita")
    public String agendarcita(){


        return "paciente/agendarcita";

    }
}
