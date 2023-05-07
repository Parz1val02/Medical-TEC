package com.example.medicaltec.controller;
import com.example.medicaltec.Entity.Especialidade;
import com.example.medicaltec.Entity.Usuario;
import com.example.medicaltec.funciones.GeneradorDeContrasenha;
import com.example.medicaltec.repository.EspecialidadeRepository;
import com.example.medicaltec.repository.ReporteRepository;
import com.example.medicaltec.repository.UsuarioRepository;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/administrador")
public class AdministradorController {


    /* Repositorys
    - usuarios (paciente y doctor)
    -
     */


    final EspecialidadeRepository especialidadeRepository;
    final UsuarioRepository usuarioRepository;
    final ReporteRepository reporteRepository;
    public AdministradorController (UsuarioRepository usuarioRepository, ReporteRepository reporteRepository,EspecialidadeRepository especialidadeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.reporteRepository = reporteRepository;
        this.especialidadeRepository = especialidadeRepository;
    }



    @GetMapping("/principal")
    public String pagprincipal(Model model){
        List listaPacientes = usuarioRepository.obtenerListaPacientes();
        List listaDoctores = usuarioRepository.obtenerlistaDoctores();
        model.addAttribute("listaPacientes",listaPacientes);
        model.addAttribute("listaDoctores",listaDoctores);
        return "administrador/principal";
    }

    @GetMapping("/usuarios")
    public String pagusuarios(Model model, @ModelAttribute("usuario") Usuario usuario){
        List<Especialidade> listaEspecialidades = especialidadeRepository.findAll();
        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        List<Usuario> listaDoctores = usuarioRepository.obtenerlistaDoctores();
        model.addAttribute("listaEspecialidades",listaEspecialidades);
        model.addAttribute("listaPacientes",listaPacientes);
        model.addAttribute("listaDoctores",listaDoctores);
        return "administrador/usuarios";
    }


    /*
    @PostMapping("/editarDoctor")
    public String editarDoctor(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String dni,
            @RequestParam("apellido") String apellido,
            @RequestParam("especialidad") int especialidad,
            @RequestParam("telefono") String telefono,
            RedirectAttributes attr

    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Doctor actualizado exitosamente");
        usuarioRepository.editarDoctor( email,  nombre,  apellido,  telefono,  especialidad,  dni,  sede );
        return "redirect:/administrador/usuarios";
    }*/




    @GetMapping("/editarDoctorPagina")
    public String editarDoctorPagina(Model model, @ModelAttribute("doctor") Usuario doctor, @RequestParam("id") String dni, RedirectAttributes attr){
        Optional<Usuario> optDoctor = usuarioRepository.findById(dni);
        if (optDoctor.isPresent()){
            doctor = optDoctor.get();
            model.addAttribute("doctor",doctor);
            return "administrador/editarDoctor";
        } else {
            attr.addFlashAttribute("msgDanger","El paciente a editar no existe");
            return "redirect:/administrador/usuarios";
        }

    }

    @PostMapping("/editarDoctor")
    public String editarDoctor(
            @ModelAttribute("usuario") Usuario doctor,
            RedirectAttributes attr

    ){
        attr.addFlashAttribute("msg","Doctor actualizado exitosamente");
        usuarioRepository.editarDoctor( doctor.getEmail(),  doctor.getNombre(), doctor.getApellido(),  doctor.getTelefono(),  doctor.getEdad(),  doctor.getId(),  1 );
        return "redirect:/administrador/usuarios";
    }

    /*
    @PostMapping("/crearDoctor")
    public String crearDoctor(
            @RequestParam("sede") int sede,
            @RequestParam("edad") int edad,
            @RequestParam("direccion") String direccion,
            @RequestParam("sexo") String sexo,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String dni,
            @RequestParam("apellido") String apellido,
            @RequestParam("especialidad") int especialidad,
            @RequestParam("telefono") String telefono,
            RedirectAttributes attr

    ){
        List<Usuario> listaDoctores = usuarioRepository.obtenerlistaDoctores();
        System.out.println(nombre);
        boolean existeDoctor = false;
        for (Usuario doctor : listaDoctores) {
            if (dni.equalsIgnoreCase(doctor.getId()) ) {
                existeDoctor = true;
            }
        }


        if ( existeDoctor) {
            attr.addFlashAttribute("msgDanger","El doctor ingresado ya existe");
            return "redirect:/administrador/usuarios";
        } else {
            GeneradorDeContrasenha generadorDeContrasenha=new GeneradorDeContrasenha();
            String contrasena = generadorDeContrasenha.crearPassword();
            usuarioRepository.crearDoctor( email,  nombre,  apellido,  telefono,  especialidad,  dni,  sede, edad, direccion, sexo, contrasena );
            attr.addFlashAttribute("msg","Doctor creado exitosamente");
            return "redirect:/administrador/usuarios";
        }
    }*/
    @PostMapping("/crearDoctor")
    public String crearDoctor(
            @ModelAttribute("usuario") Usuario doctor,
            RedirectAttributes attr

    ){
        List<Usuario> listaDoctores = usuarioRepository.obtenerlistaDoctores();

        boolean existeDoctor = false;
        for (Usuario doctorLista : listaDoctores) {
            if (doctor.getId().equalsIgnoreCase(doctorLista.getId()) ) {
                existeDoctor = true;
            }
        }


        if ( existeDoctor) {
            attr.addFlashAttribute("msgDanger","El doctor ingresado ya existe");
            return "redirect:/administrador/usuarios";
        } else {
            GeneradorDeContrasenha generadorDeContrasenha=new GeneradorDeContrasenha();
            String contrasena = generadorDeContrasenha.crearPassword();
            usuarioRepository.crearDoctor( doctor.getEmail(),  doctor.getNombre(),  doctor.getApellido(),  doctor.getTelefono(),  doctor.getEspecialidadesIdEspecialidad().getId(),  doctor.getId(),  1, doctor.getEdad(), doctor.getDireccion(), doctor.getSexo(), contrasena );
            attr.addFlashAttribute("msg","Doctor creado exitosamente");
            return "redirect:/administrador/usuarios";
        }
    }




