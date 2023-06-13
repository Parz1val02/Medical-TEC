package com.example.medicaltec.controller;

import com.example.medicaltec.dto.Sede1Dto;
import com.example.medicaltec.dto.SedeDto;
import com.example.medicaltec.dto.SeguroDto;
import com.example.medicaltec.funciones.Regex;
import com.example.medicaltec.Entity.*;
import com.example.medicaltec.dto.RecetaMedicamentoDto;
import com.example.medicaltec.repository.HistorialMedicoRepository;
import com.example.medicaltec.repository.TipoCitaRepository;
import com.example.medicaltec.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    final HistorialMedicoRepository historialMedicoRepository;
    final SedeRepository sedeRepository;
    final SeguroRepository seguroRepository;

    final EspecialidadRepository especialidadRepository;

    final AlergiaRepository alergiaRepository;

    final BoletaRepository boletaRepository;

    final UsuarioRepository usuarioRepository;

    final RolesRepository rolesRepository;
    final TipoCitaRepository tipoCitaRepository;
    final CitaRepository citaRepository;

    final MedicamentoRepository medicamentoRepository;

    final PreguntaRepository preguntaRepository;

    final CuestionarioRepository cuestionarioRepository;
    final RptaRepository rptaRepository;
    final HistorialMedicoHasAlergiaRepository historialMedicoHasAlergiaRepository;

    final RecetaHasMedicamentoRepository recetaHasMedicamentoRepository;
    final RecetaRepository recetaRepository;


    public PacienteController(HistorialMedicoRepository historialMedicoRepository, SedeRepository sedeRepository, SeguroRepository seguroRepository, EspecialidadRepository especialidadRepository, AlergiaRepository alergiaRepository, BoletaRepository boletaRepository, UsuarioRepository usuarioRepository, RolesRepository rolesRepository,
                              TipoCitaRepository tipoCitaRepository, CitaRepository citaRepository, MedicamentoRepository medicamentoRepository, PreguntaRepository preguntaRepository, RptaRepository rptaRepository, HistorialMedicoHasAlergiaRepository historialMedicoHasAlergiaRepository, RecetaHasMedicamentoRepository recetaHasMedicamentoRepository,

                              CuestionarioRepository cuestionarioRepository,
                              RecetaRepository recetaRepository) {
        this.historialMedicoRepository = historialMedicoRepository;

        this.sedeRepository = sedeRepository;
        this.seguroRepository = seguroRepository;
        this.especialidadRepository = especialidadRepository;
        this.alergiaRepository = alergiaRepository;
        this.boletaRepository = boletaRepository;
        this.usuarioRepository = usuarioRepository;
        this.rolesRepository = rolesRepository;
        this.tipoCitaRepository = tipoCitaRepository;
        this.citaRepository = citaRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.preguntaRepository = preguntaRepository;
        this.rptaRepository = rptaRepository;
        this.historialMedicoHasAlergiaRepository=historialMedicoHasAlergiaRepository;
        this.recetaHasMedicamentoRepository = recetaHasMedicamentoRepository;
        this.cuestionarioRepository = cuestionarioRepository;
        this.recetaRepository = recetaRepository;
    }

    @RequestMapping(value = "/principal")
    public String paginaprincipal(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        SedeDto sedeUsuario = sedeRepository.getSede(usuario.getId());
        model.addAttribute("sedes", sedeRepository.findAll());
        model.addAttribute("sedeUsuario", sedeUsuario);
        List<Sede> listaSedes = sedeRepository.findAll();
        model.addAttribute("listaSedes",listaSedes);
        List<Sede1Dto> listaSedes1 = sedeRepository.sedeMapa();
        model.addAttribute("listaSedes1",listaSedes1);
        return "paciente/principal";
   }

    @RequestMapping("/perfil")
    public String perfilpaciente(@ModelAttribute("alergia")Alergia alergia, Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        SeguroDto seguroUsuario = seguroRepository.getSeguro(usuario.getId());
        List<Integer> idAlergias = historialMedicoHasAlergiaRepository.listarAlergiasPorId(usuario.getHistorialmedicoIdhistorialmedico().getId());
        ArrayList<Alergia> alergias = new ArrayList<>();
        for(int i=0; i<idAlergias.size(); i++){
            alergias.add(alergiaRepository.obtenerAlergia(idAlergias.get(i)));
        }
        model.addAttribute("alergias", alergias);
        model.addAttribute("seguros", seguroRepository.findAll());
        model.addAttribute("seguroUsuario", seguroUsuario);
        return "paciente/perfil";
    }

    @GetMapping("/password")
    public String cambiarContra(Model model, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        return "paciente/cambioContrasena";
    }

    @RequestMapping("/consultas")
    public String citas(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Cita> citas = citaRepository.historialCitas2(usuario.getId());
        //recetaHasMedicamentoRepository.listarMedxId(citas.get().getRecetaIdreceta());
        ArrayList<Medicamento> medicamentos = new ArrayList<>();
        for (int i = 0; i < citas.size(); i++) {
            //List<RecetaMedicamentoDto> recetaMedicamentoDtoList = recetaRepository.RecetasxMedicam(citas.get(i).getRecetaIdreceta().getId());
            List<Integer> idmed = recetaHasMedicamentoRepository.listarMedxId(citas.get(i).getRecetaIdreceta().getId());

            for (int j = 0; j < idmed.size(); j++) {
                medicamentos.add(medicamentoRepository.obtenerMedicamento(idmed.get(j)));
            }
        }
        model.addAttribute("medicamentos", medicamentos);
        model.addAttribute("citas", citas);
        return "paciente/consultas";
    }


    @RequestMapping("/notificaciones")
    public String notificaciones(Model model, HttpServletRequest httpServletRequest,HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
       return "paciente/notificaciones";
    }
    @RequestMapping("/mensajeria")
    public String mensajeria(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
       return "paciente/mensajeria";
    }

    @RequestMapping("/pagos")
    public String pagos(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        model.addAttribute("usuario", usuario);

        List<Cita> citaspas =  citaRepository.historialCitas2(usuario.getId());
        ArrayList<Boleta> boletas = new ArrayList<>();
        for (int i = 0; i < citaspas.size(); i++) {
            boletas.add(boletaRepository.obtenerCitaxBoleta(citaspas.get(i).getId()));
        }
        model.addAttribute("boletas", boletas);
        model.addAttribute("citas", citaRepository.historialCitas2(usuario.getId()));
        model.addAttribute("medicamentos", medicamentoRepository.findAll());
       return "paciente/pagos";
    }

    @RequestMapping("/listaDoctores")
    public String doctores(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        SedeDto sedeUsuario = sedeRepository.getSede(usuario.getId());
        List<Usuario> doctores = usuarioRepository.obtenerlistaDoctores(sedeUsuario.getId());
        /*ArrayList<Usuario> doctores1 =  new ArrayList<>();
        ArrayList<Usuario> doctores2 =  new ArrayList<>();
        for(int i=0; i<(doctoresA.size()/2);i++){
           doctores1.add(doctoresA.get(i));
           doctores2.add(doctoresA.get(i+doctoresA.size()/2));
        }
        model.addAttribute("doctores1", doctores1);
        model.addAttribute("doctores2", doctores2);
        */
        model.addAttribute("doctores", doctores);
        return "paciente/listarDoctores";
    }

    @RequestMapping("/cuestionarios")
    public String cuestionarios(Model model,HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        Cuestionario cuestionario=cuestionarioRepository.cuestionaXPaciente(usuario.getId());
        //creo que se debe enviar tambien lista de preguntas por cuestionario
        ArrayList<Pregunta> preguntas = new ArrayList<>();

        /*for (int i = 0; i < cuestionarioList.size(); i++) {
            preguntas.add(  preguntaRepository.obtenerPreguntas( cuestionarioList.get(i).getId())  );
        }*/
        model.addAttribute("cuestionarios",cuestionarioRepository.cuestionaXPaciente(usuario.getId()));
       return "paciente/cuestionarios";
    }

    @GetMapping("/sede")
    public String cambiarSede(@RequestParam("id")String idSede, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        String id = sedeRepository.verificaridSede(idSede);
        if(id!=null){
            sedeRepository.cambiarSede(idSede, usuario.getId());
            attr.addFlashAttribute("msg1", "Se cambió la sede exitosamente");
        }else{
            attr.addFlashAttribute("msg3", "Error al intentar cambiar la sede");
        }
        return "redirect:/paciente/principal";
    }
    @GetMapping("/agendarCita")
    public String agendarCita(@ModelAttribute("cita")Cita cita,Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Usuario> doctores = usuarioRepository.obtenerlistaDoctores(usuario.getSedesIdsedes().getId());
        ArrayList<String> modalidad = new ArrayList<>();
        modalidad.add("Presencial");
        modalidad.add("Virtual");
        ArrayList<String> formapago = new ArrayList<>();
        formapago.add("En caja");
        formapago.add("Virtual");
        model.addAttribute("doctores", doctores);
        model.addAttribute("sedes", sedeRepository.findAll());
        model.addAttribute("especialidades", especialidadRepository.findAll());
        model.addAttribute("tipos", tipoCitaRepository.findAll());
        model.addAttribute("modalidades", modalidad);
        model.addAttribute("pagos", formapago);
       return "paciente/agendar";
    }
    @PostMapping("/guardarCita")
    public String guardarCita(@ModelAttribute("cita")@Valid Cita cita, BindingResult bindingResult,
                              Model model, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(bindingResult.hasErrors()){
            List<Usuario> doctores = usuarioRepository.obtenerlistaDoctores(usuario.getSedesIdsedes().getId());
            ArrayList<String> modalidad = new ArrayList<>();
            modalidad.add("Presencial");
            modalidad.add("Virtual");
            ArrayList<String> formapago = new ArrayList<>();
            formapago.add("En caja");
            formapago.add("Virtual");
            model.addAttribute("doctores", doctores);
            model.addAttribute("sedes", sedeRepository.findAll());
            model.addAttribute("especialidades", especialidadRepository.findAll());
            model.addAttribute("tipos", tipoCitaRepository.findAll());
            model.addAttribute("modalidades", modalidad);
            model.addAttribute("pagos", formapago);
            return "paciente/agendar";
        }else{
            Usuario paciente = usuarioRepository.findByid(cita.getPaciente().getId());
            Usuario doctor = usuarioRepository.findByid(cita.getDoctor().getId());
            if(paciente == null || doctor == null) {
                List<Usuario> doctores = usuarioRepository.obtenerlistaDoctores(usuario.getSedesIdsedes().getId());
                ArrayList<String> modalidad = new ArrayList<>();
                modalidad.add("Presencial");
                modalidad.add("Virtual");
                ArrayList<String> formapago = new ArrayList<>();
                formapago.add("En caja");
                formapago.add("Virtual");
                model.addAttribute("doctores", doctores);
                model.addAttribute("sedes", sedeRepository.findAll());
                model.addAttribute("especialidades", especialidadRepository.findAll());
                model.addAttribute("tipos", tipoCitaRepository.findAll());
                model.addAttribute("modalidades", modalidad);
                model.addAttribute("pagos", formapago);
                model.addAttribute("errorUsuario","Input ingresado no válido");
                return "paciente/agendar";
            }
            Estadoscita estadoscita = new Estadoscita();
            estadoscita.setId(1);
            cita.setCitacancelada(false);
            cita.setPaciente(paciente);
            cita.setDoctor(doctor);
            cita.setEstadoscitaIdestados(estadoscita);
            citaRepository.save(cita);

            //para la boleta de la cita creada
            Boleta boleta=new Boleta();
            boletaRepository.crearBoletaCita(tipoCitaRepository.findById(1).get().getTipoCita(), 60, SPA.getSegurosIdSeguro().getId(), null, cita.getId());
            attr.addFlashAttribute("msg", "Cita agendada de manera exitosa");
        }
        return "redirect:/paciente/principal";
    }

    @PostMapping("/cambiarSeguro")
    public String cambiarSeguro(@RequestParam("seguro") String seguro, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        String id = seguroRepository.verificaridSeguro(seguro);
        if(id!=null){
            usuarioRepository.cambiarSeguro(id, usuario.getId());
            attr.addFlashAttribute("msg1", "Se cambió el seguro exitosamente");
        }else{
            attr.addFlashAttribute("msg3", "Error al intentar cambiar el seguro");
        }
        return "redirect:/paciente/perfil";
    }

    @PostMapping("/guardarAlergias")
    public String guardarAlergias(@ModelAttribute("alergia") @Valid Alergia alergia, BindingResult bindingResult,
                                  RedirectAttributes attr, Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication) {
        Regex regex = new Regex();
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if (bindingResult.hasErrors()) {
            List<Integer> idAlergias = historialMedicoHasAlergiaRepository.listarAlergiasPorId(usuario.getHistorialmedicoIdhistorialmedico().getId());
            ArrayList<Alergia> alergias = new ArrayList<>();
            for (int i = 0; i < idAlergias.size(); i++) {
                alergias.add(alergiaRepository.obtenerAlergia(idAlergias.get(i)));
            }
            model.addAttribute("alergias", alergias);
            model.addAttribute("seguros", seguroRepository.findAll());
            model.addAttribute("arch", "windowzzz");
            return "paciente/perfil";
        }else if(!regex.inputisValid(alergia.getNombre())){
            List<Integer> idAlergias = historialMedicoHasAlergiaRepository.listarAlergiasPorId(usuario.getHistorialmedicoIdhistorialmedico().getId());
            ArrayList<Alergia> alergias = new ArrayList<>();
            for(int i = 0; i < idAlergias.size(); i++) {
                alergias.add(alergiaRepository.obtenerAlergia(idAlergias.get(i)));
            }
            model.addAttribute("alergias", alergias);
            model.addAttribute("seguros", seguroRepository.findAll());
            model.addAttribute("arch", "windowzzz");
            attr.addFlashAttribute("msgRegex", "Ingresar solo texto en el input");
            return "redirect:/paciente/perfil";
        }else{
            alergia.setEnabled(true);
            alergiaRepository.save(alergia);
            Integer key = alergiaRepository.lastID();
            historialMedicoHasAlergiaRepository.aea(usuario.getHistorialmedicoIdhistorialmedico().getId(), key);
            attr.addFlashAttribute("msg2", "Se agregó la alergia exitosamente");
            return "redirect:/paciente/perfil";
        }
    }

    //Adaptarlo para sesiones
    @GetMapping("/responderCuestionario")
    public String responderCues(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");

        Cuestionario cuestionario = cuestionarioRepository.cuestionaXPaciente(usuario.getId());

        ArrayList<Pregunta> preguntasCuestionario = new ArrayList<>();
        ArrayList<Cuestionario> cuestionarios1 = new ArrayList<>();
        /*for (int i = 0; i < ; i++) {
            cuestionarios1.add(cuestionarios.get(i));
        }*/


        List<Pregunta> preguntas = preguntaRepository.obtenerPreguntas(cuestionario.getId());


        //List<Pregunta> preguntas = preguntaRepository.obtenerPreguntas(cuestionarios.get().getId());
        Respuesta respuesta=new Respuesta();
        model.addAttribute("preguntas", preguntas);
        model.addAttribute("respuesta", respuesta);
        return "paciente/responderCuestionario";
    }



    @PostMapping("/guardarRespuestas")
    public String guardarRptas( @ModelAttribute("respuestas")@Valid Respuesta respuesta, BindingResult bindingResult, RedirectAttributes attr, Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){

        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        //Cuestionario cues = cuestionarioRepository.findById(id);

        if (bindingResult.hasErrors()){

            return "/paciente/responderCuestionario";
        }else {
            //model.addAttribute("preguntas");

            attr.addFlashAttribute("msg2", "Se guardaron las respuestas exitosamente");
            return "redirect:/paciente/cuestionarios";

        }
        //List<Pregunta> preguntas = preguntaRepository.obtenerPreg(id);
        //rptaRepository.guardarRptas(respuesta);

    }


    @GetMapping("/borrarAlergia")
    public String borrarAlergia(@RequestParam("id")String id,
                                @RequestParam("id2")String id2,
                                RedirectAttributes attr){
        String idAlergia = alergiaRepository.verificaridAlergia(id);
        String idHistorialMedico = historialMedicoRepository.verificaridHistorialMedico(id2);
        if(idAlergia!=null && idHistorialMedico!=null){
            alergiaRepository.borrarAlergia(idAlergia);
            historialMedicoHasAlergiaRepository.borrarweas(idHistorialMedico, idAlergia);
            attr.addFlashAttribute("msg4", "Se borró la alergia exitosamente");
        }else{
            attr.addFlashAttribute("msg5", "Error al intentar borrar la alergia");
        }
        return "redirect:/paciente/perfil";
    }
    @PostMapping("/change")
    public String changePassword(@RequestParam("pass1") String pass1,
                                 @RequestParam("pass2") String pass2,
                                 @RequestParam("pass3") String pass3, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication)
    {
        Regex regex = new Regex();
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        String passwordAntiguabCrypt = usuarioRepository.buscarPasswordPropioUsuario(usuarioSession.getId());
        boolean passwordActualCoincide = BCrypt.checkpw(pass1, passwordAntiguabCrypt);
        if(pass1.equals("") || pass2.equals("") || pass3.equals("")){
            attr.addFlashAttribute("errorPass", "Los campos no pueden estar vacios");
            return "redirect:/paciente/password";
        } else if (!passwordActualCoincide ) {
            attr.addFlashAttribute("errorPass", "Ocurrió un error durante el cambio de contraseña. No se aplicaron cambios.");
            return "redirect:/paciente/password";
        } else if (!pass3.equals(pass2) ) {
            attr.addFlashAttribute("errorPass", "Las nuevas contraseñas no coinciden");
            return "redirect:/paciente/password";
        }else if(!regex.contrasenaisValid(pass2)){
            attr.addFlashAttribute("errorPass", "La nueva contraseña no coincide con los requerimientos.");
            return "redirect:/paciente/password";
        }else {
            usuarioRepository.cambiarContra(new BCryptPasswordEncoder().encode(pass3), usuarioSession.getId());
            attr.addFlashAttribute("msgContrasenia","Su contraseña ha sido cambiada exitosamente");
            return "redirect:/paciente/perfil";
        }
    }
    @PostMapping("/guardarFoto")
    public String guardarFoto(@RequestParam("file")MultipartFile file, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(file.isEmpty()){
           attr.addFlashAttribute("foto", "Debe subir un archivo");
           return "redirect:/paciente/perfil";
        }
        String filename = file.getOriginalFilename();
        if(filename.contains("..")){
            attr.addFlashAttribute("foto", "No se permiten caracteres especiales");
            return "redirect:/paciente/perfil";
        }
        try{
           usuario.setFoto(file.getBytes());
           usuario.setFotonombre(filename);
           usuario.setFotocontenttype(file.getContentType());
           usuarioRepository.save(usuario);
           attr.addFlashAttribute("fotoSiu", "Foto actualizada de manera exitosa");
           return "redirect:/paciente/perfil";
        } catch (IOException e) {
            e.printStackTrace();
            attr.addFlashAttribute("foto", "Error al intentar actualizar foto");
            return "redirect:/paciente/perfil";
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> mostrarImagen(@PathVariable("id") String id){
        Optional<Usuario> opt = usuarioRepository.findById(id);
        if(opt.isPresent()){
            Usuario u = opt.get();
            byte[] imagenComoBytes = u.getFoto();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.parseMediaType(u.getFotocontenttype()));
            return new ResponseEntity<>(imagenComoBytes, httpHeaders, HttpStatus.OK);
        }else{
            return null;
        }
    }
}
