package com.example.medicaltec.controller;

import com.example.medicaltec.Entity.Usuario;
import com.example.medicaltec.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/rbr")
    public String redirectByRole(Authentication authentication, HttpSession httpSession){
        String rol = "";
        for (GrantedAuthority role : authentication.getAuthorities()) {
            rol = role.getAuthority();
            break;
        }

        Usuario usuario = usuarioRepository.findByEmail(authentication.getName());
        System.out.println(usuario.getNombre() + " " + usuario.getApellido() + " " + usuario.getTelefono());
        httpSession.setAttribute("usuario", usuario);

        switch (rol) {
            case "paciente" -> {
                return "redirect:/paciente/principal";
            }
            case "administrativo" -> {
                return "redirect:/administrativo/dashboard";
            }
            case "administrador" -> {
                return "redirect:/administrador/principal";
            }
            case "doctor" -> {
                return "redirect:/doctor/principal";
            }
            case "superadmin" -> {
                return "redirect:/superAdmin/dashboard";
            }
            default -> {
                return "redirect:/paciente/principal";
            }
        }
    }
}