    /*
    @PostMapping("/editarPaciente")
    public String editarPaciente(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String dni,
            @RequestParam("apellido") String apellido,
            @RequestParam("telefono") String telefono,
            RedirectAttributes attr

    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Paciente actualizado exitosamente");
        usuarioRepository.editarPaciente( email,  nombre,  apellido,  telefono, dni,  sede );
        return "redirect:/administrador/usuarios";
    }
    */

    @GetMapping("/editarPacientePagina")
    public String editarPacientePagina(Model model, @ModelAttribute("paciente") Usuario paciente, @RequestParam("id") String dni, RedirectAttributes attr){
        Optional<Usuario> optPaciente = usuarioRepository.findById(dni);
        if (optPaciente.isPresent()){
            paciente = optPaciente.get();
            model.addAttribute("paciente",paciente);
            return "administrador/editarPaciente";
        } else {
            attr.addFlashAttribute("msgDanger","El paciente a editar no existe");
            return "redirect:/administrador/usuarios";
        }

    }

    @PostMapping("/editarPaciente")
    public String editarPaciente(
           @ModelAttribute("paciente") Usuario paciente,
            RedirectAttributes attr

    ){
        attr.addFlashAttribute("msg","Paciente actualizado exitosamente");
        usuarioRepository.editarPaciente( paciente.getEmail(),  paciente.getNombre(),  paciente.getApellido(),  paciente.getTelefono(), paciente.getId(), 1 );
        return "redirect:/administrador/usuarios";
    }

    /*
    @PostMapping("/crearPaciente")
    public String crearPaciente(
            @RequestParam("sede") int sede,
            @RequestParam("edad") int edad,
            @RequestParam("direccion") String direccion,
            @RequestParam("sexo") String sexo,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String dni,
            @RequestParam("apellido") String apellido,
            @RequestParam("telefono") String telefono,
            RedirectAttributes attr

    ){
        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        System.out.println(nombre);
        boolean existePaciente = false;
        for (Usuario paciente : listaPacientes) {
            if (dni.equalsIgnoreCase(paciente.getId()) ) {
                existePaciente = true;
            }
        }


        if ( existePaciente) {
            attr.addFlashAttribute("msgDanger","El paciente ingresado ya existe");
            return "redirect:/administrador/usuarios";
        } else {
            GeneradorDeContrasenha generadorDeContrasenha=new GeneradorDeContrasenha();
            String contrasena = generadorDeContrasenha.crearPassword();
            usuarioRepository.crearPaciente( email,  nombre,  apellido,  telefono, dni,  sede, edad, direccion, sexo, contrasena );
            attr.addFlashAttribute("msg","Paciente creado exitosamente");
            return "redirect:/administrador/usuarios";
        }
    }
    */

    @PostMapping("/crearPaciente")
    public String crearPaciente(
            @ModelAttribute("usuario") Usuario paciente,
            RedirectAttributes attr

    ){
        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        boolean existePaciente = false;
        for (Usuario pacienteLista : listaPacientes) {
            if (paciente.getId().equalsIgnoreCase(pacienteLista.getId()) ) {
                existePaciente = true;
            }
        }


        if ( existePaciente) {
            attr.addFlashAttribute("msgDanger","El paciente ingresado ya existe");
            return "redirect:/administrador/usuarios";
        } else {
            GeneradorDeContrasenha generadorDeContrasenha=new GeneradorDeContrasenha();
            String contrasena = generadorDeContrasenha.crearPassword();
            usuarioRepository.crearPaciente( paciente.getEmail(),  paciente.getNombre(),  paciente.getApellido(),  paciente.getTelefono(), paciente.getId(),  paciente.getSedesIdsedes().getId(), paciente.getEdad(), paciente.getDireccion() , paciente.getSexo(), contrasena );
            attr.addFlashAttribute("msg","Paciente creado exitosamente");
            return "redirect:/administrador/usuarios";
        }
    }



    @GetMapping("/calendario")
    public String calendario(){

        return "administrador/calendario";
    }

    @GetMapping("/finanzas")
    public String finanzas(){

        return "administrador/finanzas";
    }

    @GetMapping("/mensajeria")
    public String mensajeria(){

        return "administrador/mensajeria";
    }


    @GetMapping("/formatos")
    public String formatos(){

        return "administrador/formatos";
    }

    @GetMapping("/notificaciones")
    public String notificaciones(){

        return "administrador/notificaciones";
    }

    @GetMapping("/settings")
    public String settings(){

        return "administrador/settings";
    }

    @GetMapping("/password")
    public String changePassword(){

        return "administrador/password";
    }



}
