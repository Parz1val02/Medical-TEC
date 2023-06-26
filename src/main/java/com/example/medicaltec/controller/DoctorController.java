package com.example.medicaltec.controller;

import com.example.medicaltec.Entity.*;
import com.example.medicaltec.funciones.Regex;
import com.example.medicaltec.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// GOOGLE_APPLICATION_CREDENTIALS=C:\Users\Labtel\Downloads\glowing-hearth-316315-3a00093f1823.json
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
    @Autowired
    CuestionariosRepository cuestionariosRepository;
    @RequestMapping(value = "/principal", method = {RequestMethod.GET,RequestMethod.POST})
    public String pagPrincipalDoctor(Model model, HttpSession httpSession){
        Usuario usuario_doctor = (Usuario) httpSession.getAttribute("usuario");

        model.addAttribute("listaPacientes",usuarioRepository.listarPacientes());
        model.addAttribute("listaMensajes",mensajeRepository.listarMensajesPorReceptor(usuario_doctor.getId()));
        model.addAttribute("listaNotificaciones",notificacioneRepository.listarNotisActualesSegunUsuario(usuario_doctor.getId()));
        model.addAttribute("listaProximasCitas",citaRepository.proximasCitasAgendadas());
        model.addAttribute("usuario",usuario_doctor);
        model.addAttribute("listaCuestionarios",cuestionariosRepository.findAll());


        return "doctor/principal";
    }


    @GetMapping("/historial")
    public String verHistorial(Model model, @RequestParam("id") String id){
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        Usuario paciente = optionalUsuario.get();
        model.addAttribute("paciente",paciente);

        List<Integer> idAlergias = historialMedicoHasAlergiaRepository2.listarAlergiasPorId(paciente.getHistorialmedicoIdhistorialmedico().getId());
        ArrayList<Alergia> listaAlergias = new ArrayList<>();
        for (Integer idAlergia : idAlergias) {
            listaAlergias.add(alergiaRepository.obtenerAlergia(idAlergia));
        }
        model.addAttribute("listaAlergias",listaAlergias);
        List<Cita> listaCitasPorUsuario = citaRepository.citasPorUsuario(id);
        model.addAttribute("listaCitasPorUsuario",listaCitasPorUsuario);

        if(paciente.getSexo().equals("M")){
            paciente.setSexo("Masculino");
        }else if (paciente.getSexo().equals("F")){
            paciente.setSexo("Femenino");
        }

        return "doctor/historial";
    }

    @GetMapping("/notificaciones")
    public String verNotificaciones(){return "doctor/notificaciones";}

    @GetMapping("/calendario")
    public String verCalendario(HttpSession httpSession){
        Usuario usuario_doctor = (Usuario) httpSession.getAttribute("usuario");
        return "doctor/calendario";
    }

    @GetMapping("/mensajeria")
    public String verMensajes(){return "doctor/mensajeria";}

    @GetMapping("/pacientes")
    public String verPacientes(Model model){
        model.addAttribute("listaCitas",citaRepository.pacientesAtendidos());
        return "doctor/pacientes";
    }

    @GetMapping("/cuestionarios")
    public String verCuestionarios(Model model){
        List<Cuestionarios> cuestionariosList = cuestionariosRepository.findAll();
        for (Cuestionarios cue : cuestionariosList){
            String entrada = cue.getPreguntas();
            List<String> listapreguntas = List.of(entrada.split("#!%&%!#"));
            cue.setListapreguntas(listapreguntas);
        }
        model.addAttribute("cuestionariosList",cuestionariosList);
        model.addAttribute("listaPacientes",usuarioRepository.obtenerListaPacientes());
        return "doctor/cuestionarios";
    }

    @GetMapping("/boletas")
    public String verBoletas(Model model){
        model.addAttribute("listaCitas",citaRepository.pacientesAtendidos());
        return "doctor/boletas";
    }

    @GetMapping("/config")
    public String verConfiguracion(Model model, HttpSession httpSession){
        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        model.addAttribute("usuario",usuario);

        if(usuario.getSexo().equals("M")){
            usuario.setSexo("Masculino");
        }else if (usuario.getSexo().equals("F")){
            usuario.setSexo("Femenino");
        }


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

    public boolean esNumeroEntero(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    @PostMapping("/enviarCuest")
    public String enviarCuest(Model model,@RequestParam("usuario") String paciente,
            @RequestParam("mensaje") String mensaje,@RequestParam("cuest") String cuestionarioid,RedirectAttributes attr,
                              HttpSession httpSession){
        Usuario doctor = (Usuario) httpSession.getAttribute("usuario");
        System.out.println(paciente);
        System.out.println(mensaje);
        System.out.println(cuestionarioid);
        int a = 0;
        String alerta  = "Se han producido los siguientes errores: ";
        if (!(paciente.length()==8)){
            a = a+1;
            String alerta1 = "|Dni erroneo| ";
            alerta = alerta + alerta1;
        }
        if (cuestionarioid.isEmpty() || cuestionarioid.isBlank()){
            a = a+1;
            String alerta2 = "|id de cuestionario vacío| ";
            alerta = alerta + alerta2;

        }else {
            if(!esNumeroEntero(cuestionarioid)){
                a = a+1;
                String alerta3 = "|id de cuestionario no es un número|";
                alerta = alerta + alerta3;
            }
        }
        if (a==0){
            int idcuest = Integer.parseInt(cuestionarioid);
            cuestionariosRepository.asignarCuestionario(idcuest,paciente,"",0,doctor.getId());
            attr.addFlashAttribute("cuestionario_enviado","Cuestionario enviado exitosamente.");
            return "redirect:/doctor/cuestionarios";
        }else {
            attr.addFlashAttribute("cuestionario_noenviado",alerta);
            return "redirect:/doctor/cuestionarios";
        }
    }

    @PostMapping("/cambiarContrasena")
    public String cambiarContrasena(@RequestParam("pass1") String pass1,
                                    @RequestParam("pass2") String pass2,
                                    @RequestParam("pass3") String pass3, RedirectAttributes attr, HttpServletRequest httpServletRequest)
    {
        Regex regex = new Regex();
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        String passwordAntiguabCrypt = usuarioRepository.buscarPasswordPropioUsuario(usuarioSession.getId());
        boolean passwordActualCoincide = BCrypt.checkpw(pass1, passwordAntiguabCrypt);
        if(pass1.equals("") || pass2.equals("") || pass3.equals("")){
            attr.addFlashAttribute("errorPass", "Los campos no pueden estar vacíos");
            return "redirect:/doctor/config";
        } else if (!passwordActualCoincide ) {
            attr.addFlashAttribute("errorPass", "Ocurrió un error durante el cambio de contraseña. No se aplicaron cambios.");
            return "redirect:/doctor/config";
        } else if (!pass3.equals(pass2) ) {
            attr.addFlashAttribute("errorPass", "Las contraseñas ingresadas no coinciden");
            return "redirect:/doctor/config";
        }else if(!regex.contrasenaisValid(pass2)){
            attr.addFlashAttribute("errorPass", "La nueva contraseña no coincide con los requerimientos.");
            return "redirect:/doctor/config";
        }else {
            usuarioRepository.cambiarContra(new BCryptPasswordEncoder().encode(pass3), usuarioSession.getId());
            attr.addFlashAttribute("msgContrasenia","Su contraseña ha sido cambiada exitosamente");
            return "redirect:/doctor/config";
        }

    }

    @PostMapping("/enviarBitacora")
    public String enviarBitacora(@RequestParam("bitacora") String bitacora,
                                 @RequestParam("usuarioid") String usuarioid){


        return "redirect:/doctor/principal";
    }

}
