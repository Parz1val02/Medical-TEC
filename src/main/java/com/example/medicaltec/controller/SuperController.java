package com.example.medicaltec.controller;

import com.example.medicaltec.entity.FormulariosRegistro;
import com.example.medicaltec.entity.Usuario;
import com.example.medicaltec.repository.FormulariosRegistroRepository;
import com.example.medicaltec.repository.ReporteRepository;
import com.example.medicaltec.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/superAdmin")
public class SuperController {

    final UsuarioRepository usuarioRepository;
    final FormulariosRegistroRepository formulariosRegistroRepository;
    final ReporteRepository reporteRepository;
    public SuperController(UsuarioRepository usuarioRepository, FormulariosRegistroRepository formulariosRegistroRepository, ReporteRepository reporteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.formulariosRegistroRepository = formulariosRegistroRepository;
        this.reporteRepository = reporteRepository;
    }

    @GetMapping(value = {"/dashboard"})
    public String dashboard(Model model){
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
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
    @RequestMapping(value = {"/confSup"},method = RequestMethod.GET)
    public String confSup(){
        return "super admin/confSup";
    }
    @RequestMapping(value = {"/superPass"},method = RequestMethod.GET)
    public String superPass(){
        return "super admin/superPass";
    }
    @RequestMapping(value = {"/formulario"},method = RequestMethod.GET)
    public String formulario(){
        return "super admin/formulario";
    }

    @GetMapping("/delete")
    public String borrarFormulario(Model model,
                                      @RequestParam("id") int id,
                                      RedirectAttributes attr) {

        Optional<FormulariosRegistro> optionalFormulariosRegistro = formulariosRegistroRepository.findById(id);

        if (optionalFormulariosRegistro.isPresent()) {
            formulariosRegistroRepository.deleteById(id);
        }
        return "redirect:/super admin/forms";
    }

    @RequestMapping(value = {"/reporte"},method = RequestMethod.GET)
    public String reporte(){
        return "super admin/reporte";
    }
    @RequestMapping(value = {"/cuestionario"},method = RequestMethod.GET)
    public String cuestionario(){
        return "super admin/cuestionario";
    }
    @RequestMapping(value = {"/cuestionarios"},method = RequestMethod.GET)
    public String cuestionarios(){
        return "super admin/cuestionarios";
    }
    @RequestMapping(value = {"/editarPerfil"},method = RequestMethod.GET)
    public String editarPerfil(){
        return "super admin/editarPerfil";
    }

}