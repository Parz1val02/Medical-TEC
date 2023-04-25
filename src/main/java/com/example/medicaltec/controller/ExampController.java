package com.example.medicaltec.controller;

import com.example.medicaltec.entity.Usuario;
import com.example.medicaltec.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ExampController {

    @RequestMapping(value = {"/"},method = RequestMethod.GET)
    public String login(){


        return "auth/login";
    }

    @RequestMapping(value = {"/dashboard"},method = RequestMethod.GET)
    public String dashboard(){
        return "super admin/dashboard";
    }
}
