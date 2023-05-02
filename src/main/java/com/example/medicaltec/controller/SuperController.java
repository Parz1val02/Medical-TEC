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

    final SedeRepository sedeRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public SuperController(UsuarioRepository usuarioRepository, FormulariosRegistroRepository formulariosRegistroRepository, ReporteRepository reporteRepository, CuestionarioRepository cuestionarioRepository,
                           PreguntaRepository preguntaRepository,
                           RespuestaRepository respuestaRepository, EstadoRepository estadoRepository,
                           EspecialidadeRepository especialidadeRepository, SedeRepository sedeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.formulariosRegistroRepository = formulariosRegistroRepository;
        this.reporteRepository = reporteRepository;
        this.cuestionarioRepository = cuestionarioRepository;
        this.preguntaRepository = preguntaRepository;
        this.respuestaRepository = respuestaRepository;
        this.estadoRepository = estadoRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.sedeRepository = sedeRepository;
    }

    @GetMapping(value = {"/dashboard"})
    public String dashboard(Model model){
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
        List<Especialidade>listaEspecialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidadList", listaEspecialidades);

        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        model.addAttribute("pacientesList", listaPacientes);
        List<Usuario> listaDoctores = usuarioRepository.obtenerListaDoctores();
        model.addAttribute("doctoresList", listaDoctores);
        List<Usuario> listaAdministrativos = usuarioRepository.obtenerListaAdministrativos();
        model.addAttribute("administrativosList", listaAdministrativos);
        List<Usuario> listaAdministradores = usuarioRepository.obtenerListaAdministradores();
        model.addAttribute("administradoresList", listaAdministradores);
        return "superAdmin/dashboard";
    }

    @GetMapping(value = {"/dashboard/Doctor"})
    public String dashboardDoctor(Model model){
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
        List<Especialidade>listaEspecialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidadList", listaEspecialidades);

        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        model.addAttribute("pacientesList", listaPacientes);
        List<Usuario> listaDoctores = usuarioRepository.obtenerListaDoctores();
        model.addAttribute("doctoresList", listaDoctores);
        List<Usuario> listaAdministrativos = usuarioRepository.obtenerListaAdministrativos();
        model.addAttribute("administrativosList", listaAdministrativos);
        List<Usuario> listaAdministradores = usuarioRepository.obtenerListaAdministradores();
        model.addAttribute("administradoresList", listaAdministradores);
        return "superAdmin/dashboardDoctor";
    }

    @GetMapping(value = {"/dashboard/Paciente"})
    public String dashboardPaciente(Model model){
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
        List<Especialidade>listaEspecialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidadList", listaEspecialidades);

        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        model.addAttribute("pacientesList", listaPacientes);
        List<Usuario> listaDoctores = usuarioRepository.obtenerListaDoctores();
        model.addAttribute("doctoresList", listaDoctores);
        List<Usuario> listaAdministrativos = usuarioRepository.obtenerListaAdministrativos();
        model.addAttribute("administrativosList", listaAdministrativos);
        List<Usuario> listaAdministradores = usuarioRepository.obtenerListaAdministradores();
        model.addAttribute("administradoresList", listaAdministradores);
        return "superAdmin/dashboardPaciente";
    }

    @GetMapping(value = {"/dashboard/AdmT"})
    public String dashboardAdmT(Model model){
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
        List<Especialidade>listaEspecialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidadList", listaEspecialidades);

        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        model.addAttribute("pacientesList", listaPacientes);
        List<Usuario> listaDoctores = usuarioRepository.obtenerListaDoctores();
        model.addAttribute("doctoresList", listaDoctores);
        List<Usuario> listaAdministrativos = usuarioRepository.obtenerListaAdministrativos();
        model.addAttribute("administrativosList", listaAdministrativos);
        List<Usuario> listaAdministradores = usuarioRepository.obtenerListaAdministradores();
        model.addAttribute("administradoresList", listaAdministradores);
        return "superAdmin/dashboardAdmT";
    }

    @GetMapping(value = {"/dashboard/Adm"})
    public String dashboardAdm(Model model){
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
        List<Especialidade>listaEspecialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidadList", listaEspecialidades);

        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        model.addAttribute("pacientesList", listaPacientes);
        List<Usuario> listaDoctores = usuarioRepository.obtenerListaDoctores();
        model.addAttribute("doctoresList", listaDoctores);
        List<Usuario> listaAdministrativos = usuarioRepository.obtenerListaAdministrativos();
        model.addAttribute("administrativosList", listaAdministrativos);
        List<Usuario> listaAdministradores = usuarioRepository.obtenerListaAdministradores();
        model.addAttribute("administradoresList", listaAdministradores);
        return "superAdmin/dashboardAdmSede";
    }
    @PostMapping("/editarPacientes")
    public String editarPaciente(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String id,

            @RequestParam("telefono") String telefono,
            @RequestParam("estado") int estado,
            RedirectAttributes attr

    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Paciente actualizado exitosamente");
        usuarioRepository.editarPaciente( email,  nombre,  telefono, id, sede, estado);
        return "redirect:/superAdmin/dashboard";
    }
    @PostMapping("/editarDoctores")
    public String editarDoctor(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String id,

            @RequestParam("telefono") String telefono,
            @RequestParam("estado") int estado,
            RedirectAttributes attr

    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Doctor actualizado exitosamente");
        usuarioRepository.editarDoctor( email,  nombre,   telefono,   id,  sede, estado );
        return "redirect:/superAdmin/dashboard";
    }
    @PostMapping("/editarAdministrativos")
    public String editarAdministrativo(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String id,

            @RequestParam("telefono") String telefono,
            @RequestParam("estado") int estado,
            RedirectAttributes attr

    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Administrativo actualizado exitosamente");
        usuarioRepository.editarAdministrativo( email,  nombre,  telefono,  id,  sede, estado );
        return "redirect:/superAdmin/dashboard";
    }
    @PostMapping("/editarAdministradores")
    public String editarAdministrador(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String id,

            @RequestParam("telefono") String telefono,
            @RequestParam("estado") int estado,
            RedirectAttributes attr

    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Administrador actualizado exitosamente");
        usuarioRepository.editarAdministradores( email,  nombre,  telefono,  id,  sede, estado );
        return "redirect:/superAdmin/dashboard";
    }
    @RequestMapping(value = {"/forms"},method = RequestMethod.GET)
    public String forms(Model model){
        List<FormulariosRegistro> listaFormularios = formulariosRegistroRepository.findAll();
        model.addAttribute("formularioList", listaFormularios);
        return "superAdmin/forms";
    }

    @RequestMapping(value = {"/Crear/AdmSede"},method = RequestMethod.GET)
    public String crearAdmSEDE(Model model){
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
        List<Especialidade>listaEspecialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidadList", listaEspecialidades);

        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        model.addAttribute("pacientesList", listaPacientes);
        List<Usuario> listaDoctores = usuarioRepository.obtenerListaDoctores();
        model.addAttribute("doctoresList", listaDoctores);
        List<Usuario> listaAdministrativos = usuarioRepository.obtenerListaAdministrativos();
        model.addAttribute("administrativosList", listaAdministrativos);
        List<Usuario> listaAdministradores = usuarioRepository.obtenerListaAdministradores();
        model.addAttribute("administradoresList", listaAdministradores);
        List<Sede> listaSede = sedeRepository.findAll();
        model.addAttribute("listaSede", listaSede);
        return "superAdmin/crearAdmSede";
    }

    @RequestMapping(value = {"/Crear/AdmT"},method = RequestMethod.GET)
    public String crearAdmT(Model model){
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
        List<Especialidade>listaEspecialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidadList", listaEspecialidades);

        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        model.addAttribute("pacientesList", listaPacientes);
        List<Usuario> listaDoctores = usuarioRepository.obtenerListaDoctores();
        model.addAttribute("doctoresList", listaDoctores);
        List<Usuario> listaAdministrativos = usuarioRepository.obtenerListaAdministrativos();
        model.addAttribute("administrativosList", listaAdministrativos);
        List<Usuario> listaAdministradores = usuarioRepository.obtenerListaAdministradores();
        model.addAttribute("administradoresList", listaAdministradores);
        List<Sede> listaSede = sedeRepository.findAll();
        model.addAttribute("listaSede", listaSede);
        return "superAdmin/crearAdmT";
    }

    @PostMapping(value = "/Guardar/AdmSede")
    public String guardarAdmSede(Model model, RedirectAttributes attr,
                                 @RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,
                                 @RequestParam("correo") String correo, @RequestParam("password") String password,
                                 @RequestParam("edad") int edad, @RequestParam("telefono") String telefono,
                                 @RequestParam("address") String address, @RequestParam("sede") int sede,
                                 @RequestParam("dni") String dni, @RequestParam("sexo") String sexo) {
        usuarioRepository.crearAdmSede(dni,password,correo, nombre,apellido,  edad,  telefono,  sexo,  address, sede);
        attr.addFlashAttribute("msg","Administrador de Sede creado exitosamente");
        return "redirect:/superAdmin/dashboard/Adm";
    }

    @PostMapping(value = "/Nuevo/Guardar")
    public String guardarAdmT(Model model, RedirectAttributes attr,
                                       @RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,
                                       @RequestParam("correo") String correo, @RequestParam("password") String password,
                                       @RequestParam("edad") int edad, @RequestParam("telefono") String telefono,
                                       @RequestParam("address") String address, @RequestParam("sede") int sede,
                                        @RequestParam("dni") String dni, @RequestParam("sexo") String sexo) {
        usuarioRepository.crearAdmT( dni,  password, correo, nombre,apellido,  edad,  telefono,  sexo,  address,  sede);
        attr.addFlashAttribute("msg","Administrativo creado exitosamente");
        return "redirect:/superAdmin/dashboard/AdmT";
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