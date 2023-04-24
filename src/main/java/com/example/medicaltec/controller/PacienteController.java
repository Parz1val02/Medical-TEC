package com.example.medicaltec.controller;


import com.example.medicaltec.repository.EspecialidadRepository;
import com.example.medicaltec.repository.SedeRepository;
import com.example.medicaltec.repository.SeguroRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    final SedeRepository sedeRepository;
    final SeguroRepository seguroRepository;

    final EspecialidadRepository especialidadRepository;

    public PacienteController(SedeRepository sedeRepository, SeguroRepository seguroRepository, EspecialidadRepository especialidadRepository) {
        this.sedeRepository = sedeRepository;
        this.seguroRepository = seguroRepository;
        this.especialidadRepository = especialidadRepository;
    }

    @RequestMapping(value = "/principal")
    public String paginaprincipal(Model model){

       model.addAttribute("sedes",sedeRepository.findAll());
       return "paciente/principal";
   }

   @GetMapping("/")
   public String mandarDatos(Model model){


        model.addAttribute("sedes", sedeRepository.findAll());
       model.addAttribute("seguros", seguroRepository.findAll());
       model.addAttribute("especialidades", especialidadRepository.findAll());


        return "fragmentos/topbarpaciente";
   }

    @RequestMapping("/perfil")
    public String perfilpaciente(Model model){

        model.addAttribute("seguros",seguroRepository.findAll());
        return "paciente/perfil";
    }

    @GetMapping("/password")
    public String cambiarContra(Model model){

        return "paciente/cambioContrasena";
    }

    @RequestMapping("/consultas")
    public String citas(){
        return "paciente/consultas";
    }

    @RequestMapping("/notificaciones")
    public String notificaciones(){
       return "paciente/notificaciones";
    }
    @RequestMapping("/mensajeria")
    public String mensajeria(){
       return "paciente/mensajeria";
    }

    @RequestMapping("/pagos")
    public String pagos(){
       return "paciente/pagos";
    }
    @RequestMapping("cuestionarios")
    public String cuestionarios(){
       return "paciente/cuestionarios";
    }

}
