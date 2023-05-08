package com.example.medicaltec.controller;


import com.example.medicaltec.Entity.Cita;
import com.example.medicaltec.Entity.Usuario;
import com.example.medicaltec.repository.CitaRepository;
import com.example.medicaltec.repository.UsuarioRepository;
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

    @GetMapping(value = "/citas")
    public Usuario returnCitas(Model model){
        Usuario usuario = usuarioRepository.findByid("22647853");
        List<Cita> citas = citaRepository.historialCitasAgendadas(usuario.getId());
        return usuario;
    }
}
