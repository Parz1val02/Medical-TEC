package com.example.medicaltec.controller;
import com.example.medicaltec.Entity.*;
import com.example.medicaltec.dto.FinanzasDto;
import com.example.medicaltec.funciones.GeneradorDeContrasenha;
import com.example.medicaltec.funciones.Regex;
import com.example.medicaltec.more.CorreoConEstilos;
import com.example.medicaltec.repository.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


@Controller
@RequestMapping("/administrador")
public class AdministradorController {


    /* Repositorys
    - usuarios (paciente y doctor)
    -
     */

    final CorreoConEstilos correoConEstilos;
    final EspecialidadeRepository especialidadeRepository;
    final UsuarioRepository usuarioRepository;
    final CitaRepository citaRepository;
    final AlergiaRepository alergiaRepository;
    final HistorialMedicoHasAlergiaRepository2 historialMedicoHasAlergiaRepository2;
    final FormInvitationRepository formInvitationRepository;

    final BoletaRepository boletaRepository;

    final InformeRepository informeRepository;

    final HistorialMedicoRepository historialMedicoRepository;

    final SeguroRepository seguroRepository;

    final SedeHasEspecialidadeRepository sedeHasEspecialidadeRepository;

    final EspecialidadRepository especialidadRepository;
    public AdministradorController (
            CitaRepository citaRepository,
            UsuarioRepository usuarioRepository,
            EspecialidadeRepository especialidadeRepository,
            AlergiaRepository alergiaRepository,
            HistorialMedicoHasAlergiaRepository2 historialMedicoHasAlergiaRepository2,
            FormInvitationRepository formInvitationRepository,
            CorreoConEstilos correoConEstilos,
            BoletaRepository boletaRepository,
            InformeRepository informeRepository,
            HistorialMedicoRepository historialMedicoRepository,
            SeguroRepository seguroRepository,
            SedeHasEspecialidadeRepository sedeHasEspecialidadeRepository,
            EspecialidadRepository especialidadRepository
            ) {
        this.usuarioRepository = usuarioRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.citaRepository = citaRepository;
        this.alergiaRepository = alergiaRepository;
        this.historialMedicoHasAlergiaRepository2 = historialMedicoHasAlergiaRepository2;
        this.formInvitationRepository = formInvitationRepository;
        this.correoConEstilos = correoConEstilos;
        this.boletaRepository = boletaRepository;
        this.informeRepository = informeRepository;
        this.historialMedicoRepository = historialMedicoRepository;
        this.seguroRepository = seguroRepository;
        this.sedeHasEspecialidadeRepository = sedeHasEspecialidadeRepository;
        this.especialidadRepository = especialidadRepository;
    }



    @GetMapping("/principal")
    public String pagprincipal(Model model, HttpServletRequest httpServletRequest){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List listaPacientes = usuarioRepository.obtenerListaPacientes2(usuarioSession.getSedesIdsedes().getId());
        List listaDoctores = usuarioRepository.obtenerlistaDoctoresAdmin(usuarioSession.getSedesIdsedes().getId());
        model.addAttribute("listaPacientes",listaPacientes);
        model.addAttribute("listaDoctores",listaDoctores);
        return "administrador/principal";
    }

    @GetMapping("/usuarios")
    public String pagusuarios(Model model, @ModelAttribute("usuario") Usuario usuario, HttpServletRequest httpServletRequest){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        //model.addAttribute("listaCitas",citaRepository.pacientesAtendidos());
        List<Especialidade> listaEspecialidades = especialidadeRepository.findAll();
        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes2(usuarioSession.getSedesIdsedes().getId());
        List<Usuario> listaDoctores = usuarioRepository.obtenerlistaDoctoresAdmin(usuarioSession.getSedesIdsedes().getId());
        model.addAttribute("listaEspecialidades",listaEspecialidades);
        model.addAttribute("listaPacientes",listaPacientes);
        model.addAttribute("listaDoctores",listaDoctores);


        /*ENVIAR CORREO CON CONTRASEÑA A MI MISMO PRUEBA
        String destinatario = usuarioSession.getEmail() ;
        String asunto = "Invitación a la plataforma de Medical-Tec";
        try {
            correoConEstilos.sendEmailUserCreation(destinatario, asunto, "PRUEBA DE CONTRASENA");
        } catch (MessagingException e) {
            // Manejar la excepción en caso de que ocurra un error al enviar el correo
            e.printStackTrace();
            return "redirect:/administrador/usuarios";
        }*/


        return "administrador/usuarios";
    }


    @GetMapping("/editarDoctorPagina")
    public String editarDoctorPagina(Model model, @ModelAttribute("doctor") Usuario doctor, @RequestParam("id") String dni, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        Optional<Usuario> optDoctor = usuarioRepository.findById(dni);
        if (optDoctor.isPresent()){
            doctor = optDoctor.get();
            //List<Especialidade> listaEspecialidades = especialidadeRepository.findAll();
            List<Integer> especialidadesxSedeId = sedeHasEspecialidadeRepository.listarEspecialidadesPorId(usuarioSession.getSedesIdsedes().getId());
            ArrayList<Especialidade> listaEspecialidades = new ArrayList<>();
            for(int i=0;i<especialidadesxSedeId.size();i++){
                listaEspecialidades.add(especialidadRepository.obtenerEspecialidadId(especialidadesxSedeId.get(i)));
            }
            model.addAttribute("listaEspecialidades",listaEspecialidades);
            model.addAttribute("doctor",doctor);
            model.addAttribute("idEspecialidadParaQueFuncioneSelected",doctor.getEspecialidadesIdEspecialidad().getId());
            httpServletRequest.getSession().setAttribute("idDelDoctorAEditar", dni);

            return "administrador/editarDoctor";
        } else {
            attr.addFlashAttribute("msgDanger","El doctor a editar no existe");
            return "redirect:/administrador/usuarios";
        }

    }

