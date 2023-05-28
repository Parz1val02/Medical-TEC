package com.example.medicaltec.controller;

import com.example.medicaltec.Entity.*;
import com.example.medicaltec.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    final InformeRepository informeRepository;

    public DoctorController(SedeRepository sedeRepository, CuestionarioRepository cuestionarioRepository, UsuarioRepository usuarioRepository, MensajeRepository mensajeRepository, NotificacioneRepository notificacioneRepository, CitaRepository citaRepository, HistorialMedicoHasAlergiaRepository2 historialMedicoHasAlergiaRepository2, AlergiaRepository alergiaRepository, InformeRepository informeRepository) {
        this.sedeRepository = sedeRepository;
        this.cuestionarioRepository = cuestionarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.mensajeRepository = mensajeRepository;
        this.notificacioneRepository = notificacioneRepository;
        this.citaRepository = citaRepository;
        this.historialMedicoHasAlergiaRepository2 = historialMedicoHasAlergiaRepository2;
        this.alergiaRepository = alergiaRepository;
        this.informeRepository = informeRepository;
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
        for (Integer idAlergia : idAlergias) {
            listaAlergias.add(alergiaRepository.obtenerAlergia(idAlergia));
        }
        model.addAttribute("listaAlergias",listaAlergias);
        List<Cita> listaCitasPorUsuario = citaRepository.citasPorUsuario(id);
        model.addAttribute("listaCitasPorUsuario",listaCitasPorUsuario);

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
        Optional<Usuario> optionalUsuario = usuarioRepository.findById("12345678");
        Usuario usuario = optionalUsuario.get();
        model.addAttribute("usuario",usuario);
        List<Sede> sedeList = sedeRepository.sedesMenosActual(usuario.getSedesIdsedes().getId());
        model.addAttribute("sedeList",sedeList);
        return "doctor/config";
    }

    @PostMapping("/cambiarSede")
    public String cambiarSede(RedirectAttributes attr,
                              @RequestParam("id") int id){
        usuarioRepository.actualizarSede(id);
        attr.addFlashAttribute("msg", "Se actualizó la sede del usuario");
        return "redirect:/doctor/config";
    }

    @PostMapping("/cambiarContrasena")
    public String cambiarContrasena(RedirectAttributes attr,
                                    @RequestParam("contrasena") String password,
                                    @RequestParam("contrasena_repetida") String password_repetida){

        if(password.equals(password_repetida)){
            attr.addFlashAttribute("contrasena_correcta", "Se actualizó la contraseña del usuario");
        }else {
            attr.addFlashAttribute("contrasenas_diferentes", "Ambas contraseñas deben ser iguales");
        }

        return "redirect:/doctor/config";
    }

    @PostMapping("/enviarCuest")
    public String enviarCuest(RedirectAttributes attr,
                              @RequestParam("pacientecorreo") String correo,
                              @RequestParam("mensaje") String mensaje){

        Usuario usuario = usuarioRepository.findByEmail(correo);
        if(usuario.getId()!=null){
            attr.addFlashAttribute("cuestionario_correcto", "Se envió el cuestionario.");
        }else {
            attr.addFlashAttribute("no_existe_correo", "No existe el correo indicado");
        }

        return "redirect:/doctor/cuestionarios";
    }

}
