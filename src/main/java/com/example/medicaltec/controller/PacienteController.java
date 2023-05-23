package com.example.medicaltec.controller;


import com.example.medicaltec.Entity.*;
import com.example.medicaltec.dto.RecetaMedicamentoDto;
import com.example.medicaltec.repository.HistorialMedicoRepository;
import com.example.medicaltec.repository.TipoCitaRepository;
import com.example.medicaltec.repository.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @RequestMapping(value = "/principal")
    public String paginaprincipal(Model model){
        Usuario usuario = usuarioRepository.findByid("22647853");
        List<Usuario> doctores = usuarioRepository.obtenerlistaDoctores(usuario.getSedesIdsedes().getId());
        model.addAttribute("usuario", usuario);
        model.addAttribute("doctores", doctores);
        model.addAttribute("sedes", sedeRepository.findAll());
        model.addAttribute("arch", "arch");
        return "paciente/principal";
   }

    @RequestMapping("/perfil")
    public String perfilpaciente(@ModelAttribute("alergia")Alergia alergia, Model model){
        Usuario usuario = usuarioRepository.findByid("22647853");
        List<Integer> idAlergias = historialMedicoHasAlergiaRepository.listarAlergiasPorId(usuario.getHistorialmedicoIdhistorialmedico().getId());
        ArrayList<Alergia> alergias = new ArrayList<>();
        for(int i=0; i<idAlergias.size(); i++){
            alergias.add(alergiaRepository.obtenerAlergia(idAlergias.get(i)));
        }
        model.addAttribute("alergias", alergias);
        model.addAttribute("usuario", usuario);
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
    public String citas(Model model, HttpSession session){
        Usuario usuario = usuarioRepository.findByid("22647853");
        Usuario usuario1 = (Usuario) session.getAttribute("usuario");
        System.out.println(usuario1.getApellido());
        List<Cita> citas = citaRepository.historialCitas2(usuario.getId());
        //recetaHasMedicamentoRepository.listarMedxId(citas.get().getRecetaIdreceta());
        ;

        for (int i = 0; i < citas.size(); i++) {

            List<RecetaMedicamentoDto> recetaMedicamentoDtoList = recetaRepository.RecetasxMedicam(citas.get(i).getRecetaIdreceta().getId());
            model.addAttribute("medicamentos", recetaMedicamentoDtoList);

        }


        /*for (int i = 0; i < citas.size(); i++) {
            List<RecetaHasMedicamentoId> ids = recetaHasMedicamentoRepository.listarMedxId(citas.get(i).getRecetaIdreceta().getId());
            model.addAttribute("medicamentos",recetaHasMedicamentoRepository.findAllById(ids));
            //recetaHasMedicamentoRepository.findAllById(ids);

        }*/

        model.addAttribute("usuario", usuario);
        model.addAttribute("citas", citas);

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
        Usuario usuario = usuarioRepository.findByid("22647853");
        model.addAttribute("usuario", usuario);

        List<Cita> citaspas =  citaRepository.historialCitas2(usuario.getId());

        for (int i = 0; i < citaspas.size(); i++) {
            Boleta boleta = boletaRepository.obtenerRecetaxBoleta(citaspas.get(i).getRecetaIdreceta().getId());
            model.addAttribute("boleta", boleta);

        }
        model.addAttribute("citas", citaRepository.historialCitas2(usuario.getId()));


        model.addAttribute("medicamentos", medicamentoRepository.findAll());
        model.addAttribute("arch", "windowzzz");
       return "paciente/pagos";
    }
    @RequestMapping("/cuestionarios")
    public String cuestionarios(Model model, @RequestParam("id") int id){

        //Cuestionario cues = cuestionarioRepository.findById(id);

        model.addAttribute("preguntas",preguntaRepository.obtenerPreg(id));
        model.addAttribute("arch", "windowzzz");
       return "paciente/cuestionarios";
    }

    @GetMapping("/sede")
    public String cambiarSede(@RequestParam("id")String idSede, RedirectAttributes attr){
        String id = sedeRepository.verificaridSede(idSede);
        if(id!=null){
            sedeRepository.cambiarSede(idSede);
            attr.addFlashAttribute("msg1", "Se cambió la sede exitosamente");
        }else{
            attr.addFlashAttribute("msg3", "Error al intentar cambiar la sede");
        }
        return "redirect:/paciente/principal";
    }
    @GetMapping("/agendarCita")
    public String agendarCita(@ModelAttribute("cita")Cita cita,Model model){
        Usuario usuario = usuarioRepository.findByid("22647853");
        List<Usuario> doctores = usuarioRepository.obtenerlistaDoctores(usuario.getSedesIdsedes().getId());
        ArrayList<String> modalidad = new ArrayList<>();
        modalidad.add("Presencial");
        modalidad.add("Virtual");
        ArrayList<String> formapago = new ArrayList<>();
        formapago.add("En caja");
        formapago.add("Virtual");
        model.addAttribute("usuario", usuario);
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
                              Model model, RedirectAttributes attr){
        if(bindingResult.hasErrors()){
            Usuario usuario = usuarioRepository.findByid("22647853");
            List<Usuario> doctores = usuarioRepository.obtenerlistaDoctores(usuario.getSedesIdsedes().getId());
            ArrayList<String> modalidad = new ArrayList<>();
            modalidad.add("Presencial");
            modalidad.add("Virtual");
            ArrayList<String> formapago = new ArrayList<>();
            formapago.add("En caja");
            formapago.add("Virtual");
            model.addAttribute("usuario", usuario);
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
                Usuario usuario = usuarioRepository.findByid("22647853");
                List<Usuario> doctores = usuarioRepository.obtenerlistaDoctores(usuario.getSedesIdsedes().getId());
                ArrayList<String> modalidad = new ArrayList<>();
                modalidad.add("Presencial");
                modalidad.add("Virtual");
                ArrayList<String> formapago = new ArrayList<>();
                formapago.add("En caja");
                formapago.add("Virtual");
                model.addAttribute("usuario", usuario);
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
    public String cambiarSeguro(@RequestParam("seguro") String seguro, RedirectAttributes attr){
        String id = seguroRepository.verificaridSeguro(seguro);
        if(id!=null){
            usuarioRepository.cambiarSeguro(id);
            attr.addFlashAttribute("msg1", "Se cambió el seguro exitosamente");
        }else{
            attr.addFlashAttribute("msg3", "Error al intentar cambiar el seguro");
        }
        return "redirect:/paciente/perfil";
    }

    @PostMapping("/guardarAlergias")
    public String guardarAlergias(@ModelAttribute("alergia") @Valid Alergia alergia, BindingResult bindingResult,
                                  @RequestParam("id") String id,
                                  RedirectAttributes attr, Model model) {
        if (bindingResult.hasErrors()) {
            Usuario usuario = usuarioRepository.findByid("22647853");
            List<Integer> idAlergias = historialMedicoHasAlergiaRepository.listarAlergiasPorId(usuario.getHistorialmedicoIdhistorialmedico().getId());
            ArrayList<Alergia> alergias = new ArrayList<>();
            for(int i=0; i<idAlergias.size(); i++){
                alergias.add(alergiaRepository.obtenerAlergia(idAlergias.get(i)));
            }
            model.addAttribute("alergias", alergias);
            model.addAttribute("usuario", usuario);
            model.addAttribute("seguros", seguroRepository.findAll());
            model.addAttribute("arch", "windowzzz");
            return "paciente/perfil";
        }else{
            Usuario usuario = usuarioRepository.findByid(id);
            alergia.setEnabled(true);
            alergiaRepository.save(alergia);
            Integer key = alergiaRepository.lastID();
            historialMedicoHasAlergiaRepository.aea(usuario.getHistorialmedicoIdhistorialmedico().getId(), key);
            attr.addFlashAttribute("msg2", "Se agregó la alergia exitosamente");
            return "redirect:/paciente/perfil";
        }
    }

    @PostMapping("/guardarRespuestas")
    public String guardarRptas( @RequestParam("respuesta0")String respuesta, RedirectAttributes attr){

        //Cuestionario cues = cuestionarioRepository.findById(id);
        //List<Pregunta> preguntas = preguntaRepository.obtenerPreg(id);
        rptaRepository.guardarRptas(respuesta);


        attr.addFlashAttribute("msg2", "Se guardaron las respuestas exitosamente");
        return "redirect:/paciente/cuestionarios?id=1";
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
}