    @PostMapping("/editarDoctor")
    public String editarDoctor(
            @ModelAttribute("doctor") Usuario doctor,
            @RequestParam(value = "especialidad", required = false) String especialidad,
            RedirectAttributes attr,
            Model model,
            HttpServletRequest httpServletRequest

    ){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        //List<Especialidade> listaEspecialidades = especialidadeRepository.findAll();
        List<Integer> especialidadesxSedeId = sedeHasEspecialidadeRepository.listarEspecialidadesPorId(usuarioSession.getSedesIdsedes().getId());
        ArrayList<Especialidade> listaEspecialidades = new ArrayList<>();
        for(int i=0;i<especialidadesxSedeId.size();i++){
            listaEspecialidades.add(especialidadRepository.obtenerEspecialidadId(especialidadesxSedeId.get(i)));
        }

        Regex regex = new Regex();

        int a = 0;
        boolean existeDoctor = false;
        boolean dniNulo = false;
        if  ( doctor.getId()!=null && (!doctor.getId().isBlank())) {
            System.out.println("########################");
            System.out.println("########################");
            System.out.println(doctor.getId());
            System.out.println("########################");
            System.out.println("########################");
            try {
                int dniEntero = Integer.parseInt(doctor.getId());
                if (dniEntero<11111111 || dniEntero>99999999){
                    model.addAttribute("dnimsg","Deber ser un número de DNI válido de maximo 8 dígitos");
                    a = a + 1;
                } else {
                    List<Usuario> listaDoctores = usuarioRepository.findAll();

                    for (Usuario doctorLista : listaDoctores) {
                        if (doctor.getId().equalsIgnoreCase(doctorLista.getId()) ) {
                            existeDoctor = true;
                            break;
                        }
                    }
                }
            } catch (NumberFormatException e) {
                model.addAttribute("dnimsg","Deber ser un número de DNI válido de maximo 8 dígitos");
                a = a + 1;
            }
        } else {
            // model.addAttribute("dnimsg","El DNI no puede ser nulo");
            dniNulo = true;
        }

        boolean correoDuplicado = false;
        int contadorDuplicadosCorreo = 0;
        if  ( doctor.getEmail()!=null && (!doctor.getEmail().isBlank())) {
            System.out.println("########################");
            System.out.println("########################");
            System.out.println(doctor.getEmail());
            System.out.println("########################");
            System.out.println("########################");
            if ( doctor.getEmail().length() > 100  ||  (!regex.emailValid(doctor.getEmail()))) {
                model.addAttribute("emailmsg","El correo es de máximo 100 caracteres válidos. Es necesario el caracter @");
                a = a + 1;
            } else {
                if ( !dniNulo  && existeDoctor) {
                    List<Usuario> listaDoctores = usuarioRepository.findAll();
                    for (Usuario doctorLista : listaDoctores) {
                        if (doctor.getEmail().equals(doctorLista.getEmail()) ) {
                            if ( !doctor.getId().equals(doctorLista.getId())) {
                                contadorDuplicadosCorreo = contadorDuplicadosCorreo + 1;
                            }

                        }
                    }
                    if (contadorDuplicadosCorreo > 0) {
                        correoDuplicado = true;
                    }
                }
            }

        } else {
            model.addAttribute("emailmsg","El correo no puede ser nulo");
            a = a + 1;
        }


        //VALIDACION NOMBRE
        if(doctor.getNombre() == null || doctor.getNombre().isBlank()){
            model.addAttribute("nombremsg","El nombre no puede ser nulo");
            a = a+1;
        } else if (doctor.getNombre().length() > 45 ){  // || !regex.inputisValid(doctor.getNombre())
            model.addAttribute("nombremsg","El nombre debe ser un conjunto de caracteres valido de máximo 45 letras");
            a = a+1;
        }
        //VALIDACION NOMBRE

        //VALIDACION APELLIDO
        if(doctor.getApellido() == null || doctor.getApellido().isBlank()){
            model.addAttribute("apellidomsg","El apellido no puede ser nulo");
            a = a+1;
        } else if (doctor.getApellido().length() > 45 ){ //  || !regex.inputisValid(doctor.getApellido())
            model.addAttribute("apellidomsg","El apellido debe ser un conjunto de caracteres valido de máximo 45 letras");
            a = a+1;
        }
        //VALIDACION APELLIDO

        //VALIDACION DIRECCION
        if(doctor.getDireccion() == null || doctor.getDireccion().isBlank() ){
            model.addAttribute("direccionmsg","La dirección no puede ser nulo");
            a = a+1;
        } else if (doctor.getDireccion().length() > 200 ){
            model.addAttribute("direccionmsg","La dirección debe ser un conjunto de caracteres valido de máximo 45 letras");
            a = a+1;
        }
        //VALIDACION DIRECCION

        //VALIDACION TELEFONO
        if ( doctor.getTelefono() != null  && (!doctor.getTelefono().isBlank()) ) {
            try {
                int telefonoEntero = Integer.parseInt(doctor.getTelefono());
                if (telefonoEntero > 999999999 || telefonoEntero < 9999999 ) {
                    model.addAttribute("telefonomsg","El teléfono debe ser un número entero de máximo 9 digitos");
                    a = a + 1;
                }
            } catch (NumberFormatException e) {
                model.addAttribute("telefonomsg","El teléfono debe ser un número entero de máximo 9 digitos");
                a = a + 1;
            }
        } else {
            model.addAttribute("telefonomsg","El teléfono no puede ser nulo");
            a = a + 1;
        }
        //VALIDACION TELEFONO

        //VALIDACION ESPECIALIDAD
        if ( especialidad != null  && (!especialidad.isBlank()) ) {
            try {
                int especialidadEntero = Integer.parseInt(especialidad);
                if (especialidadEntero < 1 || (!especialidadesxSedeId.contains(especialidadEntero)) ) {
                    model.addAttribute("especialidadmsg","Especialidad no existente en la sede");
                    a = a + 1;
                }
            } catch (NumberFormatException e) {
                model.addAttribute("especialidadmsg","Valor de especialidad invalido");
                a = a + 1;
            }
        } else {
            model.addAttribute("especialidadmsg","La especialidad no puede ser nulo");
            a = a + 1;
        }
        //VALIDACION ESPECIALIDAD

        String idNecesario = (String) httpServletRequest.getSession().getAttribute("idDelDoctorAEditar");
        System.out.println(idNecesario);
        int idEspecialidadParaFuncionarElSelected = 0;
        Optional<Usuario> optDoctorParaSelectedDeEspecialidad = usuarioRepository.findById(idNecesario);
        if (optDoctorParaSelectedDeEspecialidad.isPresent()) {
            Usuario doctorParaSelectedDeEspecialidad  = optDoctorParaSelectedDeEspecialidad.get();
            idEspecialidadParaFuncionarElSelected = doctorParaSelectedDeEspecialidad.getEspecialidadesIdEspecialidad().getId();
        }
        System.out.println("especialidad entero:" +  idEspecialidadParaFuncionarElSelected);

        /*
        Usuario usuarioParaEnviarEstado = usuarioRepository.findByid(idNecesario);
        paciente.getEstadosIdestado().setNombre("");*/
        if ( !existeDoctor || dniNulo) {
            attr.addFlashAttribute("msgDanger","El DNI del doctor ingresado no existe o no puede ser nulo");
            String retorno = "redirect:/administrador/editarDoctorPagina?id="+ idNecesario;
            System.out.println("DNI NULO ");
            return retorno;
        } else if (correoDuplicado) {
            model.addAttribute("msgDanger","El correo del doctor ingresado ya existe");
            model.addAttribute("listaEspecialidades",listaEspecialidades);
            model.addAttribute("idEspecialidadParaQueFuncioneSelected", idEspecialidadParaFuncionarElSelected);
            System.out.println("EXISTECORREO");
            return "administrador/editarDoctor";
        } else if (a > 0) {
            System.out.println("ALGUN ERROR ESTA SALTANDO");
            model.addAttribute("listaEspecialidades",listaEspecialidades);
            model.addAttribute("idEspecialidadParaQueFuncioneSelected", idEspecialidadParaFuncionarElSelected);
            return "administrador/editarDoctor";
        } else {
            attr.addFlashAttribute("msg","Doctor actualizado exitosamente");
            usuarioRepository.editarDoctor( doctor.getEmail(),  doctor.getNombre(),  doctor.getApellido(),  doctor.getTelefono(), Integer.parseInt(especialidad), doctor.getDireccion() ,doctor.getId(), usuarioSession.getSedesIdsedes().getId() );
            return "redirect:/administrador/usuarios";
        }
    }

