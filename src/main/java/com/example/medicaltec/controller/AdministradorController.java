package com.example.medicaltec.controller;
import com.example.medicaltec.repository.ReporteRepository;
import com.example.medicaltec.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;





@Controller
@RequestMapping("/administrador")
public class AdministradorController {


    /* Repositorys
    - usuarios (paciente y doctor)
    -
     */



    final UsuarioRepository usuarioRepository;
    final ReporteRepository reporteRepository;
    public AdministradorController (UsuarioRepository usuarioRepository, ReporteRepository reporteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.reporteRepository = reporteRepository;
    }



    @GetMapping("/principal")
    public String pagprincipal(){

        return "administrador/principal";
    }

    @GetMapping("/usuarios")
    public String pagusuarios(Model model){
        List listaPacientes = usuarioRepository.obtenerListaPacientes();
        List listaDoctores = usuarioRepository.obtenerlistaDoctores();


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
