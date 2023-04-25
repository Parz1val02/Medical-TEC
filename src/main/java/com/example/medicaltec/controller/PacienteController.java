package com.example.medicaltec.controller;


import com.example.medicaltec.repository.TipoCitaRepository;
import com.example.medicaltec.entity.Usuario;
import com.example.medicaltec.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    final SedeRepository sedeRepository;
    final SeguroRepository seguroRepository;

    final EspecialidadRepository especialidadRepository;

    final AlergiaRepository alergiaRepository;

    final UsuarioRepository usuarioRepository;

    final RolesRepository rolesRepository;
    private final TipoCitaRepository tipoCitaRepository;


    public PacienteController(SedeRepository sedeRepository, SeguroRepository seguroRepository, EspecialidadRepository especialidadRepository, AlergiaRepository alergiaRepository, UsuarioRepository usuarioRepository, RolesRepository rolesRepository,
                              TipoCitaRepository tipoCitaRepository) {
        this.sedeRepository = sedeRepository;
        this.seguroRepository = seguroRepository;
        this.especialidadRepository = especialidadRepository;
        this.alergiaRepository = alergiaRepository;
        this.usuarioRepository = usuarioRepository;
        this.rolesRepository = rolesRepository;
        this.tipoCitaRepository = tipoCitaRepository;
    }

    @RequestMapping(value = "/principal")
    public String paginaprincipal(Model model){
        Usuario usuario = usuarioRepository.findByid("22647853");
        List<Usuario> doctores = usuarioRepository.obtenerlistaDoctores(usuario.getSedesIdsedes().getId());
        model.addAttribute("usuario", usuario);
        model.addAttribute("doctores", doctores);
        model.addAttribute("sedes", sedeRepository.findAll());
        model.addAttribute("seguros", seguroRepository.findAll());
        model.addAttribute("especialidades", especialidadRepository.findAll());
        model.addAttribute("tipos", tipoCitaRepository.findAll());
        return "paciente/principal";
   }

    @RequestMapping("/perfil")
    public String perfilpaciente(Model model){
        Usuario usuario = usuarioRepository.findByid("22647853");
        model.addAttribute("seguros",seguroRepository.findAll());
        model.addAttribute("alergias",alergiaRepository.findAll());

        return "paciente/perfil";
    }

    @GetMapping("/password")
    public String cambiarContra(Model model){
        Usuario usuario = usuarioRepository.findByid("22647853");
        return "paciente/cambioContrasena";
    }

    @RequestMapping("/consultas")
    public String citas(Model model){
        Usuario usuario = usuarioRepository.findByid("22647853");
        //List<Usuario> listaDoctores = usuarioRepository.obtenerlistaDoctores();
        //model.addAttribute("listaDoc",listaDoctores);
        //model.addAttribute("roles",rolesRepository.findAll());

        //System.out.print(listaDoctores);
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

    @GetMapping("/sede")
    public String cambiarSede(@RequestParam("id")String idSede){
        sedeRepository.cambiarSede(idSede);
        return "redirect:/paciente/principal";
    }
}
