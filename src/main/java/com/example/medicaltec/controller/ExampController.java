package com.example.medicaltec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
