package com.example.medicaltec.controller;


import com.example.medicaltec.repository.TipoCitaRepository;
import com.example.medicaltec.Entity.Usuario;
import com.example.medicaltec.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
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
    final TipoCitaRepository tipoCitaRepository;
    final CitaRepository citaRepository;

    final MedicamentoRepository medicamentoRepository;

    public PacienteController(SedeRepository sedeRepository, SeguroRepository seguroRepository, EspecialidadRepository especialidadRepository, AlergiaRepository alergiaRepository, UsuarioRepository usuarioRepository, RolesRepository rolesRepository,
                              TipoCitaRepository tipoCitaRepository, CitaRepository citaRepository, MedicamentoRepository medicamentoRepository) {
        this.sedeRepository = sedeRepository;
        this.seguroRepository = seguroRepository;
        this.especialidadRepository = especialidadRepository;
        this.alergiaRepository = alergiaRepository;
        this.usuarioRepository = usuarioRepository;
        this.rolesRepository = rolesRepository;
        this.tipoCitaRepository = tipoCitaRepository;
        this.citaRepository = citaRepository;
        this.medicamentoRepository = medicamentoRepository;
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
        model.addAttribute("arch", "arch");
        return "paciente/principal";
   }

    @RequestMapping("/perfil")
    public String perfilpaciente(Model model){
        Usuario usuario = usuarioRepository.findByid("22647853");
        model.addAttribute("alergias",alergiaRepository.findAll());

        List<Usuario> doctores = usuarioRepository.obtenerlistaDoctores(usuario.getSedesIdsedes().getId());
        model.addAttribute("usuario", usuario);
        model.addAttribute("doctores", doctores);
        model.addAttribute("sedes", sedeRepository.findAll());
        model.addAttribute("seguros", seguroRepository.findAll());
        model.addAttribute("especialidades", especialidadRepository.findAll());
        model.addAttribute("tipos", tipoCitaRepository.findAll());
        model.addAttribute("arch", "windowzzz");
        return "paciente/perfil";
    }

    @GetMapping("/password")
    public String cambiarContra(Model model){
        model.addAttribute("arch", "windowzzz");
        return "paciente/cambioContrasena";
    }

    @RequestMapping("/consultas")
    public String citas(Model model){
        Usuario usuario = usuarioRepository.findByid("22647853");
        List<Usuario> doctores = usuarioRepository.obtenerlistaDoctores(usuario.getSedesIdsedes().getId());
        model.addAttribute("usuario", usuario);
        model.addAttribute("doctores", doctores);
        model.addAttribute("sedes", sedeRepository.findAll());
        model.addAttribute("seguros", seguroRepository.findAll());
        model.addAttribute("especialidades", especialidadRepository.findAll());
        model.addAttribute("tipos", tipoCitaRepository.findAll());
        model.addAttribute("medicamentos", medicamentoRepository.findAll());
        //List<Usuario> listaDoctores = usuarioRepository.obtenerlistaDoctores();
        //model.addAttribute("listaDoc",listaDoctores);
        //model.addAttribute("roles",rolesRepository.findAll());

        //System.out.print(listaDoctores);
        model.addAttribute("arch", "windowzzz");
        return "paciente/consultas";
    }

    @RequestMapping("/notificaciones")
    public String notificaciones(Model model){
        model.addAttribute("arch", "windowzzz");
       return "paciente/notificaciones";
    }
    @RequestMapping("/mensajeria")
    public String mensajeria(Model model){
        model.addAttribute("arch", "windowzzz");
       return "paciente/mensajeria";
    }

    @RequestMapping("/pagos")
    public String pagos(Model model){
        model.addAttribute("arch", "windowzzz");
       return "paciente/pagos";
    }
    @RequestMapping("/cuestionarios")
    public String cuestionarios(Model model){
        model.addAttribute("arch", "windowzzz");
       return "paciente/cuestionarios";
    }

    @GetMapping("/sede")
    public String cambiarSede(@RequestParam("id")String idSede){
        sedeRepository.cambiarSede(idSede);
        return "redirect:/paciente/principal";
    }
    @PostMapping("/guardarCita")
    public String guardarCita(@RequestParam("id") String dni,
                              @RequestParam("especialidad") String idEspecialidad,
                              @RequestParam("tipocita") String idTipoCita,
                              @RequestParam("sede") String idSede,
                              @RequestParam("doctor") String idDoctor,
                              @RequestParam("seguro") String idSeguro,
                              @RequestParam("modalidad") String modalidad,
                              @RequestParam("hora")LocalTime hora,
                              @RequestParam("fecha")LocalDate fecha,
                              RedirectAttributes attr){
        String formaPago;
        if(modalidad.equals("Presencial")){
            formaPago = "En caja";
        }else{
            formaPago = "Virtual";
        }
        citaRepository.guardarCita(idSede, idEspecialidad, formaPago, modalidad, idTipoCita, fecha, hora, dni, idDoctor);
        attr.addFlashAttribute("msg", "Cita agendada de manera exitosa");
        return "redirect:/paciente/principal";
    }


    @PostMapping("/guardarAlergias")
    public String guardarAlergias(@RequestParam("nombre") String nombre, RedirectAttributes attr){


        alergiaRepository.guardarAlergias(nombre);
        attr.addFlashAttribute("msg", "se agregó exitosamente");
        return "redirect:/paciente/perfil";
    }
}