    @GetMapping("/crearDoctorPagina")
    public String crearDoctorPagina(Model model, @ModelAttribute("doctor") Usuario doctor, @ModelAttribute("especialidad") String especialidad, HttpServletRequest httpServletRequest){
        //List<Especialidade> listaEspecialidades = especialidadeRepository.findAll();
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Integer> especialidadesxSedeId = sedeHasEspecialidadeRepository.listarEspecialidadesPorId(usuarioSession.getSedesIdsedes().getId());
        ArrayList<Especialidade> listaEspecialidades = new ArrayList<>();
        for(int i=0;i<especialidadesxSedeId.size();i++){
            listaEspecialidades.add(especialidadRepository.obtenerEspecialidadId(especialidadesxSedeId.get(i)));
        }

        model.addAttribute("listaEspecialidades",listaEspecialidades);
        return "administrador/crearDoctor";
    }

    @PostMapping("/crearDoctor")
    public String crearDoctor(
            @ModelAttribute("doctor") Usuario doctor,
            @RequestParam(value = "especialidad", required = false) String especialidad,
            RedirectAttributes attr,
            Model model,
            HttpServletRequest httpServletRequest
    ){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");

        //List<Especialidade> listaEspecialidades = especialidadeRepository.findAll();
        List<Integer> especialidadesxSedeId = sedeHasEspecialidadeRepository.listarEspecialidadesPorId(usuarioSession.getSedesIdsedes().getId());
        ArrayList<Especialidade> listaEspecialidades = new ArrayList<>();
        for(int i=0;i<especialidadesxSedeId.size();i++){
            listaEspecialidades.add(especialidadRepository.obtenerEspecialidadId(especialidadesxSedeId.get(i)));
        }

        Regex regex = new Regex();

        int a = 0;
        //VALIDACION EXISTE PACIENTE
        boolean existeDoctor = false;
        if  ( doctor.getId()!=null && (!doctor.getId().isBlank())) {
            System.out.println("########################");
            System.out.println("########################");
            System.out.println(doctor.getId());
            System.out.println("########################");
            System.out.println("########################");
            try {
                int dniEntero = Integer.parseInt(doctor.getId());
                if ( (dniEntero<11111111) || (dniEntero>99999999)){
                    model.addAttribute("dnimsg","Deber ser un número de DNI válido de maximo 8 dígitos");
                    a = a + 1;
                } else {
                    List<Usuario> listaPacientes = usuarioRepository.findAll();
                    for (Usuario pacienteLista : listaPacientes) {
                        if (doctor.getId().equalsIgnoreCase(pacienteLista.getId()) ) {
                            existeDoctor = true;
                            break;
                        }
                    }
                }
            } catch (NumberFormatException e) {
                model.addAttribute("dnimsg","Deber ser un número de DNI válido de maximo 8 dígitos");
                a = a + 1;
            }
        } else {
            model.addAttribute("dnimsg","El DNI no puede ser nulo");
            a = a + 1;
        }
        //VALIDACION EXISTE PACIENTE

        //VALIDACION EXISTE CORREO
        boolean existeCorreo = false;
        if  ( doctor.getEmail()!=null && (!doctor.getEmail().isBlank())) {

            System.out.println("########################");
            System.out.println("########################");
            System.out.println(doctor.getEmail());
            System.out.println("########################");
            System.out.println("########################");
            if ( doctor.getEmail().length() > 100  ||  (!regex.emailValid(doctor.getEmail()))) {
                model.addAttribute("emailmsg","El correo es de máximo 100 caracteres válidos. Es necesario el caracter @");
                a = a + 1;
            } else {
                List<Usuario> listaPacientes = usuarioRepository.findAll();
                for (Usuario pacienteLista : listaPacientes) {
                    if (doctor.getEmail().equals(pacienteLista.getEmail()) ) {
                        existeCorreo=true;
                        break;
                    }
                }
            }
        } else {
            model.addAttribute("emailmsg","El correo no puede ser nulo");
            a = a + 1;
        }
        //VALIDACION EXISTE CORREO

        //VALIDACION NOMBRE
        if(doctor.getNombre() == null || doctor.getNombre().isBlank()){
            model.addAttribute("nombremsg","El nombre no puede ser nulo");
            a = a+1;
        } else if (doctor.getNombre().length() > 45 ){  // || !regex.inputisValid(doctor.getNombre())
            model.addAttribute("nombremsg","El nombre debe ser un conjunto de caracteres valido de máximo 45 letras");
            a = a+1;
        }
        //VALIDACION NOMBRE


        //VALIDACION APELLIDO
        if(doctor.getApellido() == null || doctor.getApellido().isBlank()){
            model.addAttribute("apellidomsg","El apellido no puede ser nulo");
            a = a+1;
        } else if (doctor.getApellido().length() > 45 ){   // || !regex.inputisValid(doctor.getApellido())
            model.addAttribute("apellidomsg","El apellido debe ser un conjunto de caracteres valido de máximo 45 letras");
            a = a+1;
        }
        //VALIDACION APELLIDO

        //VALIDACION DIRECCION
        if(doctor.getDireccion() == null || doctor.getDireccion().isBlank() ){
            model.addAttribute("direccionmsg","La dirección no puede ser nulo");
            a = a+1;
        } else if (doctor.getDireccion().length() > 200 ){
            model.addAttribute("direccionmsg","La dirección debe ser un conjunto de caracteres valido de máximo 45 letras");
            a = a+1;
        }
        //VALIDACION DIRECCION


        //VALIDACION SEXO
        if(doctor.getSexo() == null || doctor.getSexo().isBlank() ){
            model.addAttribute("sexomsg","El sexo del paciente no puede ser nulo");
            a = a+1;
        } else if ( !doctor.getSexo().equals("F") && !doctor.getSexo().equals("M") ){
            model.addAttribute("sexomsg","El sexo debe ser masculino o femenino");
            a = a+1;
        }
        System.out.println(doctor.getSexo());
        //VALIDACION SEXO


        //VALIDACION TELEFONO
        if ( doctor.getTelefono() != null  && (!doctor.getTelefono().isBlank()) ) {
            try {
                int telefonoEntero = Integer.parseInt(doctor.getTelefono());
                if (telefonoEntero > 999999999 || telefonoEntero < 9999999 ) {
                    model.addAttribute("telefonomsg","El teléfono debe ser un número entero de máximo 9 digitos");
                    a = a + 1;
                }
            } catch (NumberFormatException e) {
                model.addAttribute("telefonomsg","El teléfono debe ser un número entero de máximo 9 digitos");
                a = a + 1;
            }
        } else {
            model.addAttribute("telefonomsg","El teléfono no puede ser nulo");
            a = a + 1;
        }
        //VALIDACION TELEFONO


        //VALIDACION FECHA NACIMIENTO
        if (doctor.getFechaNacimiento()!=null && !doctor.getFechaNacimiento().isBlank()){
            System.out.println(doctor.getFechaNacimiento());
            //SimpleDateFormat formatoRecibido = new SimpleDateFormat("yyyy-MM-dd");
            // formatoDeseado = new SimpleDateFormat("dd-MM-yyyy");
            try {
                LocalDate fechaRecibidaDate = LocalDate.parse(doctor.getFechaNacimiento());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String fechaFormateada = fechaRecibidaDate.format(formatter);
                LocalDate fechaFormateadaDate = LocalDate.parse(fechaFormateada, formatter);
                LocalDate fechaActual = LocalDate.now();
                LocalDate fechaMayorEdad = fechaActual.minusYears(28);
                //copiar esto para crear paciente porque paciente aun usa solo Date y no LocalDate
                boolean esFechaValida = !fechaFormateadaDate.isAfter(fechaMayorEdad);
                if (!esFechaValida) {
                    model.addAttribute("nacimientomsg", "La fecha seleccionada debe corresponder con la mayoria de edad.");
                    a = a + 1;
                }
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                model.addAttribute("nacimientomsg","La informacion ingresada no es valida");
                a = a + 1;
            }
        } else {
            model.addAttribute("nacimientomsg","La fecha de nacimiento no puede ser nulo");
            a = a+1;
        }
        //VALIDACION FECHA NACIMIENTO

        //VALIDACION ESPECIALIDAD
        if ( especialidad != null  && (!especialidad.isBlank()) ) {
            try {
                int especialidadEntero = Integer.parseInt(especialidad);
                if (especialidadEntero < 1 || (!especialidadesxSedeId.contains(especialidadEntero)) ) {
                    model.addAttribute("especialidadmsg","Especialidad no existente en la sede");
                    a = a + 1;
                }
            } catch (NumberFormatException e) {
                model.addAttribute("especialidadmsg","Valor de especialidad invalido");
                a = a + 1;
            }
        } else {
            model.addAttribute("especialidadmsg","La especialidad no puede ser nulo");
            a = a + 1;
        }
        //VALIDACION ESPECIALIDAD

        if (existeDoctor) {
            model.addAttribute("msgDanger","El DNI del doctor ingresado ya existe");
            System.out.println(existeDoctor);
            model.addAttribute("listaEspecialidades",listaEspecialidades);
            return "administrador/crearDoctor";
        } else if (existeCorreo) {
            model.addAttribute("listaEspecialidades",listaEspecialidades);
            model.addAttribute("msgDanger","El correo del doctor ingresado ya existe");
            return "administrador/crearDoctor";
        } else if (existeDoctor && existeCorreo) {
            model.addAttribute("listaEspecialidades",listaEspecialidades);
            model.addAttribute("msgDanger","El DNI y correo del doctor ingresado ya existe");
            return "administrador/crearDoctor";
        } else if (a > 0) {
            model.addAttribute("listaEspecialidades",listaEspecialidades);
            return "administrador/crearDoctor";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate fechaRecibidaDate = LocalDate.parse(doctor.getFechaNacimiento());
            String fechaFormateada = fechaRecibidaDate.format(formatter);
            GeneradorDeContrasenha generadorDeContrasenha=new GeneradorDeContrasenha();
            String contrasena = generadorDeContrasenha.crearPassword();
            System.out.println("contrasena creada para doctor de id " + doctor.getId() + " es: " + contrasena);
            String contrasenaBCrypt = new BCryptPasswordEncoder().encode(contrasena);
            usuarioRepository.crearDoctor( doctor.getEmail(),  doctor.getNombre(),  doctor.getApellido(),  doctor.getTelefono(),  Integer.parseInt(especialidad),  doctor.getId(),  usuarioSession.getSedesIdsedes().getId(), fechaFormateada, doctor.getDireccion(), doctor.getSexo(), contrasenaBCrypt );

            /*
            CometChatApi cometChatApi = new CometChatApi();
            String dniAPI = doctor.getId();
            String nameAPI = doctor.getNombre() + " " + doctor.getApellido();
            try {
                cometChatApi.crearUsuarioCometChat(dniAPI,nameAPI);
            } catch (IOException | InterruptedException e) {
                System.out.println("ERROR EN LA CREACION DE USUARIO DOCTOR EN COMETCHAT. REVISAR ADMINISTRADOR CONTROLLER. METODO CREAR DOCTOR");
                throw new RuntimeException(e);
            }*/


            //ENVIAR CORREO CON CONTRASEÑA A PACIENTE RECIEN CREADO
            String destinatario = doctor.getEmail() ;
            String asunto = "Invitación a la plataforma de Medical-Tec";
            try {
                correoConEstilos.sendEmailUserCreation(destinatario, asunto, contrasena);
            } catch (MessagingException e) {
                // Manejar la excepción en caso de que ocurra un error al enviar el correo
                e.printStackTrace();
                return "redirect:/administrador/usuarios";
            }


            attr.addFlashAttribute("msg","Doctor creado exitosamente");
            return "redirect:/administrador/usuarios";
        }
    }


