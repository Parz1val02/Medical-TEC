package com.example.medicaltec.controller;

import com.example.medicaltec.entity.Cuestionario;
import com.example.medicaltec.entity.FormulariosRegistro;
import com.example.medicaltec.entity.Reporte;
import com.example.medicaltec.entity.Usuario;
import com.example.medicaltec.repository.CuestionarioRepository;
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
    final CuestionarioRepository cuestionarioRepository;
    public SuperController(UsuarioRepository usuarioRepository, FormulariosRegistroRepository formulariosRegistroRepository, ReporteRepository reporteRepository, CuestionarioRepository cuestionarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.formulariosRegistroRepository = formulariosRegistroRepository;
        this.reporteRepository = reporteRepository;
        this.cuestionarioRepository = cuestionarioRepository;
    }

    @GetMapping(value = {"/dashboard"})
    public String dashboard(Model model){
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
        return "superAdmin/dashboard";
    }
    @RequestMapping(value = {"/forms"},method = RequestMethod.GET)
    public String forms(Model model){
        List<FormulariosRegistro> listaFormularios = formulariosRegistroRepository.findAll();
        model.addAttribute("formularioList", listaFormularios);
        return "superAdmin/forms";
    }
    @GetMapping("/forms/delete")
    public String borrarFormulario(Model model,
                                   @RequestParam("id") int id,
                                   RedirectAttributes attr) {

        Optional<FormulariosRegistro> optionalFormulariosRegistro = formulariosRegistroRepository.findById(id);

        if (optionalFormulariosRegistro.isPresent()) {
            formulariosRegistroRepository.deleteById(id);
        }
        return "redirect:/superAdmin/forms";
    }
    @RequestMapping(value = {"/reports"},method = RequestMethod.GET)
    public String reports(Model model){
        List<Reporte> listaReportes = reporteRepository.findAll();
        model.addAttribute("reporteList", listaReportes);
        return "superAdmin/reports";
    }
    @GetMapping("/reports/delete")
    public String borrarReporte(Model model,
                                   @RequestParam("id") int id,
                                   RedirectAttributes attr) {

        Optional<Reporte> optionalReporte = reporteRepository.findById(id);

        if (optionalReporte.isPresent()) {
            reporteRepository.deleteById(id);
        }
        return "redirect:/superAdmin/reports";
    }
    @RequestMapping(value = {"/confSup"},method = RequestMethod.GET)
    public String confSup(){
        return "superAdmin/confSup";
    }
    @RequestMapping(value = {"/superPass"},method = RequestMethod.GET)
    public String superPass(){
        return "superAdmin/superPass";
    }
    @RequestMapping(value = {"/formulario"},method = RequestMethod.GET)
    public String formulario(){
        return "superAdmin/formulario";
    }

    @RequestMapping(value = {"/reporte"},method = RequestMethod.GET)
    public String reporte(){
        return "superAdmin/reporte";
    }
    @RequestMapping(value = {"/cuestionario"},method = RequestMethod.GET)
    public String cuestionario(){
        return "superAdmin/cuestionario";
    }
    @RequestMapping(value = {"/cuestionarios"},method = RequestMethod.GET)
    public String cuestionarios(Model model){
        List<Cuestionario> listaCuestionarios = cuestionarioRepository.findAll();
        model.addAttribute("cuestionarioList", listaCuestionarios);
        return "superAdmin/cuestionarios";
    }
    @GetMapping("/cuestionarios/delete")
    public String borrarCuestionarioLleno(Model model,
                                          @RequestParam("id") int id,
                                          RedirectAttributes attr) {

        Optional<Cuestionario> optionalCuestionario = cuestionarioRepository.findById(id);

        if (optionalCuestionario.isPresent()) {
            cuestionarioRepository.deleteById(id);
        }
        return "redirect:/superAdmin/cuestionarios";
    }
    @RequestMapping(value = {"/editarPerfil"},method = RequestMethod.GET)
    public String editarPerfil(){
        return "superAdmin/editarPerfil";
    }

}