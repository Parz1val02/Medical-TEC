package com.example.medicaltec.controller;

import com.example.medicaltec.entity.*;
import com.example.medicaltec.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/superAdmin")
public class SuperController {

    final UsuarioRepository usuarioRepository;
    final FormulariosRegistroRepository formulariosRegistroRepository;
    final ReporteRepository reporteRepository;
    final CuestionarioRepository cuestionarioRepository;
    final PreguntaRepository preguntaRepository;
    final RespuestaRepository respuestaRepository;
    final EstadoRepository estadoRepository;

    public SuperController(UsuarioRepository usuarioRepository, FormulariosRegistroRepository formulariosRegistroRepository, ReporteRepository reporteRepository, CuestionarioRepository cuestionarioRepository,
                           PreguntaRepository preguntaRepository,
                           RespuestaRepository respuestaRepository, EstadoRepository estadoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.formulariosRegistroRepository = formulariosRegistroRepository;
        this.reporteRepository = reporteRepository;
        this.cuestionarioRepository = cuestionarioRepository;
        this.preguntaRepository = preguntaRepository;
        this.respuestaRepository = respuestaRepository;
        this.estadoRepository = estadoRepository;
    }

    @GetMapping(value = {"/dashboard"})
    public String dashboard(Model model){
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);

        return "superAdmin/dashboard";
    }
    @PostMapping("/editarPaciente")
    public String editarPaciente(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String id,
            @RequestParam("apellido") String apellido,
            @RequestParam("especialidad") int especialidad,
            @RequestParam("telefono") String telefono,
            RedirectAttributes attr

    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Paciente actualizado exitosamente");
        usuarioRepository.editarPaciente( email,  nombre,  apellido,  telefono,  especialidad,  id);
        return "redirect:/superAdmin/dashboard";
    }
    @PostMapping("/editarDoctor")
    public String editarDoctor(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String id,
            @RequestParam("apellido") String apellido,
            @RequestParam("especialidad") int especialidad,
            @RequestParam("telefono") String telefono,
            RedirectAttributes attr

    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Doctor actualizado exitosamente");
        usuarioRepository.editarDoctor( email,  nombre,  apellido,  telefono,  especialidad,  id,  sede );
        return "redirect:/administrador/usuarios";
    }
    @PostMapping("/editarAdministrativo")
    public String editarAdministrativo(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String id,
            @RequestParam("apellido") String apellido,
            @RequestParam("especialidad") int especialidad,
            @RequestParam("telefono") String telefono,
            RedirectAttributes attr

    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Administrativo actualizado exitosamente");
        usuarioRepository.editarAdministrativo( email,  nombre,  apellido,  telefono,  especialidad,  id,  sede );
        return "redirect:/superAdmin/dashboard";
    }
    @PostMapping("/editarAdministrador")
    public String editarAdministrador(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String id,
            @RequestParam("apellido") String apellido,
            @RequestParam("especialidad") int especialidad,
            @RequestParam("telefono") String telefono,
            RedirectAttributes attr

    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Administrador actualizado exitosamente");
        usuarioRepository.editarAdministrador( email,  nombre,  apellido,  telefono,  especialidad,  id,  sede );
        return "redirect:/superAdmin/dashboard";
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
    /*/@GetMapping("/cuestionarios/delete")
    public String borrarCuestionarioLleno(Model model,
                                          @RequestParam("id") int id,
                                          RedirectAttributes attr) {

        Optional<Cuestionario> optionalCuestionario = cuestionarioRepository.findById(id);
        Optional<Pregunta> optionalPregunta = preguntaRepository.findById(id);
        Optional<Respuesta> optionalRespuesta = respuestaRepository.findById(id);

        if (optionalCuestionario.isPresent()) {
            cuestionarioRepository.deleteById(id);
            preguntaRepository.deleteById(id);
            respuestaRepository.deleteById(id);
        }
        return "redirect:/superAdmin/cuestionarios";
    }
    /*/
    @RequestMapping(value = {"/editarPerfil"},method = RequestMethod.GET)
    public String editarPerfil(){
        return "superAdmin/editarPerfil";
    }

}