    @GetMapping("/editarPacientePagina")
    public String editarPacientePagina(Model model, @ModelAttribute("paciente") Usuario paciente, @RequestParam("id") String dni, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Optional<Usuario> optPaciente = usuarioRepository.findById(dni);
        if (optPaciente.isPresent()){
            paciente = optPaciente.get();
            model.addAttribute("paciente",paciente);
            httpServletRequest.getSession().setAttribute("idDelPacienteAEditar", dni);
            return "administrador/editarPacientePRUEBA";
        } else {
            attr.addFlashAttribute("msgDanger","El paciente a editar no existe");
            return "redirect:/administrador/usuarios";
        }
    }

    @PostMapping("/editarPaciente")
    public String editarPaciente(
            @ModelAttribute("paciente") Usuario paciente,
            RedirectAttributes attr,
            Model model,
            HttpServletRequest httpServletRequest
    ){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        Regex regex = new Regex();

        int a = 0;
        boolean existePaciente = false;
        boolean dniNulo = false;
        if  ( paciente.getId()!=null && (!paciente.getId().isBlank())) {
            System.out.println("########################");
            System.out.println("########################");
            System.out.println(paciente.getId());
            System.out.println("########################");
            System.out.println("########################");
            try {
                int dniEntero = Integer.parseInt(paciente.getId());
                if (dniEntero<11111111 || dniEntero>99999999){
                    model.addAttribute("dnimsg","Deber ser un número de DNI válido de maximo 8 dígitos");
                    a = a + 1;
                } else {
                    List<Usuario> listaPacientes = usuarioRepository.findAll();

                    for (Usuario pacienteLista : listaPacientes) {
                        if (paciente.getId().equalsIgnoreCase(pacienteLista.getId()) ) {
                            existePaciente = true;
                            break;
                        }
                    }
                }
            } catch (NumberFormatException e) {
                model.addAttribute("dnimsg","Deber ser un número de DNI válido de maximo 8 dígitos");
                a = a + 1;
            }
        } else {
           // model.addAttribute("dnimsg","El DNI no puede ser nulo");
            dniNulo = true;
        }

        boolean correoDuplicado = false;
        int contadorDuplicadosCorreo = 0;
        if  ( paciente.getEmail()!=null && (!paciente.getEmail().isBlank())) {
            System.out.println("########################");
            System.out.println("########################");
            System.out.println(paciente.getEmail());
            System.out.println("########################");
            System.out.println("########################");
            if ( paciente.getEmail().length() > 100  ||  (!regex.emailValid(paciente.getEmail()))) {
                model.addAttribute("emailmsg","El correo es de máximo 100 caracteres válidos. Es necesario el caracter @");
                a = a + 1;
            } else {
                if ( !dniNulo  && existePaciente) {
                    List<Usuario> listaPacientes = usuarioRepository.findAll();
                    for (Usuario pacienteLista : listaPacientes) {
                        if (paciente.getEmail().equals(pacienteLista.getEmail()) ) {
                            if ( !paciente.getId().equals(pacienteLista.getId())) {
                                contadorDuplicadosCorreo = contadorDuplicadosCorreo + 1;
                            }

                        }
                    }
                    if (contadorDuplicadosCorreo > 0) {
                        correoDuplicado = true;
                    }
                }
            }

        } else {
            model.addAttribute("emailmsg","El correo no puede ser nulo");
            a = a + 1;
        }


        //VALIDACION NOMBRE
        if(paciente.getNombre() == null || paciente.getNombre().isBlank()){
            model.addAttribute("nombremsg","El nombre no puede ser nulo");
            a = a+1;
        } else if (paciente.getNombre().length() > 45 ){ // || !regex.inputisValid(paciente.getNombre())
            model.addAttribute("nombremsg","El nombre debe ser un conjunto de caracteres valido de máximo 45 letras");
            a = a+1;
        }
        //VALIDACION NOMBRE

        //VALIDACION APELLIDO
        if(paciente.getApellido() == null || paciente.getApellido().isBlank()){
            model.addAttribute("apellidomsg","El apellido no puede ser nulo");
            a = a+1;
        } else if (paciente.getApellido().length() > 45 ){ //  || !regex.inputisValid(paciente.getApellido())
            model.addAttribute("apellidomsg","El apellido debe ser un conjunto de caracteres valido de máximo 45 letras");
            a = a+1;
        }
        //VALIDACION APELLIDO

        //VALIDACION DIRECCION
        if(paciente.getDireccion() == null || paciente.getDireccion().isBlank() ){
            model.addAttribute("direccionmsg","La dirección no puede ser nulo");
            a = a+1;
        } else if (paciente.getDireccion().length() > 200 ){
            model.addAttribute("direccionmsg","La dirección debe ser un conjunto de caracteres valido de máximo 45 letras");
            a = a+1;
        }
        //VALIDACION DIRECCION


        //VALIDACION TELEFONO
        if ( paciente.getTelefono() != null  && (!paciente.getTelefono().isBlank()) ) {
            try {
                int telefonoEntero = Integer.parseInt(paciente.getTelefono());
                if (telefonoEntero > 999999999 || telefonoEntero < 9999999 ) {
                    model.addAttribute("telefonomsg","El teléfono debe ser un número entero de máximo 9 digitos");
                    a = a + 1;
                }
            } catch (NumberFormatException e) {
                model.addAttribute("telefonomsg","El teléfono debe ser un número entero de máximo 9 digitos");
                a = a + 1;
            }
        } else {
            model.addAttribute("telefonomsg","El teléfono no puede ser nulo");
            a = a + 1;
        }
        //VALIDACION TELEFONO

        String idNecesario = (String) httpServletRequest.getSession().getAttribute("idDelPacienteAEditar");
        System.out.println(idNecesario);
        /*
        Usuario usuarioParaEnviarEstado = usuarioRepository.findByid(idNecesario);
        paciente.getEstadosIdestado().setNombre("");*/
        if ( !existePaciente || dniNulo) {
            attr.addFlashAttribute("msgDanger","El DNI del paciente ingresado no existe o no puede ser nulo");
            String retorno = "redirect:/administrador/editarPacientePagina?id="+ idNecesario;
            System.out.println("DNI NULO ");
            return retorno;
        } else if (correoDuplicado) {
            model.addAttribute("msgDanger","El correo del paciente ingresado ya existe");
            System.out.println("EXISTECORREO");
            return "administrador/editarPacientePRUEBA";
        } else if (a > 0) {
            System.out.println("ALGUN ERROR ESTA SALTANDO");
            return "administrador/editarPacientePRUEBA";
        } else {
            attr.addFlashAttribute("msg","Paciente actualizado exitosamente");
            usuarioRepository.editarPaciente( paciente.getEmail(),  paciente.getNombre(),  paciente.getApellido(),  paciente.getTelefono(), paciente.getDireccion() ,paciente.getId(), usuarioSession.getSedesIdsedes().getId() );
            return "redirect:/administrador/usuarios";
        }

    }

