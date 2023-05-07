package com.example.medicaltec.controller;


import com.example.medicaltec.Entity.Cita;
import com.example.medicaltec.Entity.Usuario;
import com.example.medicaltec.repository.CitaRepository;
import com.example.medicaltec.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

public class RestController {

    final UsuarioRepository usuarioRepository;
    final CitaRepository citaRepository;

    public RestController(UsuarioRepository usuarioRepository, CitaRepository citaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.citaRepository = citaRepository;
    }

    @GetMapping(value = "/citas")
    public String returnCitas(Model model){
        Usuario usuario = usuarioRepository.findByid("22647853");

        List<Cita> citas = citaRepository.historialCitasAgendadas(usuario.getId());

        model.addAttribute("arch", "windowzzz");
        return "";
    }
}
