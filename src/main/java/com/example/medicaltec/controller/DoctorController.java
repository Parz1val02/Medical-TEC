package com.example.medicaltec.controller;

import com.example.medicaltec.entity.Alergia;
import com.example.medicaltec.entity.Cuestionario;
import com.example.medicaltec.entity.Sede;
import com.example.medicaltec.entity.Usuario;
import com.example.medicaltec.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    final SedeRepository sedeRepository;
    final CuestionarioRepository cuestionarioRepository;
    final UsuarioRepository usuarioRepository;
    final MensajeRepository mensajeRepository;
    final NotificacioneRepository notificacioneRepository;
    final CitaRepository citaRepository;
    final HistorialMedicoHasAlergiaRepository2 historialMedicoHasAlergiaRepository2;
    final AlergiaRepository alergiaRepository;

    public DoctorController(SedeRepository sedeRepository, CuestionarioRepository cuestionarioRepository, UsuarioRepository usuarioRepository, MensajeRepository mensajeRepository, NotificacioneRepository notificacioneRepository, CitaRepository citaRepository, HistorialMedicoHasAlergiaRepository2 historialMedicoHasAlergiaRepository2, AlergiaRepository alergiaRepository) {
        this.sedeRepository = sedeRepository;
        this.cuestionarioRepository = cuestionarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.mensajeRepository = mensajeRepository;
        this.notificacioneRepository = notificacioneRepository;
        this.citaRepository = citaRepository;
        this.historialMedicoHasAlergiaRepository2 = historialMedicoHasAlergiaRepository2;
        this.alergiaRepository = alergiaRepository;
    }

    @RequestMapping(value = "/principal", method = {RequestMethod.GET,RequestMethod.POST})
    public String pagPrincipalDoctor(Model model){

        model.addAttribute("listaPacientes",usuarioRepository.listarPacientes());
        model.addAttribute("listaMensajes",mensajeRepository.listarMensajesMasActuales());
        model.addAttribute("listaNotificaciones",notificacioneRepository.listarNotificacionesMasActuales());
        model.addAttribute("listaProximasCitas",citaRepository.proximasCitasAgendadas());
        return "doctor/principal";
    }


    @GetMapping("/historial")
    public String verHistorial(Model model, @RequestParam("id") String id){
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        Usuario usuario = optionalUsuario.get();
        model.addAttribute("paciente",usuario);

        List<Integer> idAlergias = historialMedicoHasAlergiaRepository2.listarAlergiasPorId(usuario.getHistorialmedicoIdhistorialmedico().getId());
        ArrayList<Alergia> listaAlergias = new ArrayList<>();
        for(int i=0; i<idAlergias.size(); i++){
            listaAlergias.add(alergiaRepository.obtenerAlergia(idAlergias.get(i)));
        }
        model.addAttribute("listaAlergias",listaAlergias);
        model.addAttribute("listaCitasPorUsuario",citaRepository.citasPorUsuario(id));
        return "doctor/historial";
    }

    @GetMapping("/notificaciones")
    public String verNotificaciones(){return "doctor/notificaciones";}

    @GetMapping("/calendario")
    public String verCalendario(){return "doctor/calendario";}

    @GetMapping("/mensajeria")
    public String verMensajes(){return "doctor/mensajeria";}

    @GetMapping("/pacientes")
    public String verPacientes(Model model){
        model.addAttribute("listaCitas",citaRepository.pacientesAtendidos());
        return "doctor/pacientes";
    }

    @GetMapping("/cuestionarios")
    public String verCuestionarios(Model model){
        List<Cuestionario> cuestionariosList = cuestionarioRepository.findAll();
        model.addAttribute("cuestionariosList",cuestionariosList);
        return "doctor/cuestionarios";
    }

    @GetMapping("/boletas")
    public String verBoletas(Model model){
        model.addAttribute("listaCitas",citaRepository.pacientesAtendidos());
        return "doctor/boletas";
    }

    @GetMapping("/config")
    public String verConfiguracion(Model model){
        List<Sede> sedeList = sedeRepository.sedesMenosActual();
        model.addAttribute("sedeList",sedeList);
        return "doctor/config";
    }

    @PostMapping("/cambiarSede")
    public String cambiarSede(Model model,
                              @RequestParam("id") int id){
        Optional<Sede> optionalSede = sedeRepository.findById(id);

        if (optionalSede.isPresent()) {
            usuarioRepository.actualizarSede(3,id);
        }
        return "redirect:/doctor/config";
    }




}
