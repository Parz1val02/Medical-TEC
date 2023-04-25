package com.example.medicaltec.controller;

import com.example.medicaltec.entity.Usuario;
import com.example.medicaltec.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RequestMapping("/administrativo")
@Controller
public class AdministrativoController {

    UsuarioRepository usuarioRepository;

    public AdministrativoController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }





    @RequestMapping(value = {"/dashboard", ""},method = RequestMethod.GET)
    public String dashboard(Model model){
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        model.addAttribute("listaUsuarios",listaUsuarios);
        return "administrativo/dashboard";
    }

    @RequestMapping(value = {"/perfil"},method = RequestMethod.GET)
    public String VerPerfil(){

        return "administrativo/Perfil";
    }
    @RequestMapping(value = {"/pass"},method = RequestMethod.GET)
    public String CambiarPassword(){

        return "administrativo/CambiarPassword";
    }

    @RequestMapping(value = {"/form"},method = RequestMethod.GET)
    public String previewForm(){

        return "administrativo/clash";
    }

    @RequestMapping(value = {"/mensajeria"},method = RequestMethod.GET)
    public String mensajeria(){

        return "administrativo/mensajeria";
    }

    @RequestMapping(value = {"/notificaciones"},method = RequestMethod.GET)
    public String notificaciones(){

        return "administrativo/notificaciones";
    }

}
