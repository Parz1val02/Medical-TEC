package com.example.medicaltec.controller;


import com.example.medicaltec.Entity.Cita;
import com.example.medicaltec.Entity.Medicamento;
import com.example.medicaltec.Entity.Usuario;
import com.example.medicaltec.repository.CitaRepository;
import com.example.medicaltec.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    final UsuarioRepository usuarioRepository;
    final CitaRepository citaRepository;

    public RestController(UsuarioRepository usuarioRepository, CitaRepository citaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.citaRepository = citaRepository;
    }

    //@GetMapping(value = "/citas", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    //public List<Cita> returnCitas(HttpServletRequest httpServletRequest){
    //    Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
    //    //Usuario usuario = usuarioRepository.findByid("22647853");
    //    return citaRepository.historialCitasAgendadas(usuario.getId());
    //}


    /*@GetMapping(value = "/apiparamdicamentos", produces = )
    public List<Medicamento> recetaxmed(HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){


        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());

        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");

        ArrayList<Medicamento> medicamentos = new ArrayList<>();
        List<Cita> citas = citaRepository.historialCitas2(usuario.getId());
    }*/
}
