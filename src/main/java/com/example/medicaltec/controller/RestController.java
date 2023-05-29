package com.example.medicaltec.controller;


import com.example.medicaltec.Entity.Cita;
import com.example.medicaltec.Entity.Usuario;
import com.example.medicaltec.repository.CitaRepository;
import com.example.medicaltec.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping(value = "/citas", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    public List<Cita> returnCitas(HttpServletRequest httpServletRequest){
        //Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        Usuario usuario = usuarioRepository.findByid("22647853");
        return citaRepository.historialCitasAgendadas(usuario.getId());
    }
}
