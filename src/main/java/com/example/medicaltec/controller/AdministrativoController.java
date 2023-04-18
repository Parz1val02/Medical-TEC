package com.example.medicaltec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/administrativo")
@Controller
public class AdministrativoController {
    @RequestMapping(value = {"/dashboard", ""},method = RequestMethod.GET)
    public String dashboard(){
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

}