    @GetMapping("/crearPacientePagina")
    public String crearPacientePagina(Model model, @ModelAttribute("paciente") Usuario paciente,
                                      RedirectAttributes attr){
        return "administrador/crearPacientePRUEBA";
    }

    @PostMapping("/crearPaciente")
    public String crearPaciente(
            @ModelAttribute("paciente") Usuario paciente,
            RedirectAttributes attr,
            Model model,
            HttpServletRequest httpServletRequest

    ) {
        Regex regex = new Regex();
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        int a = 0;
        //VALIDACION EXISTE PACIENTE
        boolean existePaciente = false;
        if  ( paciente.getId()!=null && (!paciente.getId().isBlank())) {
            System.out.println("########################");
            System.out.println("########################");
            System.out.println(paciente.getId());
            System.out.println("########################");
            System.out.println("########################");
            try {
                int dniEntero = Integer.parseInt(paciente.getId());
                if ( (dniEntero<11111111) || (dniEntero>99999999)){
                    model.addAttribute("dnimsg","Deber ser un número de DNI válido de maximo 8 dígitos aaaa");
                    a = a + 1;
                } else {
                    List<Usuario> listaPacientes = usuarioRepository.findAll();
                    for (Usuario pacienteLista : listaPacientes) {
                        if (paciente.getId().equalsIgnoreCase(pacienteLista.getId()) ) {
                            existePaciente = true;
                            break;
                        }
                    }
                }
            } catch (NumberFormatException e) {
                model.addAttribute("dnimsg","Deber ser un número de DNI válido de maximo 8 dígitos xd");
                a = a + 1;
            }
        } else {
            model.addAttribute("dnimsg","El DNI no puede ser nulo");
            a = a + 1;
        }
        //VALIDACION EXISTE PACIENTE

        //VALIDACION EXISTE CORREO
        boolean existeCorreo = false;
        if  ( paciente.getEmail()!=null && (!paciente.getEmail().isBlank())) {

            System.out.println("########################");
            System.out.println("########################");
            System.out.println(paciente.getEmail());
            System.out.println("########################");
            System.out.println("########################");
            if ( paciente.getEmail().length() > 100  ||  (!regex.emailValid(paciente.getEmail()))) {
                model.addAttribute("emailmsg","El correo es de máximo 100 caracteres válidos. Es necesario el caracter @");
                a = a + 1;
            } else {
                List<Usuario> listaPacientes = usuarioRepository.findAll();
                for (Usuario pacienteLista : listaPacientes) {
                    if (paciente.getEmail().equals(pacienteLista.getEmail()) ) {
                        existeCorreo=true;
                        break;
                    }
                }
            }
        } else {
            model.addAttribute("emailmsg","El correo no puede ser nulo");
            a = a + 1;
        }
        //VALIDACION EXISTE CORREO

        //VALIDACION NOMBRE
        if(paciente.getNombre() == null || paciente.getNombre().isBlank()){
            model.addAttribute("nombremsg","El nombre no puede ser nulo");
            a = a+1;
        } else if (paciente.getNombre().length() > 45 ){  //   || !regex.inputisValid(paciente.getNombre())
            model.addAttribute("nombremsg","El nombre debe ser un conjunto de caracteres valido de máximo 45 letras.");
            a = a+1;
        }
        //VALIDACION NOMBRE


        //VALIDACION APELLIDO
        if(paciente.getApellido() == null || paciente.getApellido().isBlank()){
            model.addAttribute("apellidomsg","El apellido no puede ser nulo");
            a = a+1;
        } else if (paciente.getApellido().length() > 45 ){  //   || !regex.inputisValid(paciente.getApellido())
            model.addAttribute("apellidomsg","El apellido debe ser un conjunto de caracteres valido de máximo 45 letras");
            a = a+1;
        }
        //VALIDACION APELLIDO

        //VALIDACION DIRECCION
        if(paciente.getDireccion() == null || paciente.getDireccion().isBlank() ){
            model.addAttribute("direccionmsg","La dirección no puede ser nulo");
            a = a+1;
        } else if (paciente.getDireccion().length() > 200 ){
            model.addAttribute("direccionmsg","La dirección debe ser un conjunto de caracteres valido de máximo 45 letras");
            a = a+1;
        }
        //VALIDACION DIRECCION


        //VALIDACION SEXO
        if(paciente.getSexo() == null || paciente.getSexo().isBlank() ){
            model.addAttribute("sexomsg","El sexo del paciente no puede ser nulo");
            a = a+1;
        } else if ( !paciente.getSexo().equals("F") && !paciente.getSexo().equals("M") ){
            model.addAttribute("sexomsg","El sexo debe ser masculino o femenino");
            a = a+1;
        }
        System.out.println(paciente.getSexo());
        //VALIDACION SEXO


        //VALIDACION TELEFONO
        if ( paciente.getTelefono() != null  && (!paciente.getTelefono().isBlank()) ) {
            try {
                int telefonoEntero = Integer.parseInt(paciente.getTelefono());
                if (telefonoEntero > 999999999 || telefonoEntero < 9999999 ) {
                    model.addAttribute("telefonomsg","El teléfono debe ser un número entero de máximo 9 digitos");
                    a = a + 1;
                }
            } catch (NumberFormatException e) {
                model.addAttribute("telefonomsg","El teléfono debe ser un número entero de máximo 9 digitos");
                a = a + 1;
            }
        } else {
            model.addAttribute("telefonomsg","El teléfono no puede ser nulo");
            a = a + 1;
        }
        //VALIDACION TELEFONO


        //VALIDACION FECHA NACIMIENTO
        if (paciente.getFechaNacimiento()!=null && !paciente.getFechaNacimiento().isBlank()){
            System.out.println();
            SimpleDateFormat formatoRecibido = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date fechanacimientoDate = formatoRecibido.parse(paciente.getFechaNacimiento());
                java.sql.Date sqlDate = new java.sql.Date(fechanacimientoDate.getTime());
                System.out.println("Fecha Nacimiento exitoso SQL: " + sqlDate);
                Date fechaActual = new Date();
                if (fechanacimientoDate.after(fechaActual)) {
                    model.addAttribute("nacimientomsg", "La fecha seleccionada debe ser pasada o igual a la fecha actual.");
                    a = a + 1;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                model.addAttribute("nacimientomsg","La informacion ingresada no es valida");
                a = a + 1;
            }
        } else {
            model.addAttribute("nacimientomsg","La fecha de nacimiento no puede ser nulo");
            a = a+1;
        }
        //VALIDACION FECHA NACIMIENTO

        if (existePaciente) {
            model.addAttribute("msgDanger","El DNI del paciente ingresado ya existe");
            System.out.println(existePaciente);
            return "administrador/crearPacientePRUEBA";
        } else if (existeCorreo) {
            model.addAttribute("msgDanger","El correo del paciente ingresado ya existe");
            return "administrador/crearPacientePRUEBA";
        } else if (existePaciente && existeCorreo) {
            model.addAttribute("msgDanger","El DNI y correo del paciente ingresado ya existe");
            return "administrador/crearPacientePRUEBA";
        } else if (a > 0) {
            return "administrador/crearPacientePRUEBA";
        } else {
            GeneradorDeContrasenha generadorDeContrasenha=new GeneradorDeContrasenha();
            String contrasena = generadorDeContrasenha.crearPassword();
            System.out.println("contrasena creada para paciente de id " + paciente.getId() + " es: " + contrasena);
            String contrasenaBCrypt = new BCryptPasswordEncoder().encode(contrasena);

            //CREACION HISTORIAL MEDICO POR DEFECTO ASIGNADO A PACIENTE;
            Historialmedico historialmedicoDefecto = new Historialmedico();
            Optional<Seguro> seguroDefectoOpt = seguroRepository.findById(7);
            Seguro seguroDefecto = seguroDefectoOpt.get();
            historialmedicoDefecto.setValidahistorial(true);
            historialmedicoDefecto.setSegurosIdSeguro(seguroDefecto);
            Historialmedico historialmedicoGuardadoDefecto = historialMedicoRepository.save(historialmedicoDefecto);
            int idReciengenerado = historialmedicoGuardadoDefecto.getId();

            //CREAMOS PACIENTE CON SU NUEVO HISTORIAL MEDICO POR DEFECTO
            usuarioRepository.crearPaciente( paciente.getEmail(),  paciente.getNombre(),  paciente.getApellido(),  paciente.getTelefono(), paciente.getId(),  usuarioSession.getSedesIdsedes().getId(), paciente.getFechaNacimiento(), paciente.getDireccion() , paciente.getSexo(), contrasenaBCrypt,idReciengenerado,7,"invitado"); //seguro: sin seguro cuando lo creo yo

            /*
            CometChatApi cometChatApi = new CometChatApi();
            String dniAPI = paciente.getId();
            String nameAPI = paciente.getNombre() + " " + paciente.getApellido();
            try {
                cometChatApi.crearUsuarioCometChat(dniAPI,nameAPI);
            } catch (IOException | InterruptedException e) {
                System.out.println("ERROR EN LA CREACION DE USUARIO PACIENTE EN COMETCHAT. REVISAR ADMINISTRADOR CONTROLLER. METODO CREAR PACIENTE");
                throw new RuntimeException(e);
            }*/


            //ENVIAR CORREO CON CONTRASEÑA A PACIENTE RECIEN CREADO
            String destinatario = paciente.getEmail() ;
            String asunto = "Invitación a la plataforma de Medical-Tec";
            try {
                correoConEstilos.sendEmailUserCreation(destinatario, asunto, contrasena);
            } catch (MessagingException e) {
                // Manejar la excepción en caso de que ocurra un error al enviar el correo
                e.printStackTrace();
                return "redirect:/administrador/usuarios";
            }

            attr.addFlashAttribute("msg","Paciente creado exitosamente");
            return "redirect:/administrador/usuarios";
        }


    }

