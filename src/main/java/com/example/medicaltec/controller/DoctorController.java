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
import org.springframework.web.servlet.view.RedirectView;

import java.net.http.HttpClient;
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
    final ReunionVirtualRepository reunionVirtualRepository;

    public DoctorController(SedeRepository sedeRepository, CuestionarioRepository cuestionarioRepository, UsuarioRepository usuarioRepository, MensajeRepository mensajeRepository, NotificacioneRepository notificacioneRepository, CitaRepository citaRepository, HistorialMedicoHasAlergiaRepository2 historialMedicoHasAlergiaRepository2, AlergiaRepository alergiaRepository, InformeRepository informeRepository, ReunionVirtualRepository reunionVirtualRepository) {
        this.sedeRepository = sedeRepository;
        this.cuestionarioRepository = cuestionarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.mensajeRepository = mensajeRepository;
        this.notificacioneRepository = notificacioneRepository;
        this.citaRepository = citaRepository;
        this.historialMedicoHasAlergiaRepository2 = historialMedicoHasAlergiaRepository2;
        this.alergiaRepository = alergiaRepository;
        this.informeRepository = informeRepository;
        this.reunionVirtualRepository = reunionVirtualRepository;
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

        //aqui corrijes esto carlos
        //model.addAttribute("videollamadas",  citaRepository.citasxEnlace());

        return "doctor/principal";
    }


    @GetMapping("/historial")
    public String verHistorial(Model model, @RequestParam("id") String id, RedirectAttributes attr){
        // Obtener paciente
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()){
            Usuario paciente = optionalUsuario.get();
            model.addAttribute("paciente",paciente);
            // Nombre completo del sexo de la persona
            if(paciente.getSexo().equals("M")){
                paciente.setSexo("Masculino");
            }else if (paciente.getSexo().equals("F")){
                paciente.setSexo("Femenino");
            }
            // Obtener alergias
            int id_paciente = paciente.getHistorialmedicoIdhistorialmedico().getId();
            List<Integer> idAlergias = historialMedicoHasAlergiaRepository2.listarAlergiasPorId(id_paciente);
            ArrayList<Alergia> listaAlergias = new ArrayList<>();
            for (Integer idAlergia : idAlergias) {
                listaAlergias.add(alergiaRepository.obtenerAlergia(idAlergia));
            }
            model.addAttribute("listaAlergias",listaAlergias);
            // Obtener informes y citas por usuario
            model.addAttribute("informesPorUsuario",informeRepository.listarInformesPorPaciente(paciente.getId()));
            return "doctor/historial";
        }else {
            attr.addFlashAttribute("historial_noexiste","El historial médico a ver no existe");
            return "redirect:/doctor/pacientes";
        }

    }


    //videollamada
    @GetMapping("/videollamada")
    public RedirectView videollamada(Model model, @RequestParam("idCita") String id, RedirectAttributes attr){
        // Obtener paciente

        ReunionVirtual reu  =reunionVirtualRepository.ReuPorCita(Integer.parseInt(id) );

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(reu.getEnlace());
        return redirectView;


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
        List<String> listadepacientesstring = citaRepository.pacientesdeldoctor();
        List<Usuario> listapaciente = new ArrayList<>();
        for (String dnipaciente : listadepacientesstring){
            Usuario pacientedentro = usuarioRepository.findByid(dnipaciente);
            listapaciente.add(pacientedentro);
        }
        //System.out.println(listadepacientesstring);
        //System.out.println(listapaciente);
        model.addAttribute("listapaciente",listapaciente);
        return "doctor/pacientes";
    }

    @Autowired
    InformeNuevoRepository informeNuevoRepository;

    @GetMapping("/citas")
    public String verCitas(Model model){
        model.addAttribute("listaCitas",citaRepository.pacientesAtendidos());
        model.addAttribute("tiposdeinformes", informeNuevoRepository.findAll());
        return "doctor/citas";
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
                              @RequestParam("id") int id, HttpSession httpSession){
        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        usuarioRepository.actualizarSede2(id, usuario.getId() );
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



    //Adaptarlo para sesiones
    /*@GetMapping("/llenarInforme")
    public String llenarInforme(@RequestParam("idCita") String idcuestionario,Model model,
                                HttpServletRequest httpServletRequest,
                                RedirectAttributes attr, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        System.out.println(idcuestionario);
        int id  = Integer.parseInt(idcuestionario);
        Optional<Cita> optionalCita= citaRepository.findById(id);
        if (optionalCita.isPresent()) {
            Cita cciiitaassss= optionalCita.get();
            String entrada = cuestionariosUsuarios.getIdcuestionario().getPreguntas();
            List<String> listapreguntas = List.of(entrada.split("#!%&%!#"));
            cuestionariosUsuarios.getIdcuestionario().setListapreguntas(listapreguntas);
            model.addAttribute("cuestionario", cuestionariosUsuarios);
            return "paciente/responderCuestionario";
        } else {
            attr.addFlashAttribute("cuestionario_noexiste","El cuestionario a responder no existe");
            return "redirect:/doctor/cuestionarios";
        }
    }
    @GetMapping("/llenarInforme2")
    public String llenarInforme2(@RequestParam("idCita") String idcuestionario,Model model,
                                HttpServletRequest httpServletRequest,
                                RedirectAttributes attr, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        System.out.println(idcuestionario);
        int id  = Integer.parseInt(idcuestionario);
        Optional<Cita> optionalCita= citaRepository.findById(id);
        if (optionalCita.isPresent()) {
            Cita cciiitaassss= optionalCita.get();
            //String entrada = cuestionariosUsuarios.getIdcuestionario().getPreguntas();
            //List<String> listapreguntas = List.of(entrada.split("#!%&%!#"));
            //cuestionariosUsuarios.getIdcuestionario().setListapreguntas(listapreguntas);
            //model.addAttribute("cuestionario", cuestionariosUsuarios);
            return "paciente/responderCuestionario";
        } else {
            attr.addFlashAttribute("cuestionario_noexiste","El cuestionario a responder no existe");
            return "redirect:/paciente/cuestionarios";
        }
    }

    @Autowired
    InformeNuevoRepository informeNuevoRepository;
    /**@GetMapping("/verInforme")
    public String verRespuestas(@RequestParam("idCuestionario") String idcuestionario,Model model,
                                HttpServletRequest httpServletRequest,
                                RedirectAttributes attr, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        System.out.println(idcuestionario);
        int id  = Integer.parseInt(idcuestionario);
        Optional<CuestionariosUsuarios> optionalCuestionariosUsuarios= cuestionariosUsuariosRepository.findById(id);
        if (optionalCuestionariosUsuarios.isPresent()) {
            CuestionariosUsuarios cuestionariosUsuarios= optionalCuestionariosUsuarios.get();
            String entrada = cuestionariosUsuarios.getIdcuestionario().getPreguntas();
            List<String> listapreguntas = List.of(entrada.split("#!%&%!#"));
            cuestionariosUsuarios.getIdcuestionario().setListapreguntas(listapreguntas);
            String entrada2 = cuestionariosUsuarios.getRespuestas();
            List<String> listarespuestas = List.of(entrada2.split("#!%&%!#"));
            cuestionariosUsuarios.setListarespuestas(listarespuestas);
            model.addAttribute("cuestionario", cuestionariosUsuarios);
            return "paciente/verRespuestas";
        } else {
            attr.addFlashAttribute("cuestionario_noexiste","El cuestionario a responder no existe");
            return "redirect:/paciente/cuestionarios";
        }
    }
*/
}
