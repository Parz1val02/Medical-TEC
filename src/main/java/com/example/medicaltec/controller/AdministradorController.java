package com.example.medicaltec.controller;
import com.example.medicaltec.Entity.Especialidade;
import com.example.medicaltec.Entity.Usuario;
import com.example.medicaltec.funciones.GeneradorDeContrasenha;
import com.example.medicaltec.repository.EspecialidadeRepository;
import com.example.medicaltec.repository.ReporteRepository;
import com.example.medicaltec.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;





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
    public String pagusuarios(Model model){
        List<Especialidade> listaEspecialidades = especialidadeRepository.findAll();
        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        List<Usuario> listaDoctores = usuarioRepository.obtenerlistaDoctores();
        model.addAttribute("listaEspecialidades",listaEspecialidades);
        model.addAttribute("listaPacientes",listaPacientes);
        model.addAttribute("listaDoctores",listaDoctores);
        return "administrador/usuarios";
    }

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
    }

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
    }

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
