package com.example.medicaltec.controller;

import com.example.medicaltec.entity.Especialidade;
import com.example.medicaltec.entity.Sede;
import com.example.medicaltec.entity.Usuario;
import com.example.medicaltec.repository.CuestionarioRepository;
import com.example.medicaltec.repository.EspecialidadeRepository;
import com.example.medicaltec.repository.SedeRepository;
import com.example.medicaltec.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ExampController {

    final SedeRepository sedeRepository;
    final CuestionarioRepository cuestionarioRepository;
    final UsuarioRepository usuarioRepository;
    final EspecialidadeRepository especialidadeRepository;


    public ExampController(SedeRepository sedeRepository, CuestionarioRepository cuestionarioRepository, UsuarioRepository usuarioRepository, EspecialidadeRepository especialidadeRepository) {
        this.sedeRepository = sedeRepository;
        this.cuestionarioRepository = cuestionarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.especialidadeRepository = especialidadeRepository;
    }

    @RequestMapping(value = {"/"},method = RequestMethod.GET)
    public String paginaPrincipal(Model model){
        List<Usuario> usuarioList = usuarioRepository.listarDoctores();
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        List<Sede> sedeList = sedeRepository.findAll();
        model.addAttribute("usuarioList",usuarioList);
        model.addAttribute("especialidadesList",especialidadeList);
        model.addAttribute("sedeList",sedeList);
        return "auth/principalpage";
    }

    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(){
        return "auth/register";
    }


}
