package com.example.medicaltec.controller;

import com.example.medicaltec.Entity.*;
import com.example.medicaltec.funciones.Regex;
import com.example.medicaltec.more.CorreoConEstilos;
import com.example.medicaltec.repository.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.LocalTime;
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
    final HorasDoctorRepository horasDoctorRepository;
    @Autowired
    RecetaRepository recetaRepository;

    @Autowired
    RecetaHasMedicamentoRepository recetaHasMedicamentoRepository;

    @Autowired
    MedicamentoRepository medicamentoRepository;

    @Autowired
    ExamenMedicoRepository examenMedicoRepository;

    public DoctorController(SedeRepository sedeRepository, CuestionarioRepository cuestionarioRepository, UsuarioRepository usuarioRepository, MensajeRepository mensajeRepository, NotificacioneRepository notificacioneRepository, CitaRepository citaRepository, HistorialMedicoHasAlergiaRepository2 historialMedicoHasAlergiaRepository2, AlergiaRepository alergiaRepository, InformeRepository informeRepository, ReunionVirtualRepository reunionVirtualRepository, HorasDoctorRepository horasDoctorRepository) {
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
        this.horasDoctorRepository = horasDoctorRepository;
    }
    @Autowired
    CuestionariosRepository cuestionariosRepository;

    @RequestMapping(value = "/principal", method = {RequestMethod.GET,RequestMethod.POST})
    public String pagPrincipalDoctor(Model model, HttpSession httpSession){
        Usuario usuario_doctor = (Usuario) httpSession.getAttribute("usuario");

        model.addAttribute("listaPacientes",usuarioRepository.listarPacientes());
        model.addAttribute("listaMensajes",mensajeRepository.listarMensajesPorReceptor(usuario_doctor.getId()));
        model.addAttribute("listaNotificaciones",notificacioneRepository.listarNotisActualesSegunUsuario(usuario_doctor.getId()));
        model.addAttribute("listaProximasCitas",citaRepository.citasParaVer(usuario_doctor.getId()));
        model.addAttribute("usuario",usuario_doctor);
        model.addAttribute("listaCuestionarios",cuestionariosRepository.findAll());
        return "doctor/principal";
    }


    @GetMapping("/iniciarCita")
    public String iniciarCita(Model model, @RequestParam("idUsuario") String idUsuario, @RequestParam("idInforme") String idInforme,
                              RedirectAttributes attr, HttpSession httpSession){

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(idUsuario);
        System.out.println("usuario entero");
        if (optionalUsuario.isPresent()){
            System.out.println("usuario encontrado");
            if(esNumeroEntero(idInforme)){
                System.out.println("informe entero");
                Integer iddinforme = Integer.parseInt(idInforme);
                Optional<InformeNuevo> optionalInformeNuevo = informeNuevoRepository.findById(iddinforme);
                if(optionalInformeNuevo.isPresent()){
                    System.out.println("informe nuevo existente");
                    // Llenar informe
                    httpSession.removeAttribute("iddelinforme");
                    httpSession.setAttribute("iddelinforme",iddinforme);
                    InformeNuevo informeNuevo = optionalInformeNuevo.get();
                    model.addAttribute("titulo",informeNuevo.getNombre());
                    String campostexto = informeNuevo.getCampos();
                    List<String> listadecampos = List.of(campostexto.split(">%%%%%<%%%%>%%%%%<"));
                    informeNuevo.setListacampos(listadecampos);
                    model.addAttribute("informe",informeNuevo);
                    List<Medicamento> listademedicamentos = medicamentoRepository.findAll();
                    model.addAttribute("listmedic", listademedicamentos);

                    // Historial clínico
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
                    return "doctor/iniciarcita";

                }else {
                    return "redirect:/doctor/principal";
                }
            }else {
                return "redirect:/doctor/principal";
            }
        }else {
            return "redirect:/doctor/principal";
        }


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
    public String videollamada(Model model, @RequestParam("idCita") String id, RedirectAttributes attr){
        // Cambiamos el estado de cita a 'En consulta'


        // Obtener paciente
        ReunionVirtual reu  =reunionVirtualRepository.ReuPorCita(Integer.parseInt(id) );
        /*for (Cita c:citaRepository.proximasCitasAgendadas()) {
            c.getId();
            citaRepository.cambiarEstadoCita(2 , Integer.parseInt(id) );

        }*/
        model.addAttribute("reu",reunionVirtualRepository.ReuPorCita(Integer.parseInt(id) ) );
        //RedirectView redirectView = new RedirectView();
        //redirectView.setUrl(reu.getEnlace());
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("enlace",reu.getEnlace() );
        //modelAndView.setViewName("redirect:" +  );
        return "";


    }

    @GetMapping("/notificaciones")
    public String verNotificaciones(HttpSession  httpSession){
        Usuario usuario_doctor = (Usuario) httpSession.getAttribute("usuario");
        System.out.println("EL USUARIO ESSSSS:");
        System.out.println(
                usuario_doctor.getNombre()
        );
        return "doctor/notificaciones";}

    @GetMapping("/calendario")
    public String verCalendario(HttpSession httpSession){
        Usuario usuario_doctor = (Usuario) httpSession.getAttribute("usuario");
        return "doctor/calendario";
    }

    @GetMapping("/mensajeria")
    public String verMensajes(){return "doctor/mensajeria";}


    @GetMapping("/pacientes")
    public String verPacientes(Model model, HttpSession httpSession){
        Usuario usuario_doctor = (Usuario) httpSession.getAttribute("usuario");
        model.addAttribute("listaCitas",citaRepository.pacientesAtendidos(usuario_doctor.getId()));
        List<String> listadepacientesstring = citaRepository.pacientesdeldoctor(usuario_doctor.getId());
        List<Usuario> listapaciente = new ArrayList<>();
        for (String dnipaciente : listadepacientesstring){
            Usuario pacientedentro = usuarioRepository.findByid(dnipaciente);
            listapaciente.add(pacientedentro);
        }
        model.addAttribute("listapaciente",listapaciente);
        return "doctor/pacientes";
    }

    @Autowired
    InformeNuevoRepository informeNuevoRepository;

    @GetMapping("/citas")
    public String verCitas(Model model, HttpSession httpSession){
        Usuario usuario_doctor = (Usuario) httpSession.getAttribute("usuario");
        model.addAttribute("listaCitas",citaRepository.pacientesAtendidos(usuario_doctor.getId()));
        model.addAttribute("tiposdeinformes", informeNuevoRepository.findAll());
        return "doctor/citas";
    }

    @GetMapping("/elegirInforme")
    public String elegirInforme (@RequestParam("idCita") String idCita,Model model,RedirectAttributes attr,HttpSession httpSession){
        if (esNumeroEntero(idCita)){
            Integer iddecita = Integer.parseInt(idCita);
            Optional<Cita> optionalCita = citaRepository.findById(iddecita);
            if (optionalCita.isPresent()){
                Cita citas = optionalCita.get();
                httpSession.removeAttribute("idcitaparainforme");
                httpSession.setAttribute("idcitaparainforme",citas.getId());
                List<InformeNuevo> listainformes = informeNuevoRepository.findAll();
                for (InformeNuevo informes : listainformes){
                    String entrada1 = informes.getCampos();
                    List<String> listacampos = List.of(entrada1.split(">%%%%%<%%%%>%%%%%<"));
                    informes.setListacampos((listacampos));
                }
                model.addAttribute("informeList", listainformes);
                return "doctor/eleccionInforme";
            }else {
                return "redirect:/doctor/citas";
            }
        }else {
            return "redirect:/doctor/citas";
        }
    }

    @GetMapping("/elegirPlantilla")
    public String elegirPlantilla (@RequestParam("idCita") String idCita,@RequestParam("idUsuario") String idUsuario,
                                   Model model,RedirectAttributes attr,HttpSession httpSession){
        citaRepository.cambiarEstadoCita(5, Integer.parseInt(idCita));
        if (esNumeroEntero(idCita)){
            Integer iddecita = Integer.parseInt(idCita);
            Optional<Cita> optionalCita = citaRepository.findById(iddecita);
            if (optionalCita.isPresent()){
                Cita citas = optionalCita.get();
                httpSession.removeAttribute("idcitaparainforme");
                httpSession.setAttribute("idcitaparainforme",citas.getId());
                List<InformeNuevo> listainformes = informeNuevoRepository.findAll();
                for (InformeNuevo informes : listainformes){
                    String entrada1 = informes.getCampos();
                    List<String> listacampos = List.of(entrada1.split(">%%%%%<%%%%>%%%%%<"));
                    informes.setListacampos((listacampos));
                }
                model.addAttribute("informeList", listainformes);
                model.addAttribute("idUsuario",idUsuario);
                return "doctor/elegirPlantilla";
            }else {
                return "redirect:/doctor/citas";
            }
        }else {
            return "redirect:/doctor/citas";
        }
    }

    @GetMapping("/llenarInforme")
    public String llenarInforme(@RequestParam("idInforme") String idinforme,
                                @RequestParam("checkboxName") Boolean valorCheckbox,RedirectAttributes attr,
                                HttpSession httpSession,Model model){
        if(esNumeroEntero(idinforme)){
            Integer iddinforme = Integer.parseInt(idinforme);
            Optional<InformeNuevo> optionalInformeNuevo = informeNuevoRepository.findById(iddinforme);
            if(optionalInformeNuevo.isPresent()){
                httpSession.removeAttribute("iddelinforme");
                httpSession.setAttribute("iddelinforme",iddinforme);
                InformeNuevo informeNuevo = optionalInformeNuevo.get();
                model.addAttribute("titulo",informeNuevo.getNombre());
                String campostexto = informeNuevo.getCampos();
                List<String> listadecampos = List.of(campostexto.split(">%%%%%<%%%%>%%%%%<"));
                informeNuevo.setListacampos(listadecampos);
                model.addAttribute("informe",informeNuevo);
                List<Medicamento> listademedicamentos = medicamentoRepository.findAll();
                model.addAttribute("listmedic", listademedicamentos);

                if (valorCheckbox){ //si se marca el checkbox
                    model.addAttribute("examenes", examenMedicoRepository.findAll());
                }else {
                    model.addAttribute("");
                }
                return "doctor/llenarInforme";
            }else {
                return "redirect:/doctor/citas";
            }

        }else {
            return "redirect:/doctor/citas";
        }
    }

    @PostMapping("/enviarBitacoras")
    public String enviarBitacoras(@RequestParam("listaidInformes") String listaidInformes, @RequestParam("listaBitacoras") String listaBitacoras,
                                  Model model){

        String[] informesid = listaidInformes.split(">%%%%%<%%%%>%%%%%<");
        String[] bitacoraContent = listaBitacoras.split(">%%%%%<%%%%>%%%%%<");

        for (int i=0;i<informesid.length;i++){
            String idInforme = informesid[i];
            String bitacora = bitacoraContent[i];
            informeRepository.ingresarBitacora(bitacora, Integer.parseInt(idInforme));
        }

        model.addAttribute("bitacoracambios","Se guardaron los comentarios correctamento");
        return "redirect:/doctor/pacientes";
    }
    @PostMapping("/rellenarInforme")
    public String rellenarInforme(@RequestParam("diagnostico") String diag,
                                  @RequestParam("tratamiento") String trat,
                                  @RequestParam("listamedicamentos") String med,
                                  @RequestParam("listacantidades") String cant,
                                  @RequestParam("listaobservaciones") String obser,
                                  @RequestParam("listaexamenes")String examenes,
                                  @RequestParam("comentario") String comen,@RequestParam("listarespuestas") String respuestas,
                                  @RequestParam("checkboxName") Boolean valorCheckbox,
                                RedirectAttributes attr, Model model, HttpSession httpSession) throws MessagingException {
        int iddelinformenuevo = (int) httpSession.getAttribute("iddelinforme");
        int iddelacita = (int) httpSession.getAttribute("idcitaparainforme");
        String dniusuario;
        Optional<Cita> optionalCita = citaRepository.findById(iddelacita);
        int a = 0;
        if(optionalCita.isPresent()){
            Cita cita = optionalCita.get();
            dniusuario = cita.getPaciente().getId();
            String diagnostico  = diag;
            String tratamiento = trat;
            String medicamente  = med;
            String cantidad = cant;
            String observaciones = obser;
            String comentario  =comen;
            String examen  =examenes;//verfiicar o corregir para examenes
            String[] respuestasSeparadas = respuestas.split(">%%%%%<%%%%>%%%%%<");
            String [] listamedicamentos = medicamente.split(">%%%%%<%%%%>%%%%%<");
            String [] listaobservaciones = observaciones.split(">%%%%%<%%%%>%%%%%<");
            String [] listacantidades = cantidad.split(">%%%%%<%%%%>%%%%%<");
            String [] listaexamenes = examenes.split(">%%%%%<%%%%>%%%%%<");
            String salida ="";
            String separador ="#!%&%!#";
            int i = 0;
            for (String respuesta : respuestasSeparadas){
                System.out.println(respuesta);
                System.out.println(i);
                if (i==0){
                    salida = salida+respuesta;
                }else {
                    salida = salida + separador + respuesta;
                }
                i++;
            }
            for (int k =0; k<listamedicamentos.length;k++){
                String indicemedi = listamedicamentos[k];
                String indiceobser = listaobservaciones[k];
                String indicecant = listacantidades[k];
                if(esNumeroEntero(indicemedi)){
                    if(!esNumeroEntero(indicecant)){
                        attr.addFlashAttribute("error","La cantidad de medicamentos ingresada debe ser un número entero");
                        a++;
                        return "redirect:/doctor/llenarInforme?idInforme=?"+iddelinformenuevo;
                    }else {
                        a = 0;
                    }
                }else {
                    if(!esNumeroEntero(indicecant)){
                        attr.addFlashAttribute("error","El medicamento seleccionado no es válido. La cantidad ingresada debe ser un número entero");
                        a++;
                        return "redirect:/doctor/llenarInforme?idInforme=?"+iddelinformenuevo;
                    }
                    attr.addFlashAttribute("error","El medicamento seleccionado no es válido.");
                    a++;
                    return "redirect:/doctor/llenarInforme?idInforme=?"+iddelinformenuevo;
                }
            }

            //falatria algo similar para los examenes
            if(a==0){
                System.out.println(salida);
                String camposllenados = salida;
                attr.addFlashAttribute("respondido","Informe rellenado con éxito");
                System.out.println(camposllenados +"adad" + diagnostico +"adad"+tratamiento+"adad"+medicamente+"adad"+cantidad);
                informeRepository.rellenarInforme(dniusuario,iddelacita,diagnostico,tratamiento,camposllenados,iddelinformenuevo);
                Integer informe1 = informeRepository.idinformecreado(iddelacita);
                recetaRepository.crearReceta(comentario,informe1);
                Integer idrecetacreada = recetaRepository.idrecetacreada(informe1);
                for (int k =0; k<listamedicamentos.length;k++){
                    String indicemedi = listamedicamentos[k];
                    String indiceobser = listaobservaciones[k];
                    String indicecant = listacantidades[k];
                    Integer intindicemedi = Integer.parseInt(indicemedi);
                    Integer intindicecant = Integer.parseInt(indicecant);
                    recetaHasMedicamentoRepository.llenarRecMed(idrecetacreada,intindicemedi,intindicecant,indiceobser);
                    citaRepository.informeRecetaCita(idrecetacreada,informe1,iddelacita);
                    httpSession.removeAttribute("iddelinforme");
                    httpSession.removeAttribute("idcitaparainforme");
                    if (valorCheckbox){ //validando si se marcó el checkbox
                        usuarioRepository.actualizarEstadoPacientePendienteExa(dniusuario);
                        CorreoConEstilos correoConEstilos = new CorreoConEstilos();
                        correoConEstilos.sendEmailEstilos(usuarioRepository.findByid(dniusuario).getEmail(), "Cambio a nuevo estado", "Su estado actual es " + usuarioRepository.findByid(dniusuario).getEstadosIdestado().getNombre() );
                        correoConEstilos.sendEmailEstilos(usuarioRepository.findByid(dniusuario).getEmail(), "Recordatorio", "Recuerde separar el examen medico pendiente en un maximo de 7 dias " );


                    }

                }
            }
            return "redirect:/doctor/citas";
        }else {
            attr.addFlashAttribute("error","Ha ocurrido un error inesperado");
            return "redirect:/doctor/citas";
        }
    }

    @GetMapping("/cuestionarios")
    public String verCuestionarios(Model model){
        List<Cuestionarios> cuestionariosList = cuestionariosRepository.listaDeCuestionarios();
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
    public String verBoletas(Model model, HttpSession httpSession){
        Usuario usuario_doctor = (Usuario) httpSession.getAttribute("usuario");
        model.addAttribute("listaCitas",citaRepository.pacientesAtendidos(usuario_doctor.getId()));
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

    @PostMapping("/enviarHorasDoctor")
    public String horasDoctor(@RequestParam("mes") String mes, @RequestParam("dia") String dia,
                              @RequestParam("horainicio") String horainicio, @RequestParam("horafin") String horafin,
                              @RequestParam("horalibre") String horalibre, HttpSession httpSession,RedirectAttributes attr ){

        Horasdoctor horasdoctor = new Horasdoctor();

        Usuario doctor = (Usuario) httpSession.getAttribute("usuario");
        LocalTime horaInicio = LocalTime.parse(horainicio);
        LocalTime horaFin = LocalTime.parse(horafin);
        LocalTime horaLibre = LocalTime.parse(horalibre);

        Duration diferencia = Duration.between(horaFin,horaInicio); //citasHora - variableHoras

        long horas = diferencia.toHours();
        long minutos = diferencia.toMinutes() % 60;

        if (horas > 0){
            if (horaLibre.isBefore(horaInicio) || horaLibre.equals(horaInicio) || horaLibre.isAfter(horaFin) || horaLibre.equals(horaFin)){
                attr.addFlashAttribute("msgError","La hora libre debe encontrarse entre la hora de Inicio y la hora de Fin, y ser diferente de ellas.");
            }else{
                horasDoctorRepository.guardarHorasDoc(horaInicio,horaFin,horaLibre,doctor.getId(),dia,mes);
                attr.addFlashAttribute("msg","Horas de doctor guardadas exitosamente.");
            }
        }else {
            attr.addFlashAttribute("msgError","Las fechas deben llevarse como mínimo 1 hora.");
        }

        return "redirect:/doctor/config";
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
