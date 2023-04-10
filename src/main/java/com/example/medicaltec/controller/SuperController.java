package com.example.medicaltec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/superAdmin")
public class SuperController {

    @RequestMapping(value = {"/dashboard"},method = RequestMethod.GET)
    public String dashboard(){
        return "super admin/dashboard";
    }
    @RequestMapping(value = {"/forms"},method = RequestMethod.GET)
    public String forms(){
        return "super admin/forms";
    }
    @RequestMapping(value = {"/reports"},method = RequestMethod.GET)
    public String reports(){
        return "super admin/reports";
    }

}