    @GetMapping("/historialPaciente")
    public String verHistorial(Model model, @RequestParam("id") String id){
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        Usuario usuario = optionalUsuario.get();
        model.addAttribute("paciente",usuario);

        // Nombre completo del sexo de la persona
        if(usuario.getSexo().equals("M")){
            usuario.setSexo("Masculino");
        }else if (usuario.getSexo().equals("F")){
            usuario.setSexo("Femenino");
        }

        if (usuario.getHistorialmedicoIdhistorialmedico()!= null ) {

            // Obtener alergias
            List<Integer> idAlergias = historialMedicoHasAlergiaRepository2.listarAlergiasPorId(usuario.getHistorialmedicoIdhistorialmedico().getId());
            ArrayList<Alergia> listaAlergias = new ArrayList<>();
            for (Integer idAlergia : idAlergias) {
                listaAlergias.add(alergiaRepository.obtenerAlergia(idAlergia));
            }
            model.addAttribute("listaAlergias",listaAlergias);

            // Obtener informes y citas por usuario
            model.addAttribute("informesPorUsuario",informeRepository.listarInformesPorPaciente(usuario.getId()));

            // List<Cita> listaCitasPorUsuario = citaRepository.citasPorUsuario(id);
            // model.addAttribute("listaCitasPorUsuario",listaCitasPorUsuario);

            return "administrador/historial";

        } else {

            model.addAttribute("msgSinHistorial","El usuario no tiene registros de historial clínico");

            //List<Cita> listaCitasPorUsuario = citaRepository.citasPorUsuario(id);
            //model.addAttribute("listaCitasPorUsuario",listaCitasPorUsuario);

            // Obtener informes y citas por usuario
            model.addAttribute("informesPorUsuario",informeRepository.listarInformesPorPaciente(usuario.getId()));

            return "administrador/historial";
        }

    }



