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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

// BCryptPasswordEncoder().encode(plainTextPassword);
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
    final CuestionariosRepository cuestionariosRepository;
    @Autowired
    RecetaRepository recetaRepository;

    @Autowired
    RecetaHasMedicamentoRepository recetaHasMedicamentoRepository;

    @Autowired
    MedicamentoRepository medicamentoRepository;

    @Autowired
    ExamenMedicoRepository examenMedicoRepository;

    public DoctorController(SedeRepository sedeRepository, CuestionarioRepository cuestionarioRepository, UsuarioRepository usuarioRepository, MensajeRepository mensajeRepository, NotificacioneRepository notificacioneRepository, CitaRepository citaRepository, HistorialMedicoHasAlergiaRepository2 historialMedicoHasAlergiaRepository2, AlergiaRepository alergiaRepository, InformeRepository informeRepository, ReunionVirtualRepository reunionVirtualRepository, HorasDoctorRepository horasDoctorRepository, CuestionariosRepository cuestionariosRepository, RecetaRepository recetaRepository) {
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
        this.cuestionariosRepository = cuestionariosRepository;
        this.recetaRepository = recetaRepository;
    }

    @RequestMapping(value = "/principal", method = {RequestMethod.GET,RequestMethod.POST})
    public String pagPrincipalDoctor(Model model, HttpSession httpSession){
        Usuario usuario_doctor = (Usuario) httpSession.getAttribute("usuario");

        LocalDate fechaActual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        List<Cita> citas = citaRepository.citasParaVer(usuario_doctor.getId());

        for (Cita cita : citas) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate citaFecha = LocalDate.parse(cita.getFecha(), formatter);

            LocalTime citaHoras = cita.getHora();

            if (fechaActual == citaFecha){
                System.out.println(citaHoras);
                System.out.println(citaFecha);
                System.out.println(horaActual);

                Duration diferencia = Duration.between(citaHoras,horaActual); //citasHora - variableHoras

                long horas = diferencia.toHours();
                long minutos = diferencia.toMinutes() % 60;
                long segundos = diferencia.getSeconds() % 60;

                System.out.println("Diferencia: " + horas + " horas, " + minutos + " minutos, " + segundos + " segundos.");

                if (horas == 0 && minutos > -10 && minutos < 0){
                    // Estado 4: En espera
                    citaRepository.cambiarEstadoCita(4,cita.getId());
                }else if (horas == 0 && minutos > 30){
                    // Estado 3: Culminada
                    citaRepository.cambiarEstadoCita(3,cita.getId());
                }
                // Estado 5: En consulta
                // Se cambia al dar clic en iniciar video

            }
        }

        model.addAttribute("listaPacientes",usuarioRepository.listarPacientes());
        model.addAttribute("listaMensajes",mensajeRepository.listarMensajesPorReceptor(usuario_doctor.getId()));
        model.addAttribute("listaNotificaciones",notificacioneRepository.listarNotisActualesSegunUsuario(usuario_doctor.getId()));
        model.addAttribute("listaProximasCitas",citaRepository.citasParaVer(usuario_doctor.getId()));
        model.addAttribute("usuario",usuario_doctor);

        List<Cuestionarios> cuestionariosList = cuestionariosRepository.listaDeCuestionarios();
        for (Cuestionarios cue : cuestionariosList){
            String entrada = cue.getPreguntas();
            List<String> listapreguntas = List.of(entrada.split("#!%&%!#"));
            cue.setListapreguntas(listapreguntas);
        }
        model.addAttribute("listaCuestionarios",cuestionariosList);


        return "doctor/principal";
    }


    @GetMapping("/iniciarCita")
    public String iniciarCita(Model model, @RequestParam("idUsuario") String idUsuario, @RequestParam("idInforme") String idInforme,
                              HttpSession httpSession){

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
            System.out.println(listaAlergias);

            StringJoiner stringJoiner = new StringJoiner(", ");
            for (Alergia alergia : listaAlergias) {
                String nombreAlergia = alergia.getNombre();
                stringJoiner.add(nombreAlergia);
            }

            String resultado = stringJoiner.toString();
            System.out.println(resultado);  // "1,2,3,4"
            model.addAttribute("resultado",resultado);


            // Obtener informes y citas por usuario
            model.addAttribute("informesPorUsuario",informeRepository.listarInformesPorPaciente(paciente.getId()));
            return "doctor/historial";
        }else {
            attr.addFlashAttribute("historial_noexiste","El historial médico a ver no existe");
            return "redirect:/doctor/pacientes";
        }

    }

    //videollamada
    /*
    @GetMapping("/videollamada")
    public String videollamada(Model model, @RequestParam("idCita") String id){
        // Cambiamos el estado de cita a 'En consulta'


        // Obtener paciente
        ReunionVirtual reu  =reunionVirtualRepository.ReuPorCita(Integer.parseInt(id) );
        for (Cita c:citaRepository.proximasCitasAgendadas()) {
            c.getId();
            citaRepository.cambiarEstadoCita(2 , Integer.parseInt(id) );

        }
        model.addAttribute("reu",reunionVirtualRepository.ReuPorCita(Integer.parseInt(id) ) );
        //RedirectView redirectView = new RedirectView();
        //redirectView.setUrl(reu.getEnlace());
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("enlace",reu.getEnlace() );
        //modelAndView.setViewName("redirect:" +  );
        return "";


    }
    */

    @GetMapping("/notificaciones")
    public String verNotificaciones(HttpSession  httpSession){
        Usuario usuario_doctor = (Usuario) httpSession.getAttribute("usuario");
        System.out.println("EL USUARIO ESSSSS:");
        System.out.println(
                usuario_doctor.getNombre()
        );
        return "doctor/notificaciones";}

    @GetMapping("/calendario")
    public String verCalendario(HttpSession httpSession, Model model){
        // Todos los meses
        ArrayList<String> array = new ArrayList<>();
        array.add("Enero");
        array.add("Febrero");
        array.add("Marzo");
        array.add("Abril");
        array.add("Mayo");
        array.add("Junio");
        array.add("Julio");
        array.add("Agosto");
        array.add("Septiembre");
        array.add("Octubre");
        array.add("Noviembre");
        array.add("Diciembre");

        // Mandar horas doctor de todos los meses
        Usuario doctor = (Usuario) httpSession.getAttribute("usuario");
        for (int i=0;i<array.size();i++){
            Optional<Horasdoctor> horasdoctorOptionalgeneral = Optional.ofNullable(horasDoctorRepository.obtenerHorasDoctorEnMes(doctor.getId(), array.get(i)));
            Horasdoctor horasdoctorgeneral = horasdoctorOptionalgeneral.orElse(null);
            String nombreHorasDoctor = "horasdoctor"+array.get(i);
            System.out.println("horasdoctor"+array.get(i));
            model.addAttribute(nombreHorasDoctor,horasdoctorgeneral);
        }

        // Vemos en que mes estamos
        LocalDate fechaActual = LocalDate.now();
        int mesActual = fechaActual.getMonthValue();
        String mes = obtenerMesPorNumero(mesActual);
        model.addAttribute("mesActual",mes);


        // Obtenemos si ya tiene horasDoctor en el mes
        Optional<Horasdoctor> horasdoctorOptional = Optional.ofNullable(horasDoctorRepository.obtenerHorasDoctorEnMes(doctor.getId(), mes));

        if (horasdoctorOptional.isPresent()){
            // Actualizar
            Horasdoctor horasdoctor = horasdoctorOptional.get();
            model.addAttribute("valor",1);
            model.addAttribute("horasDoctor",horasdoctor);
        }else {
            // Crear
            model.addAttribute("valor",0);
        }

        return "doctor/calendario";
    }

    @GetMapping("/borrarHorario")
    public String borrarHorasDoctor(@RequestParam("id") String id, RedirectAttributes attr){

        Optional<Horasdoctor> horasdoctorOptional = horasDoctorRepository.findById(Integer.parseInt(id));
        if (horasdoctorOptional.isPresent()){
            Horasdoctor horasdoctor = horasdoctorOptional.get();
            horasDoctorRepository.delete(horasdoctor);
            attr.addFlashAttribute("borrado","El horario fue eliminado con éxito, cree nuevamente un horario de disponibilidad");
            return "redirect:/doctor/calendario";
        }else {
            attr.addFlashAttribute("horasdoctornoexiste","Al parecer no tiene un horario existente, vuelva a intentarlo.");
            return "redirect:/doctor/calendario";
        }
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
    public String elegirInforme (@RequestParam("idCita") String idCita,Model model,HttpSession httpSession){
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
                                   Model model,HttpSession httpSession){
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
                List<ExamenMedico> listaexamenes = examenMedicoRepository.findAll();
                model.addAttribute("examen",listaexamenes);
                return "doctor/llenarInforme";
            }else {
                return "redirect:/doctor/citas";
            }

        }else {
            return "redirect:/doctor/citas";
        }
    }

    @GetMapping("/verInforme")
    public String verInforme(@RequestParam("idCita") String idCita,Model model, RedirectAttributes attr){
        if(esNumeroEntero(idCita)){
            Integer iddelacita = Integer.parseInt(idCita);
            Optional<Cita> optionalCita = citaRepository.findById(iddelacita);
            if(optionalCita.isPresent()){
                Cita cita = optionalCita.get();
                Informe informe = cita.getIdinforme();
                String camposfilled = informe.getCampos();
                List<String> listacamposfilled = List.of(camposfilled.split("#!%&%!#"));
                cita.getIdinforme().setListacamposllenados(listacamposfilled);
                String campospallenar = informe.getInformeNuevo().getCampos();
                List<String> listacampospallenar = List.of(campospallenar.split("#!%&%!#"));
                cita.getIdinforme().getInformeNuevo().setListacampos(listacampospallenar);
                model.addAttribute("citaOWO",cita);
                return "doctor/verInforme";
            }else {
                attr.addFlashAttribute("idcitainvalido","El identificador de la cita es inválido");
                return "redirect:/doctor/citas";
            }
        }else {
            attr.addFlashAttribute("idcitainvalido","El identificador de la cita no es un entero");
            return "redirect:/doctor/citas";
        }
    }

    @GetMapping("/examenes")
    @ResponseBody
    public List<ExamenMedico> enviarExamenes(){
        return examenMedicoRepository.findAll();
    }

    @PostMapping("/enviarBitacoras")
    public String enviarBitacoras(@RequestParam("listaidInformes") String listaidInformes, @RequestParam("listaBitacoras") String listaBitacoras,
                                  Model model){

        String[] informesid = listaidInformes.split(">%%%%%<%%%%>%%%%%<");
        String[] bitacoraContent = listaBitacoras.split(">%%%%%<%%%%>%%%%%<");

        for (int i=0;i<informesid.length;i++){
            String idInforme = informesid[i];
            String bitacora = bitacoraContent[i];
            if (bitacora.equals("nulo")){
                bitacora = "";
            }
            informeRepository.ingresarBitacora(bitacora, Integer.parseInt(idInforme));
        }

        model.addAttribute("bitacoracambios","Se guardaron los comentarios correctamento");
        return "redirect:/doctor/pacientes";
    }
    @PostMapping("/rellenarInforme")
    public String rellenarInforme(@RequestParam("diagnostico") String diagnostico,
                                  @RequestParam("tratamiento") String tratamiento,
                                  @RequestParam("listamedicamentos") String medicamente,
                                  @RequestParam("listacantidades") String cantidad,
                                  @RequestParam("listaobservaciones") String observaciones,
                                  @RequestParam("examen")String examen,
                                  @RequestParam("comentario") String comentario,@RequestParam("listarespuestas") String respuestas,
                                  @RequestParam(value = "checkboxName",required = false) Boolean valorCheckbox,
                                RedirectAttributes attr, HttpSession httpSession) throws MessagingException {
        int iddelinformenuevo = (int) httpSession.getAttribute("iddelinforme");
        int iddelacita = (int) httpSession.getAttribute("idcitaparainforme");
        String dniusuario;
        Optional<Cita> optionalCita = citaRepository.findById(iddelacita);
        int a = 0;
        if(optionalCita.isPresent()){
            Cita cita = optionalCita.get();
            dniusuario = cita.getPaciente().getId();
            String[] respuestasSeparadas = respuestas.split(">%%%%%<%%%%>%%%%%<");
            String [] listamedicamentos = medicamente.split(">%%%%%<%%%%>%%%%%<");
            String [] listaobservaciones = observaciones.split(">%%%%%<%%%%>%%%%%<");
            String [] listacantidades = cantidad.split(">%%%%%<%%%%>%%%%%<");
            String salida ="";
            String separador ="#!%&%!#";
            int i = 0;
            for (String respuesta : respuestasSeparadas){
                /*System.out.println(respuesta);
                System.out.println(i);*/
                if (i==0){
                    salida = salida+respuesta;
                }else {
                    salida = salida + separador + respuesta;
                }
                i++;
            }
            if(diagnostico.isEmpty()){
                attr.addFlashAttribute("diagnosticomsg","El diagnóstico no puede estar vacio");
                a++;
            }
            if(tratamiento.isEmpty()){
                attr.addFlashAttribute("tratamientomsg","El tratamiento no puede estar vacío");
                a++;
            }
           /* System.out.println("LA LONGITUD DEL ARRAY DE MEDICAMENTOS: " + listamedicamentos.length);
            System.out.println("LA LONGITUD DEL ARRAY DE CANTIDADES: " + listacantidades.length);
            System.out.println("LA LONGITUD DEL ARRAY DE OBSERVACIONES" + listaobservaciones.length);*/
            int medilength = medicamentoRepository.findAll().toArray().length;
            String [] listaerroresmedicamentos = new String[listamedicamentos.length];
            if(listamedicamentos.length!=0){
                for(int me = 0;me<listamedicamentos.length;me++){
                    /*System.out.println("primer INGRESO MEDICAMENTOS");*/
                    String indicemedi = listamedicamentos[me];
                    if(!esNumeroEntero(indicemedi)){
                        if(me==0){
                            attr.addFlashAttribute("medicamentomsg0","El medicamento seleccionado es inválido");
                        }
                        listaerroresmedicamentos[me]="El medicamento seleccionado es inválido";
                        a++;
                    }else {
                        int medi = Integer.parseInt(indicemedi);
                        if(me==0){
                            attr.addFlashAttribute("medicamentomsg0","El medicamento seleccionado es inválido");
                        }
                        if(0<medi && medi<=medilength){
                            listaerroresmedicamentos[me]=null;
                        }else {
                            listaerroresmedicamentos[me]="El medicamento seleccionado es inválido";
                            a++;
                        }
                    }
                }
            }else {
                attr.addFlashAttribute("medicamentomsg","Debe seleccionar un medicamento válido");
                a++;
            }
            String [] listaerrorescantidades = new String[listamedicamentos.length];
           /* System.out.println("cantidades erroneas:");*/
            if(listacantidades.length!=0){
                for(int ca = 0;ca<listamedicamentos.length;ca++){
                    if(ca<listacantidades.length){
                        /*System.out.println("primer ingreso cantidades");*/
                        String indicecant = listacantidades[ca];
                        if(!esNumeroEntero(indicecant)){
                            String nombremsg = "cantidadmsg"+(ca);
                            a++;
                            /*System.out.println(nombremsg);*/
                            listaerrorescantidades[ca] = "La cantidad ingresada no es un número entero";
                            if(ca==0){
                                attr.addFlashAttribute("cantidadmsg0","La cantidad ingresada no es un número entero");
                            }
                        }else {
                            int canti = Integer.parseInt(indicecant);
                            listaerroresmedicamentos[ca] = null;
                        }
                    }else {
                        String nombremsg = "cantidadmsg"+(ca);
                        a++;
                        /*System.out.println(nombremsg);*/
                        listaerrorescantidades[ca] = "La cantidad ingresada no es un número entero";
                    }
                }
            }else {
                for(int ca = 0;ca<listamedicamentos.length;ca++){
                    String nombremsg = "cantidadmsg"+(ca);
                    a++;
                    listaerrorescantidades[ca] = "La cantidad ingresada no es un número entero";
                    /*System.out.println(nombremsg);*/
                    attr.addFlashAttribute("cantidadmsg0","La cantidad ingresada no es un número entero");
                }
            }
            String [] listaerroresobservaciones = new String[listamedicamentos.length];
            if(listaobservaciones.length!=0){
                for(int obse = 0;obse<listamedicamentos.length;obse++){
                    if(obse<listaobservaciones.length){
                        String indiceobser = listaobservaciones[obse];
                        if(indiceobser.isEmpty()){
                            String nombremsg = "observacionmsg"+(obse);
                            a++;
                            /*System.out.println(nombremsg);*/
                            listaerroresobservaciones[obse] = "El campo de OBSERVACIONES no puede estar vacío";
                            attr.addFlashAttribute("observacionmsg0","El campo de OBSERVACIONES no puede estar vacío");
                        }else {
                            listaerroresobservaciones[obse] = null;
                        }
                    }else {
                        String nombremsg = "observacionmsg"+(obse);
                        a++;
                        /*System.out.println(nombremsg);*/
                        listaerroresobservaciones[obse] = "El campo de OBSERVACIONES no puede estar vacío";
                    }
                }
            }else {
                for(int obse = 0;obse<listamedicamentos.length;obse++){
                    String nombremsg = "observacionmsg"+(obse);
                    a++;
                    /*System.out.println(nombremsg);*/
                    listaerroresobservaciones[obse] = "El campo de OBSERVACIONES no puede estar vacío";
                    attr.addFlashAttribute("observacionmsg0","El campo de OBSERVACIONES no puede estar vacío");
                }
            }
            String checked = null;
            int b =0;
            if(valorCheckbox !=null){
                if (valorCheckbox){ //validando si se marcó el checkbox
                    if(esNumeroEntero(examen)){
                        b = 1;
                    }else {
                        attr.addFlashAttribute("examenmsg","El examen seleccionado no es válido.");
                        a++;
                        b=3;
                    }
                    checked = "sdad";
                }else{
                    checked = null;
                    b= 0;
                }
            }else {
                checked = null;
                b =0;
            }
            //falatria algo similar para los examenes
            if(a==0){//poner 0 pa mandar
                /*System.out.println(salida);*/
                String camposllenados = salida;
                /*System.out.println(camposllenados +"adad" + diagnostico +"adad"+tratamiento+"adad"+medicamente+"adad"+cantidad);*/
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

                }
                citaRepository.informeRecetaCita(idrecetacreada,informe1,iddelacita);
                if(b==1){
                    Integer idexamen = Integer.parseInt(examen);
                    citaRepository.updateExamenCita(idexamen,iddelacita);
                }
                if(b==1){
                    usuarioRepository.actualizarEstadoPacientePendienteExa(dniusuario);
                    CorreoConEstilos correoConEstilos = new CorreoConEstilos();
                    correoConEstilos.sendEmailEstilos2(usuarioRepository.findByid(dniusuario).getEmail(), "Cambio a nuevo estado", "Su estado actual es " + usuarioRepository.findByid(dniusuario).getEstadosIdestado().getNombre() );
                    correoConEstilos.sendEmailEstilos2(usuarioRepository.findByid(dniusuario).getEmail(), "Recordatorio", "Recuerde separar el examen medico pendiente en un maximo de 7 dias " );
                }
                httpSession.removeAttribute("iddelinforme");
                httpSession.removeAttribute("idcitaparainforme");
                attr.addFlashAttribute("respondido","Informe rellenado con éxito");
                citaRepository.cambiarEstadoCita(3,iddelacita);
                return "redirect:/doctor/citas";
            }else {
                String errordinamico = "SI";
                attr.addFlashAttribute("errordinamico",errordinamico);
                int medilargo  = listamedicamentos.length;
                int cantlargo = listacantidades.length;
                int obserlargo = listaobservaciones.length;
                String [] nuevalistacantidad = new String[medilargo];
                for (int p=0;p<medilargo;p++){
                    if(p<listacantidades.length){
                        nuevalistacantidad[p] = listacantidades[p];
                    }else {
                        nuevalistacantidad[p] = "";
                    }
                    /*System.out.println("La cantidad iterada es: "+nuevalistacantidad[p]);*/
                }
                attr.addFlashAttribute("cantidad",nuevalistacantidad[0]);
                if(medilargo>obserlargo){
                    String [] nuevalistaobservaciones = new String[medilargo];
                    for (int t=0;t<medilargo;t++){
                        if(t<listaobservaciones.length){
                            nuevalistaobservaciones[t] = listaobservaciones[t];
                        }else {
                            nuevalistaobservaciones[t] = "";
                        }
                    }
                    attr.addFlashAttribute("observacion",nuevalistaobservaciones[0]);
                    attr.addFlashAttribute("nuevalistaobservaciones",nuevalistaobservaciones);
                }else {
                    attr.addFlashAttribute("observacion",listaobservaciones[0]);
                    attr.addFlashAttribute("nuevalistaobservaciones",listaobservaciones);
                }
                Optional<InformeNuevo> optinformeNuevo = informeNuevoRepository.findById(iddelinformenuevo);
                InformeNuevo informeNuevo = optinformeNuevo.get();
                String listadecampitosString = informeNuevo.getCampos();
                List<String> listcampos = List.of(listadecampitosString.split(">%%%%%<%%%%>%%%%%<"));
                if(listcampos.size()> respuestasSeparadas.length){
                    String [] nuevalistarespuestas = new String[listcampos.size()];
                    for (int h=0;h<listcampos.size();h++){
                        if(h<respuestasSeparadas.length){
                            nuevalistarespuestas[h] = respuestasSeparadas[h];
                        }else {
                            nuevalistarespuestas[h] = "";
                        }
                    }
                    attr.addFlashAttribute("nuevaListarespuestas",nuevalistarespuestas);
                }else {
                    attr.addFlashAttribute("nuevaListarespuestas",respuestasSeparadas);
                }
                if(b==1){
                    int examenseleccionad = Integer.parseInt(examen);
                    attr.addFlashAttribute("seleccionado",examenseleccionad);
                    /*System.out.println("asadawdad: "+examenseleccionad);*/
                }
                Integer [] listanuevamedicamentos = new Integer[listamedicamentos.length];
                for (int j = 0; j<listamedicamentos.length;j++){
                    String uu = listamedicamentos[j];
                    if(!esNumeroEntero(uu)){
                       listanuevamedicamentos[j] = 1;
                    }else {
                        int medu = Integer.parseInt(uu);
                        if(0<medu && medu<=medilength){
                            listanuevamedicamentos[j] = medu;
                        }else {
                            listanuevamedicamentos[j] = 1;
                        }
                    }
                    /*System.out.println(listanuevamedicamentos[j]);*/
                }
                attr.addFlashAttribute("checkedd",checked);
                attr.addFlashAttribute("nuevalistamedicamentos",listanuevamedicamentos);
                attr.addFlashAttribute("comentario",comentario);
                attr.addFlashAttribute("diagnostico",diagnostico);
                attr.addFlashAttribute("tratamiento",tratamiento);
                attr.addFlashAttribute("error","Ha ocurrido un error en el llenado de los campos");
                attr.addFlashAttribute("listaerroresmedicamentos",listaerroresmedicamentos);
                attr.addFlashAttribute("listaerrorescantidades",listaerrorescantidades);
                attr.addFlashAttribute("listaerroresobservaciones",listaerroresobservaciones);
                /*for (String n : listaerrorescantidades){
                    System.out.println("ERROR CANTIDAD: ");
                    System.out.println(n);
                }
                for (String m : listaerroresmedicamentos){
                    System.out.println("ERROR MEDICAMENTO: ");
                    System.out.println(m);
                }
                for (String g : listaerroresobservaciones){
                    System.out.println("ERROR OBSERVACIONES: ");
                    System.out.println(g);
                }*/
                attr.addFlashAttribute("listanuevacantidades",nuevalistacantidad);
                /*System.out.println("LISTA NUEVA CANTIDADES: ");
                for (String u : nuevalistacantidad){
                    System.out.println(u);
                }*/
                return "redirect:/doctor/llenarInforme?idInforme="+iddelinformenuevo;
            }

        }else {
            attr.addFlashAttribute("error","La cita es incorrecta");
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
            System.out.println(listapreguntas);
            System.out.println(cue.getPreguntas());
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

    public String obtenerMesPorNumero(int numero){
        String mes = "";
        switch (numero) {
            case 1:
                mes = "Enero";
                break;
            case 2:
                mes = "Febrero";
                break;
            case 3:
                mes = "Marzo";
                break;
            case 4:
                mes = "Abril";
                break;
            case 5:
                mes = "Mayo";
                break;
            case 6:
                mes = "Junio";
                break;
            case 7:
                mes = "Julio";
                break;
            case 8:
                mes = "Agosto";
                break;
            case 9:
                mes = "Setiembre";
                break;
            case 10:
                mes = "Octubre";
                break;
            case 11:
                mes = "Noviembre";
                break;
            case 12:
                mes = "Diciembre";
                break;
            default:
                mes = "";
                break;
        }
        return mes;
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
    public String horasDoctor(@RequestParam(value = "mes",required = false)  String mes, @RequestParam(value = "dia",required = false) String dia,
                              @RequestParam(value = "horainicio",required = false) String horainicio, @RequestParam(value = "horafin",required = false) String horafin,
                              @RequestParam(value = "horalibre",required = false) String horalibre, HttpSession httpSession,RedirectAttributes attr, Model model) {

        LocalDate fechaActual1 = LocalDate.now();
        int mesActual = fechaActual1.getMonthValue();
        String mes1 = obtenerMesPorNumero(mesActual);
        model.addAttribute("mesActual",mes1);

        // Obtenemos si ya tiene horasDoctor en el mes
        Usuario doctor = (Usuario) httpSession.getAttribute("usuario");
        Optional<Horasdoctor> horasdoctorOptional = Optional.ofNullable(horasDoctorRepository.obtenerHorasDoctorEnMes(doctor.getId(), mes1));

        if (horasdoctorOptional.isPresent()){
            System.out.println(horasdoctorOptional.get().getId());
            attr.addFlashAttribute("msgError", "Lo sentimos, usted ya tiene un horario existente para este mes.");
            return "redirect:/doctor/calendario";
        }else {
            int hayError = 0;
            if (mes == null){
                hayError = 1;
                attr.addFlashAttribute("mesmsg", "Por favor ingrese un mes.");
            }
            if (dia == null || dia.equals("")){
                hayError = 1;
                attr.addFlashAttribute("diamsg", "Por favor seleccione los días de su horario.");
            }
            if (horainicio == null){
                hayError = 1;
                attr.addFlashAttribute("iniciomsg", "Por favor ingrese su hora de inicio.");
            }
            if (horafin == null){
                hayError = 1;
                attr.addFlashAttribute("finmsg", "Por favor ingrese su hora fin.");
            }
            if (horalibre == null){
                hayError = 1;
                attr.addFlashAttribute("libremsg", "Por favor ingrese su hora libre.");
            }

            if (hayError !=1){
                try {
                    LocalTime horaInicio = LocalTime.parse(horainicio);
                    LocalTime horaFin = LocalTime.parse(horafin);
                    LocalTime horaLibre = LocalTime.parse(horalibre);

                    Duration diferencia = Duration.between(horaInicio, horaFin); //horaFin-horaInicio
                    long horas = diferencia.toHours();
                    long minutos = diferencia.toMinutes() % 60;

                    // Validar que mes es un número
                    if (esNumeroEntero(mes)) {
                        // cambiamos el valor de mes al nombre del mes correspondiente
                        mes = obtenerMesPorNumero(Integer.parseInt(mes));

                        // validar que horainicio < horafinal
                        if (horas > 0 || horas==0 && minutos==30) {
                            if (horas >= 3){
                                // validar que horaInicio < horalibre < horafin, además horalibre != horainicio != horafin
                                if (horaLibre.isBefore(horaInicio) || horaLibre.equals(horaInicio) || horaLibre.isAfter(horaFin) || horaLibre.equals(horaFin)) {
                                    attr.addFlashAttribute("msgError", "Lo sentimos, la hora libre debe encontrarse entre la hora de Inicio y la hora de Fin, y debe ser diferente de ellas.");
                                    return "redirect:/doctor/calendario";
                                } else {
                                    // validar que horalibre + 1 = horafin
                                    Duration diferencia2 = Duration.between(horaLibre, horaFin); //horaFin-horaInicio
                                    long horas2 = diferencia2.toHours();
                                    if (horas2 > 1){
                                        horasDoctorRepository.guardarHorasDoc(horaInicio, horaFin, horaLibre, doctor.getId(), dia, mes);
                                        attr.addFlashAttribute("msg", "Horas de doctor se guardaron exitosamente.");
                                        return "redirect:/doctor/calendario";
                                    }else {
                                        attr.addFlashAttribute("msgError", "Lo sentimos, la hora libre debe tener 1 hora de diferencia como mínimo de la hora final");
                                        return "redirect:/doctor/calendario";
                                    }
                                }
                            }else {
                                attr.addFlashAttribute("msgError", "Lo sentimos, la hora de inicio y la hora final deben llevarse como mínimo 3 horas.");
                                return "redirect:/doctor/calendario";
                            }
                        } else {
                            attr.addFlashAttribute("msgError", "Lo sentimos, la hora final no puede ser antes de la hora inicial.");
                            return "redirect:/doctor/calendario";
                        }

                    } else {
                        attr.addFlashAttribute("msgError", "Lo sentimos hubo un problema en el mes ingresado, por favor volver a intentarlo.");
                        return "redirect:/doctor/calendario";
                    }

                } catch (DateTimeParseException e) {
                    attr.addFlashAttribute("msgError", "Lo sentimos hubo un problema en las horas puestas, por favor volver a intentarlo.");
                    return "redirect:/doctor/calendario";
                }
            }else {
                attr.addFlashAttribute("msgError", "Debe completar todos los campos.");
                return "redirect:/doctor/calendario";
            }
        }

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
