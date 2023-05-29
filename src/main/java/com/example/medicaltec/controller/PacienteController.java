package com.example.medicaltec.controller;


import com.example.medicaltec.Entity.*;
import com.example.medicaltec.dto.RecetaMedicamentoDto;
import com.example.medicaltec.repository.HistorialMedicoRepository;
import com.example.medicaltec.repository.TipoCitaRepository;
import com.example.medicaltec.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

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
    private final RecetaRepository recetaRepository;


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

    @RequestMapping(value = {"/principal","/"})
    public String paginaprincipal(Model model, HttpServletRequest httpServletRequest){
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Usuario> doctores = usuarioRepository.obtenerlistaDoctores(usuario.getSedesIdsedes().getId());
        model.addAttribute("doctores", doctores);
        model.addAttribute("sedes", sedeRepository.findAll());
        List<Sede> listaSedes = sedeRepository.findAll();
        model.addAttribute("listaSedes",listaSedes);
        model.addAttribute("arch", "arch");
        return "paciente/principal";
   }

    @RequestMapping("/perfil")
    public String perfilpaciente(@ModelAttribute("alergia")Alergia alergia, Model model, HttpServletRequest httpServletRequest){
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Integer> idAlergias = historialMedicoHasAlergiaRepository.listarAlergiasPorId(usuario.getHistorialmedicoIdhistorialmedico().getId());
        ArrayList<Alergia> alergias = new ArrayList<>();
        for(int i=0; i<idAlergias.size(); i++){
            alergias.add(alergiaRepository.obtenerAlergia(idAlergias.get(i)));
        }
        model.addAttribute("alergias", alergias);
        model.addAttribute("seguros", seguroRepository.findAll());
        model.addAttribute("arch", "windowzzz");
        return "paciente/perfil";
    }

    @GetMapping("/password")
    public String cambiarContra(Model model){
        model.addAttribute("arch", "windowzzz");
        return "paciente/cambioContrasena";
    }

    @RequestMapping("/consultas")
    public String citas(Model model, HttpServletRequest httpServletRequest){
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Cita> citas = citaRepository.historialCitas2(usuario.getId());
        //recetaHasMedicamentoRepository.listarMedxId(citas.get().getRecetaIdreceta());
        List<Cita> citas1 = citaRepository.historialCitasAgendadas(usuario.getId());
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
        model.addAttribute("citas1", citas1);
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
    public String pagos(Model model, HttpServletRequest httpServletRequest){
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
        model.addAttribute("arch", "windowzzz");
       return "paciente/pagos";
    }
    @RequestMapping("/cuestionarios")
    public String cuestionarios(@ModelAttribute("respuesta")Respuesta respuesta, Model model, @RequestParam("id") int id){

        //Cuestionario cues = cuestionarioRepository.findById(id);
        //falta logica que depende de la relacion para jalar los ids de los cuestioanrio
        model.addAttribute("preguntas",preguntaRepository.obtenerPreg(id));
        model.addAttribute("arch", "windowzzz");
       return "paciente/cuestionarios";
    }

    @GetMapping("/sede")
    public String cambiarSede(@RequestParam("id")String idSede, RedirectAttributes attr, HttpServletRequest httpServletRequest){
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
    public String agendarCita(@ModelAttribute("cita")Cita cita,Model model, HttpServletRequest httpServletRequest){
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
        model.addAttribute("arch", "windowzzz");
       return "paciente/agendar";
    }
    @PostMapping("/guardarCita")
    public String guardarCita(@ModelAttribute("cita")@Valid Cita cita, BindingResult bindingResult,
                              Model model, RedirectAttributes attr, HttpServletRequest httpServletRequest){
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
            model.addAttribute("arch", "windowzzz");
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
                model.addAttribute("arch", "windowzzz");
                return "paciente/agendar";
            }
            cita.setCitacancelada(false);
            cita.setPaciente(paciente);
            cita.setDoctor(doctor);
            citaRepository.save(cita);
            attr.addFlashAttribute("msg", "Cita agendada de manera exitosa");
        }
        return "redirect:/paciente/principal";
    }

    @PostMapping("/cambiarSeguro")
    public String cambiarSeguro(@RequestParam("seguro") String seguro, RedirectAttributes attr, HttpServletRequest httpServletRequest){
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
                                  RedirectAttributes attr, Model model, HttpServletRequest httpServletRequest) {
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if (bindingResult.hasErrors()) {
            List<Integer> idAlergias = historialMedicoHasAlergiaRepository.listarAlergiasPorId(usuario.getHistorialmedicoIdhistorialmedico().getId());
            ArrayList<Alergia> alergias = new ArrayList<>();
            for(int i=0; i<idAlergias.size(); i++){
                alergias.add(alergiaRepository.obtenerAlergia(idAlergias.get(i)));
            }
            model.addAttribute("alergias", alergias);
            model.addAttribute("seguros", seguroRepository.findAll());
            model.addAttribute("arch", "windowzzz");
            return "paciente/perfil";
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
    @PostMapping("/guardarRespuestas")
    public String guardarRptas( @ModelAttribute("respuesta") @Valid Respuesta respuesta, BindingResult bindingResult, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if (bindingResult.hasErrors()) {
            //List<Integer> idsCuest = ;
            //falta logica que depende de la relacion para jalar los ids de los cuestioanrio
            return "redirect:/paciente/cuestionarios";
        } else {
            respuesta.setHistorialmedicoIdhistorialmedico(usuario.getHistorialmedicoIdhistorialmedico());
            //respuesta.setPreguntasIdpreguntas();
            rptaRepository.save(respuesta);

            attr.addFlashAttribute("msg2", "Se guardaron las respuestas exitosamente");
            return "redirect:/paciente/cuestionarios?id=1";
        }
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
                                 @RequestParam("pass3") String pass3, RedirectAttributes attr, HttpServletRequest httpServletRequest)
    {
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(pass1.equals("") || pass2.equals("") || pass3.equals("")){
            attr.addFlashAttribute("errorPass", "Los campos no pueden estar vacios");
            return "redirect:/paciente/password";
        //}else if(!pass1.equals(usuarioRepository.passAdmv())){
        //    attr.addFlashAttribute("errorPass", "La contraseña actual no coincide");
        //    return "redirect:/paciente/password";
        } else if (!pass3.equals(pass2) ) {
            attr.addFlashAttribute("errorPass", "Las nuevas contraseñas no son iguales");
            return "redirect:/paciente/password";
        }else {
            usuarioRepository.cambiarContra(new BCryptPasswordEncoder().encode(pass3), usuario.getId());
            attr.addFlashAttribute("msgContrasenia","Su contraseña ha sido cambiada exitosamente");
            return "redirect:/paciente/perfil";
        }

    }
}
