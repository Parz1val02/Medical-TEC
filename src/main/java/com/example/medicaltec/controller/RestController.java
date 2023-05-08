package com.example.medicaltec.controller;


import com.example.medicaltec.Entity.Cita;
import com.example.medicaltec.Entity.Usuario;
import com.example.medicaltec.repository.CitaRepository;
import com.example.medicaltec.repository.UsuarioRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;


@Controller
public class RestController {

    final UsuarioRepository usuarioRepository;
    final CitaRepository citaRepository;

    public RestController(UsuarioRepository usuarioRepository, CitaRepository citaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.citaRepository = citaRepository;
    }

    @ResponseBody
    @GetMapping(value = "/citas", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
    public List<Cita> returnCitas(Model model){
        Usuario usuario = usuarioRepository.findByid("23456789");

        List<Cita> citas = citaRepository.historialCitasAgendadas(usuario.getId());

        model.addAttribute("arch", "windowzzz");
        return citas;
    }
}