    @GetMapping("/calendario")
    public String calendario(HttpServletRequest httpServletRequest){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        return "administrador/calendario";
    }

    @GetMapping("/finanzas")
    public String finanzas(Model model){
        model.addAttribute("listaBoletasDto", boletaRepository.tablaFinanzasPrincipal());
        return "administrador/finanzas";
    }

    @PostMapping("/filtrarFinanzas")
    public String filtrarFinanzas(Model model,
                                  @RequestParam("tipoFiltro") String tipoFiltro,
                                  @RequestParam("filtrado") String filtrado,
                                  RedirectAttributes attr){

        //validamos segun el filtro padre : tipoFiltro
        if (tipoFiltro!=null && !tipoFiltro.isBlank()) {
            try {
                int filtradoValidado = Integer.parseInt(filtrado);
                System.out.println(filtradoValidado);
                System.out.println(Integer.valueOf(filtradoValidado).getClass());
                switch (tipoFiltro){
                    case "1":
                        if ( filtradoValidado>=1 && filtradoValidado<=19) {
                            model.addAttribute("listaBoletasDto", boletaRepository.tablaFinanzasEspecialidad(filtradoValidado));
                            return "administrador/finanzas";
                        } else {
                            attr.addFlashAttribute("msgError","Seleccione un filtrado valido.");
                            return "redirect:/administrador/finanzas";
                        }
                    case "2":
                        if ( filtradoValidado>=1 && filtradoValidado<=7) {
                            model.addAttribute("listaBoletasDto", boletaRepository.tablaFinanzasSeguro(filtradoValidado));
                            return "administrador/finanzas";
                        } else {
                            attr.addFlashAttribute("msgError","Seleccione un filtrado valido.");
                            return "redirect:/administrador/finanzas";
                        }
                    case "3":
                        if ( filtradoValidado>=1 && filtradoValidado<=12) {
                            List<FinanzasDto> tablaFinanzasPorMes = new ArrayList<>();
                            List<FinanzasDto> tablaFinanzasPrincipal = boletaRepository.tablaFinanzasPrincipal();
                            for ( FinanzasDto boleta : tablaFinanzasPrincipal  ) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                LocalDate fechaLocalDate = LocalDate.parse(boleta.getFecha(), formatter);
                                int mes = fechaLocalDate.getMonthValue();
                                if (mes == filtradoValidado) {
                                    tablaFinanzasPorMes.add(boleta);
                                }
                            }
                            model.addAttribute("listaBoletasDto", tablaFinanzasPorMes);
                            return "administrador/finanzas";
                        } else {
                            attr.addFlashAttribute("msgError","Seleccione un filtrado valido.");
                            return "redirect:/administrador/finanzas";
                        }
                    case "0":
                    default:
                        attr.addFlashAttribute("msgError","Seleccione un tipo de filtro valido.");
                        return "redirect:/administrador/finanzas";
                }
            } catch (NumberFormatException e) {
                attr.addFlashAttribute("msgError","Seleccione un filtrado valido.");
                return "redirect:/administrador/finanzas";
            }
        } else {
            attr.addFlashAttribute("msgError","Seleccione un tipo de filtro valido.");
            return "redirect:/administrador/finanzas";
        }



    }


    @GetMapping("/mensajeria")
    public String mensajeria(){

        return "administrador/mensajeria";
    }


    @GetMapping("/formatos")
    public String formatos(){

        return "administrador/formatos";
    }

    @GetMapping("/notificaciones")
    public String notificaciones(){

        return "administrador/notificaciones";
    }

    @GetMapping("/settings")
    public String settings(Model model, HttpServletRequest httpServletRequest){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        model.addAttribute("admin",usuarioSession);
        return "administrador/settings";

         /*
        String dni = "71448628";
        Optional<Usuario> administrador = usuarioRepository.findById(dni);
        if (administrador.isPresent()){
            Usuario admin = administrador.get();
            model.addAttribute("admin",admin);
            return "administrador/settings";
        } else {
            return "redirect:/administrador/principal";
        }
        */

    }

    @GetMapping("/password")
    public String changePassword(){
        return "administrador/password";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("pass1") String pass1,
                                 @RequestParam("pass2") String pass2,
                                 @RequestParam("pass3") String pass3, RedirectAttributes attr, HttpServletRequest httpServletRequest)
    {
        Regex regex = new Regex();
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        String passwordAntiguabCrypt = usuarioRepository.buscarPasswordPropioUsuario(usuarioSession.getId());
        //String pass1BCrypt = new BCryptPasswordEncoder().encode(pass1);
        System.out.println("#####################################");
        System.out.println("#####################################");
        System.out.println("#####################################");
        //System.out.println(pass1BCrypt);
        System.out.println("#####################################");
        System.out.println("#####################################");
        System.out.println("#####################################");
        boolean passwordActualCoincide = BCrypt.checkpw(pass1, passwordAntiguabCrypt);
        if(pass1.equals("") || pass2.equals("") || pass3.equals("")){
            attr.addFlashAttribute("errorPass", "Los campos no pueden estar vacios");
            return "redirect:/administrador/password";
            //}else if(!pass1.equals(usuarioRepository.passAdmv())){
            //    attr.addFlashAttribute("errorPass", "La contraseña actual no coincide");
            //    return "redirect:/paciente/password";
        } else if (!passwordActualCoincide ) {
            attr.addFlashAttribute("errorPass", "Ocurrió un error durante el cambio de contraseña. No se aplicaron cambios.");
            return "redirect:/administrador/password";
        } else if (!pass3.equals(pass2) ) {
            attr.addFlashAttribute("errorPass", "Las nuevas contraseñas no coinciden");
            return "redirect:/administrador/password";
        } else if(!regex.contrasenaisValid(pass2)) {
            attr.addFlashAttribute("errorPass", "La nueva contraseña no coincide con los requerimientos.");
            return "redirect:/administrador/password";
        } else {
            usuarioRepository.cambiarContra(new BCryptPasswordEncoder().encode(pass3), usuarioSession.getId());
            attr.addFlashAttribute("msgContrasenia","Su contraseña ha sido cambiada exitosamente");
            return "redirect:/administrador/settings";
        }

    }

    @GetMapping("/listaFormulariosRegistro")
    public String listaFormulariosRegistro(Model model, HttpServletRequest httpServletRequest){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<FormInvitacion> listaFormulariosRegistroPorInvitar = formInvitationRepository.findFormbySede(usuarioSession.getSedesIdsedes().getId());
        model.addAttribute("listaFormulariosRegistro",listaFormulariosRegistroPorInvitar);
        return "administrador/listaFormulariosRegistro";
    }

    @PostMapping("/guardarFormulariosRegistro")
    public String guardarFormulariosRegistro(@RequestParam(value = "seleccionados", required = false) List<String> idsSeleccionados, RedirectAttributes attr,HttpServletRequest httpServletRequest){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");

        if (idsSeleccionados == null)  {
            attr.addFlashAttribute("msgDanger","No se ha seleccionado ningún formulario");
            return "redirect:/administrador/listaFormulariosRegistro";
        } else {
            for (String idStr : idsSeleccionados) {
                // Obtienes el registro por ID desde tu servicio o repositorio
                Integer idInt = Integer.parseInt(idStr);
                Optional<FormInvitacion> formInvitacionOptional = formInvitationRepository.findById(idInt);


                // Realizas las operaciones necesarias para guardar el registro en la base de datos
                // ...
                GeneradorDeContrasenha generadorDeContrasenha=new GeneradorDeContrasenha();
                String contrasena = generadorDeContrasenha.crearPassword(); //contrasena del usuario sin hash
                String contrasenaBCrypt = new BCryptPasswordEncoder().encode(contrasena);

                //Linea comentada por el cambio de edad a fechanacimiento en forminvitacion
                //usuarioRepository.crearPaciente( formInvitacionOptional.get().getCorreo(), formInvitacionOptional.get().getNombres(),  formInvitacionOptional.get().getApellidos(),  formInvitacionOptional.get().getCelular(), formInvitacionOptional.get().getDni(),  Integer.parseInt(formInvitacionOptional.get().getIdSede()), Integer.parseInt(formInvitacionOptional.get().getEdad()), formInvitacionOptional.get().getDomicilio() , formInvitacionOptional.get().getSexo(), contrasenaBCrypt );

                //CREACION HISTORIAL MEDICO POR DEFECTO
                Historialmedico historialmedicoDefecto = new Historialmedico();
                Optional<Seguro> seguroDefectoOpt = seguroRepository.findById(Integer.parseInt(formInvitacionOptional.get().getIdSeguro()));
                Seguro seguroDefecto = seguroDefectoOpt.get();
                historialmedicoDefecto.setValidahistorial(true);
                historialmedicoDefecto.setSegurosIdSeguro(seguroDefecto);
                Historialmedico historialmedicoGuardadoDefecto = historialMedicoRepository.save(historialmedicoDefecto);
                int idReciengenerado = historialmedicoGuardadoDefecto.getId();
                //CREAMOS PACIENTE CON SU NUEVO HISTORIAL MEDICO POR DEFECTO
                usuarioRepository.crearPaciente( formInvitacionOptional.get().getCorreo(),  formInvitacionOptional.get().getNombres(),   formInvitacionOptional.get().getApellidos(),  formInvitacionOptional.get().getCelular(), formInvitacionOptional.get().getDni(),  usuarioSession.getSedesIdsedes().getId(), formInvitacionOptional.get().getFechanacimiento(), formInvitacionOptional.get().getDomicilio(), formInvitacionOptional.get().getSexo(), contrasenaBCrypt,idReciengenerado,seguroDefecto.getId(),"invitado"); //
                //Ahora ya no hay edad en formInvitacion, se maneja fechanacimiento


                // Actualizar el estado de pendiente a 0. ya no es pendiente.
                formInvitationRepository.actualizarEstadoFormRegistroRevisado(idInt);

                //ENVIAR CORREO CON CONTRASEÑA
                String destinatario = formInvitacionOptional.get().getCorreo() ;
                String asunto = "Invitación a la plataforma de Medical-Tec";
                try {
                    correoConEstilos.sendEmailUserCreation(destinatario, asunto, contrasena);
                } catch (MessagingException e) {
                    // Manejar la excepción en caso de que ocurra un error al enviar el correo
                    e.printStackTrace();
                    return "redirect:/administrador/listaFormulariosRegistro";
                }


            }
            attr.addFlashAttribute("msg","Pacientes invitados exitosamente");
            return "redirect:/administrador/listaFormulariosRegistro";
        }



    }




}
