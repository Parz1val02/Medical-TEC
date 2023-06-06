package com.example.medicaltec.controller;
import com.example.medicaltec.Entity.*;
import com.example.medicaltec.funciones.GeneradorDeContrasenha;
import com.example.medicaltec.funciones.Regex;
import com.example.medicaltec.more.CorreoConEstilos;
import com.example.medicaltec.more.EmailSenderService;
import com.example.medicaltec.repository.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.*;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
    public AdministradorController (
            CitaRepository citaRepository,
            UsuarioRepository usuarioRepository,
            EspecialidadeRepository especialidadeRepository,
            AlergiaRepository alergiaRepository,
            HistorialMedicoHasAlergiaRepository2 historialMedicoHasAlergiaRepository2,
            FormInvitationRepository formInvitationRepository,
            CorreoConEstilos correoConEstilos
            ) {
        this.usuarioRepository = usuarioRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.citaRepository = citaRepository;
        this.alergiaRepository = alergiaRepository;
        this.historialMedicoHasAlergiaRepository2 = historialMedicoHasAlergiaRepository2;
        this.formInvitationRepository = formInvitationRepository;
        this.correoConEstilos = correoConEstilos;
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
        return "administrador/usuarios";
    }


    /*
    @PostMapping("/editarDoctor")
    public String editarDoctor(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String dni,
            @RequestParam("apellido") String apellido,
            @RequestParam("especialidad") int especialidad,
            @RequestParam("telefono") String telefono,
            RedirectAttributes attr

    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Doctor actualizado exitosamente");
        usuarioRepository.editarDoctor( email,  nombre,  apellido,  telefono,  especialidad,  dni,  sede );
        return "redirect:/administrador/usuarios";
    }*/




    @GetMapping("/editarDoctorPagina")
    public String editarDoctorPagina(Model model, @ModelAttribute("doctor") Usuario doctor, @RequestParam("id") String dni, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Optional<Usuario> optDoctor = usuarioRepository.findById(dni);
        if (optDoctor.isPresent()){
            doctor = optDoctor.get();
            List<Especialidade> listaEspecialidades = especialidadeRepository.findAll();
            model.addAttribute("listaEspecialidades",listaEspecialidades);
            model.addAttribute("doctor",doctor);
            return "administrador/editarDoctor";
        } else {
            attr.addFlashAttribute("msgDanger","El doctor a editar no existe");
            return "redirect:/administrador/usuarios";
        }

    }

    @PostMapping("/editarDoctor")
    public String editarDoctor(
            @ModelAttribute("doctor") @Valid Usuario doctor,
            BindingResult bindingResult,
            RedirectAttributes attr,
            Model model,
            HttpServletRequest httpServletRequest

    ){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        System.out.println(bindingResult.getAllErrors());
        if(bindingResult.hasErrors()){
            List<Especialidade> listaEspecialidades = especialidadeRepository.findAll();
            model.addAttribute("listaEspecialidades",listaEspecialidades);
            return "administrador/editarDoctor";
        } else {
            attr.addFlashAttribute("msg","Doctor actualizado exitosamente");
            usuarioRepository.editarDoctor( doctor.getEmail(),  doctor.getNombre(), doctor.getApellido(),  doctor.getTelefono(),  doctor.getEspecialidadesIdEspecialidad().getId(),  doctor.getId(),  usuarioSession.getSedesIdsedes().getId());
            return "redirect:/administrador/usuarios";
        }


    }

    /*
    @PostMapping("/crearDoctor")
    public String crearDoctor(
            @RequestParam("sede") int sede,
            @RequestParam("edad") int edad,
            @RequestParam("direccion") String direccion,
            @RequestParam("sexo") String sexo,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String dni,
            @RequestParam("apellido") String apellido,
            @RequestParam("especialidad") int especialidad,
            @RequestParam("telefono") String telefono,
            RedirectAttributes attr

    ){
        List<Usuario> listaDoctores = usuarioRepository.obtenerlistaDoctores();
        System.out.println(nombre);
        boolean existeDoctor = false;
        for (Usuario doctor : listaDoctores) {
            if (dni.equalsIgnoreCase(doctor.getId()) ) {
                existeDoctor = true;
            }
        }


        if ( existeDoctor) {
            attr.addFlashAttribute("msgDanger","El doctor ingresado ya existe");
            return "redirect:/administrador/usuarios";
        } else {
            GeneradorDeContrasenha generadorDeContrasenha=new GeneradorDeContrasenha();
            String contrasena = generadorDeContrasenha.crearPassword();
            usuarioRepository.crearDoctor( email,  nombre,  apellido,  telefono,  especialidad,  dni,  sede, edad, direccion, sexo, contrasena );
            attr.addFlashAttribute("msg","Doctor creado exitosamente");
            return "redirect:/administrador/usuarios";
        }
    }*/


    @GetMapping("/crearDoctorPagina")
    public String crearDoctorPagina(Model model, @ModelAttribute("doctor") Usuario doctor, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        List<Especialidade> listaEspecialidades = especialidadeRepository.findAll();
        model.addAttribute("listaEspecialidades",listaEspecialidades);
        return "administrador/crearDoctor";
    }

    @PostMapping("/crearDoctor")
    public String crearDoctor(
            @ModelAttribute("doctor") @Valid Usuario doctor,
            BindingResult bindingResult,
            RedirectAttributes attr,
            Model model,
            HttpServletRequest httpServletRequest

    ){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        /*
        int a = 0;
        if(doctor.getNombre().isEmpty()){
            attr.addFlashAttribute("nombremsg","El nombre no puede ser nulo");
            a = a+1;
        }
        if(doctor.getApellido().isEmpty()){
            attr.addFlashAttribute("apellidomsg","El apellido no puede ser nulo");
            a = a+1;
        }
        if (doctor.getEmail().isEmpty()){
            attr.addFlashAttribute("correomsg","El correo no puede ser nulo");
            a = a+1;
        }
        if (doctor.getEdad() == null){
            attr.addFlashAttribute("edadmsg","La edad no puede ser nula");
            a = a+1;
        } else {
            if (doctor.getEdad()  <0){
                attr.addFlashAttribute("edadmsg","La edad no puede ser negativa");
                a = a+1;
            }
        }
        if (doctor.getTelefono().isEmpty()){
            attr.addFlashAttribute("telefonomsg","El teléfono no puede ser nulo");
            a = a+1;
        }
        if (doctor.getTelefono().length()!=9){
            attr.addFlashAttribute("telefonomsg", "El número de teléfono debe tener 9 dígitos");
            a = a+1;
        }
        if(doctor.getDireccion().isEmpty()) {
            attr.addFlashAttribute("addressmsg","La dirección no puede ser nula");
            a = a+1;
        }
        if(doctor.getId().isEmpty()){
            attr.addFlashAttribute("dnimsg","El DNI no puede ser nulo");
            a = a+1;
        } else if (doctor.getId().length()!=8) {
            attr.addFlashAttribute("dnimsg","El DNI tiene que tener 8 dígitos");
            a = a+1;
        } else {
            Optional<Usuario> u = usuarioRepository.findById(doctor.getId());
            if(u.isPresent()){
                attr.addFlashAttribute("dnimsg","El DNI ya se encuentra registrado.");
                a = a+1;
            }
        }
        if(a == 0){
            GeneradorDeContrasenha generadorDeContrasenha=new GeneradorDeContrasenha();
            String contrasena = generadorDeContrasenha.crearPassword();
            usuarioRepository.crearDoctor( paciente.getEmail(),  paciente.getNombre(),  paciente.getApellido(),  paciente.getTelefono(), paciente.getId(),  paciente.getSedesIdsedes().getId(), paciente.getEdad(), paciente.getDireccion() , paciente.getSexo(), contrasena );
            attr.addFlashAttribute("msg","doctor creado exitosamente");
            return "redirect:/administrador/usuarios";
        }else {
            attr.addFlashAttribute("msg1","Hubieron errores en el llenado de los campos");
            return "administrador/crearPaciente";
        }*/
        int a = 0;
        if (doctor.getEdad()!=null){
            if (doctor.getEdad()<0) {
                model.addAttribute("edadmsg","La edad debe ser un numero entero positivo");
                a = a+1;
            }


        }

        try {
            int dniEntero = Integer.parseInt(doctor.getId());
            if (dniEntero<0){
                model.addAttribute("dnimsg","Deber ser un numero de DNI valido de maximo 8 digitos");
            }
        } catch (NumberFormatException e) {
            model.addAttribute("dnimsg","Deber ser un numero de DNI valido de maximo 8 digitos");
            a = a + 1;
        }


        try {
            int telefonoEntero = Integer.parseInt(doctor.getTelefono());
        } catch (NumberFormatException e) {
            model.addAttribute("telefonomsg","El telefono debe ser un numero entero de máximo 9 digitos");
            a = a + 1;
        }


        List<Usuario> listaDoctores = usuarioRepository.obtenerlistaDoctoresAdmin(usuarioSession.getSedesIdsedes().getId());
        boolean existeDoctor = false;
        for (Usuario doctorLista : listaDoctores) {
            if (doctor.getId().equalsIgnoreCase(doctorLista.getId()) ) {
                existeDoctor = true;
            }
        }

        if ( existeDoctor && bindingResult.hasErrors()) {
            attr.addFlashAttribute("msgDanger","El doctor ingresado ya existe");
            List<Especialidade> listaEspecialidades = especialidadeRepository.findAll();
            model.addAttribute("listaEspecialidades",listaEspecialidades);
            return "administrador/crearDoctor";
        } else if (bindingResult.hasErrors()) {
            List<Especialidade> listaEspecialidades = especialidadeRepository.findAll();
            model.addAttribute("listaEspecialidades",listaEspecialidades);
            return "administrador/crearDoctor";
        } else {
            GeneradorDeContrasenha generadorDeContrasenha=new GeneradorDeContrasenha();
            String contrasena = generadorDeContrasenha.crearPassword();
            String contrasenaBCrypt = new BCryptPasswordEncoder().encode(contrasena);
            usuarioRepository.crearDoctor( doctor.getEmail(),  doctor.getNombre(),  doctor.getApellido(),  doctor.getTelefono(),  doctor.getEspecialidadesIdEspecialidad().getId(),  doctor.getId(),  usuarioSession.getSedesIdsedes().getId(), doctor.getEdad(), doctor.getDireccion(), doctor.getSexo(), contrasenaBCrypt );
            attr.addFlashAttribute("msg","Doctor creado exitosamente");
            return "redirect:/administrador/usuarios";
        }
    }




    /*
    @PostMapping("/editarPaciente")
    public String editarPaciente(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String dni,
            @RequestParam("apellido") String apellido,
            @RequestParam("telefono") String telefono,
            RedirectAttributes attr

    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Paciente actualizado exitosamente");
        usuarioRepository.editarPaciente( email,  nombre,  apellido,  telefono, dni,  sede );
        return "redirect:/administrador/usuarios";
    }
    */

    @GetMapping("/editarPacientePagina")
    public String editarPacientePagina(Model model, @ModelAttribute("paciente") Usuario paciente, @RequestParam("id") String dni, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Optional<Usuario> optPaciente = usuarioRepository.findById(dni);
        if (optPaciente.isPresent()){
            paciente = optPaciente.get();
            model.addAttribute("paciente",paciente);
            return "administrador/editarPaciente";
        } else {
            attr.addFlashAttribute("msgDanger","El paciente a editar no existe");
            return "redirect:/administrador/usuarios";
        }
    }

    @PostMapping("/editarPaciente")
    public String editarPaciente(
           @ModelAttribute("paciente") @Valid Usuario paciente,
            BindingResult bindingResult,
            RedirectAttributes attr,
            Model model,
            HttpServletRequest httpServletRequest
    ){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(bindingResult.hasErrors()){
            return "administrador/editarPaciente";
        } else {
            attr.addFlashAttribute("msg","Paciente actualizado exitosamente");
            usuarioRepository.editarPaciente( paciente.getEmail(),  paciente.getNombre(),  paciente.getApellido(),  paciente.getTelefono(), paciente.getId(), usuarioSession.getSedesIdsedes().getId() );
            return "redirect:/administrador/usuarios";
        }
    }

    /*
    @PostMapping("/crearPaciente")
    public String crearPaciente(
            @RequestParam("sede") int sede,
            @RequestParam("edad") int edad,
            @RequestParam("direccion") String direccion,
            @RequestParam("sexo") String sexo,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String dni,
            @RequestParam("apellido") String apellido,
            @RequestParam("telefono") String telefono,
            RedirectAttributes attr

    ){
        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        System.out.println(nombre);
        boolean existePaciente = false;
        for (Usuario paciente : listaPacientes) {
            if (dni.equalsIgnoreCase(paciente.getId()) ) {
                existePaciente = true;
            }
        }


        if ( existePaciente) {
            attr.addFlashAttribute("msgDanger","El paciente ingresado ya existe");
            return "redirect:/administrador/usuarios";
        } else {
            GeneradorDeContrasenha generadorDeContrasenha=new GeneradorDeContrasenha();
            String contrasena = generadorDeContrasenha.crearPassword();
            usuarioRepository.crearPaciente( email,  nombre,  apellido,  telefono, dni,  sede, edad, direccion, sexo, contrasena );
            attr.addFlashAttribute("msg","Paciente creado exitosamente");
            return "redirect:/administrador/usuarios";
        }
    }
    */

    @GetMapping("/crearPacientePagina")
    public String crearPacientePagina(Model model, @ModelAttribute("paciente") Usuario paciente, RedirectAttributes attr){
        return "administrador/crearPacientePRUEBA";
    }


    /*
    @PostMapping("/crearPaciente")
    public String crearPaciente(
            @ModelAttribute("paciente") @Valid Usuario paciente,
            BindingResult bindingResult,
            RedirectAttributes attr,
            Model model,
            HttpServletRequest httpServletRequest

    ){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");

        int a = 0;
        if(paciente.getNombre().isEmpty()){
            attr.addFlashAttribute("nombremsg","El nombre no puede ser nulo");
            a = a+1;
        }
        if(paciente.getApellido().isEmpty()){
            attr.addFlashAttribute("apellidomsg","El apellido no puede ser nulo");
            a = a+1;
        }
        if (paciente.getEmail().isEmpty()){
            attr.addFlashAttribute("correomsg","El correo no puede ser nulo");
            a = a+1;
        }
        if (paciente.getEdad() == null){
            attr.addFlashAttribute("edadmsg","La edad no puede ser nula");
            a = a+1;
        } else {
            if (paciente.getEdad()  <0){
                attr.addFlashAttribute("edadmsg","La edad no puede ser negativa");
                a = a+1;
            }
        }
        if (paciente.getTelefono().isEmpty()){
            attr.addFlashAttribute("telefonomsg","El teléfono no puede ser nulo");
            a = a+1;
        }
        if (paciente.getTelefono().length()!=9){
            attr.addFlashAttribute("telefonomsg", "El número de teléfono debe tener 9 dígitos");
            a = a+1;
        }
        if(paciente.getDireccion().isEmpty()) {
            attr.addFlashAttribute("addressmsg","La dirección no puede ser nula");
            a = a+1;
        }
        if(paciente.getId().isEmpty()){
            attr.addFlashAttribute("dnimsg","El DNI no puede ser nulo");
            a = a+1;
        } else if (paciente.getId().length()!=8) {
            attr.addFlashAttribute("dnimsg","El DNI tiene que tener 8 dígitos");
            a = a+1;
        } else {
            Optional<Usuario> u = usuarioRepository.findById(paciente.getId());
            if(u.isPresent()){
                attr.addFlashAttribute("dnimsg","El DNI ya se encuentra registrado.");
                a = a+1;
            }
        }
        if(a == 0){
            GeneradorDeContrasenha generadorDeContrasenha=new GeneradorDeContrasenha();
            String contrasena = generadorDeContrasenha.crearPassword();
            usuarioRepository.crearPaciente( paciente.getEmail(),  paciente.getNombre(),  paciente.getApellido(),  paciente.getTelefono(), paciente.getId(),  paciente.getSedesIdsedes().getId(), paciente.getEdad(), paciente.getDireccion() , paciente.getSexo(), contrasena );
            attr.addFlashAttribute("msg","Paciente creado exitosamente");
            return "redirect:/administrador/usuarios";
        }else {
            attr.addFlashAttribute("msg1","Hubieron errores en el llenado de los campos");
            return "administrador/crearPaciente";
        }


        int a = 0;
        if (paciente.getEdad()!=null){
           if (paciente.getEdad()<0) {
               model.addAttribute("edadmsg","La edad debe ser un numero entero positivo");
               a = a+1;
           }


        }
        try {
            int dniEntero = Integer.parseInt(paciente.getId());
            if (dniEntero<0){
                model.addAttribute("dnimsg","Deber ser un numero de DNI valido de maximo 8 digitos");
            }
        } catch (NumberFormatException e) {
            model.addAttribute("dnimsg","Deber ser un numero de DNI valido de maximo 8 digitos");
            a = a + 1;
        }
        try {
            int telefonoEntero = Integer.parseInt(paciente.getTelefono());
        } catch (NumberFormatException e) {
            model.addAttribute("telefonomsg","El telefono debe ser un numero entero de máximo 9 digitos");
            a = a + 1;
        }


        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes2(usuarioSession.getSedesIdsedes().getId());
        boolean existePaciente = false;
        for (Usuario pacienteLista : listaPacientes) {
            if (paciente.getId().equalsIgnoreCase(pacienteLista.getId()) ) {
                existePaciente = true;
            }
        }



        if ( existePaciente && bindingResult.hasErrors()) {
            attr.addFlashAttribute("msgDanger","El paciente ingresado ya existe");
            return "administrador/crearPaciente";
        } else if (bindingResult.hasErrors()) {
            return "administrador/crearPaciente";
        } else {
            GeneradorDeContrasenha generadorDeContrasenha=new GeneradorDeContrasenha();
            String contrasena = generadorDeContrasenha.crearPassword();
            String contrasenaBCrypt = new BCryptPasswordEncoder().encode(contrasena);
            usuarioRepository.crearPaciente( paciente.getEmail(),  paciente.getNombre(),  paciente.getApellido(),  paciente.getTelefono(), paciente.getId(),  usuarioSession.getSedesIdsedes().getId(), paciente.getEdad(), paciente.getDireccion() , paciente.getSexo(), contrasenaBCrypt );
            attr.addFlashAttribute("msg","Paciente creado exitosamente");
            return "redirect:/administrador/usuarios";
        }

    }  */


    @PostMapping("/crearPaciente")
    public String crearPaciente(
            @ModelAttribute("paciente") Usuario paciente,
            RedirectAttributes attr,
            Model model,
            HttpServletRequest httpServletRequest

    ){
        Regex regex = new Regex();
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");

        /*
        int a = 0;
        if(paciente.getNombre().isEmpty()){
            attr.addFlashAttribute("nombremsg","El nombre no puede ser nulo");
            a = a+1;
        }
        if(paciente.getApellido().isEmpty()){
            attr.addFlashAttribute("apellidomsg","El apellido no puede ser nulo");
            a = a+1;
        }
        if (paciente.getEmail().isEmpty()){
            attr.addFlashAttribute("correomsg","El correo no puede ser nulo");
            a = a+1;
        }
        if (paciente.getEdad() == null){
            attr.addFlashAttribute("edadmsg","La edad no puede ser nula");
            a = a+1;
        } else {
            if (paciente.getEdad()  <0){
                attr.addFlashAttribute("edadmsg","La edad no puede ser negativa");
                a = a+1;
            }
        }
        if (paciente.getTelefono().isEmpty()){
            attr.addFlashAttribute("telefonomsg","El teléfono no puede ser nulo");
            a = a+1;
        }
        if (paciente.getTelefono().length()!=9){
            attr.addFlashAttribute("telefonomsg", "El número de teléfono debe tener 9 dígitos");
            a = a+1;
        }
        if(paciente.getDireccion().isEmpty()) {
            attr.addFlashAttribute("addressmsg","La dirección no puede ser nula");
            a = a+1;
        }
        if(paciente.getId().isEmpty()){
            attr.addFlashAttribute("dnimsg","El DNI no puede ser nulo");
            a = a+1;
        } else if (paciente.getId().length()!=8) {
            attr.addFlashAttribute("dnimsg","El DNI tiene que tener 8 dígitos");
            a = a+1;
        } else {
            Optional<Usuario> u = usuarioRepository.findById(paciente.getId());
            if(u.isPresent()){
                attr.addFlashAttribute("dnimsg","El DNI ya se encuentra registrado.");
                a = a+1;
            }
        }
        if(a == 0){
            GeneradorDeContrasenha generadorDeContrasenha=new GeneradorDeContrasenha();
            String contrasena = generadorDeContrasenha.crearPassword();
            usuarioRepository.crearPaciente( paciente.getEmail(),  paciente.getNombre(),  paciente.getApellido(),  paciente.getTelefono(), paciente.getId(),  paciente.getSedesIdsedes().getId(), paciente.getEdad(), paciente.getDireccion() , paciente.getSexo(), contrasena );
            attr.addFlashAttribute("msg","Paciente creado exitosamente");
            return "redirect:/administrador/usuarios";
        }else {
            attr.addFlashAttribute("msg1","Hubieron errores en el llenado de los campos");
            return "administrador/crearPaciente";
        }
        */
        int a = 0;
        boolean existePaciente = false;
        if  ( paciente.getId()!=null && (!paciente.getId().isBlank())) {
            System.out.println("########################");
            System.out.println("########################");
            System.out.println(paciente.getId());
            System.out.println("########################");
            System.out.println("########################");
            try {
                int dniEntero = Integer.parseInt(paciente.getId());
                if (dniEntero<0 || dniEntero>99999999){
                    model.addAttribute("dnimsg","Deber ser un número de DNI válido de maximo 8 dígitos aaaa");
                    a = a + 1;
                } else {
                    List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes2(usuarioSession.getSedesIdsedes().getId());

                    for (Usuario pacienteLista : listaPacientes) {
                        if (paciente.getId().equalsIgnoreCase(pacienteLista.getId()) ) {
                            existePaciente = true;

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

        boolean existeCorreo = false;
        if  ( paciente.getEmail()!=null && (!paciente.getEmail().isBlank())) {
            System.out.println("########################");
            System.out.println("########################");
            System.out.println(paciente.getEmail());
            System.out.println("########################");
            System.out.println("########################");
            if ( paciente.getEmail().length() > 100  ||  (!regex.emailValid(paciente.getEmail()))) {
                model.addAttribute("emailmsg","El correo es de máximo 100 caracteres válidos");
                a = a + 1;
            }
            List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes2(usuarioSession.getSedesIdsedes().getId());
            for (Usuario pacienteLista : listaPacientes) {
                if (paciente.getEmail().equals(pacienteLista.getEmail()) ) {
                    existeCorreo= true;
                }
            }
        } else {
            model.addAttribute("emailmsg","El correo no puede ser nulo");
            a = a + 1;
        }



        if(paciente.getNombre() == null || paciente.getNombre().isBlank()){
            model.addAttribute("nombremsg","El nombre no puede ser nulo");
            a = a+1;
        } else if (paciente.getNombre().length() > 45 || !regex.inputisValid(paciente.getNombre())){
            model.addAttribute("nombremsg","El nombre debe ser un conjunto de caracteres valido de máximo 45 letras");
            a = a+1;
        }

        if(paciente.getApellido() == null || paciente.getApellido().isBlank()){
            model.addAttribute("apellidomsg","El apellido no puede ser nulo");
            a = a+1;
        } else if (paciente.getApellido().length() > 45 || !regex.inputisValid(paciente.getApellido())){
            model.addAttribute("apellidomsg","El apellido debe ser un conjunto de caracteres valido de máximo 45 letras");
            a = a+1;
        }

        if(paciente.getDireccion() == null || paciente.getDireccion().isBlank() ){
            model.addAttribute("direccionmsg","La dirección no puede ser nulo");
            a = a+1;
        } else if (paciente.getDireccion().length() > 200 ){
            model.addAttribute("direccionmsg","La dirección debe ser un conjunto de caracteres valido de máximo 45 letras");
            a = a+1;
        }

        if(paciente.getSexo() == null || paciente.getSexo().isBlank() ){
            model.addAttribute("sexomsg","El sexo del paciente no puede ser nulo");
            a = a+1;
        } else if ( !paciente.getSexo().equals("F") && !paciente.getSexo().equals("M") ){
            model.addAttribute("sexomsg","El sexo debe ser masculino o femenino");
            a = a+1;
        }
        System.out.println(paciente.getSexo());

        if (paciente.getEdad()!=null){
            try {
                //Integer edadEntero = Integer.parseInt(paciente.getEdad());
                if (paciente.getEdad()<0 || paciente.getEdad()>120) {
                    model.addAttribute("edadmsg","La edad debe ser un número entero entre 0 y 120 años");
                    a = a+1;
                }
            } catch (NumberFormatException e) {
                model.addAttribute("edadmsg","La edad debe ser un número entero entre 0 y 120 años");
                a = a + 1;
            }

        } else {
            model.addAttribute("edadmsg","La edad no puede ser nulo");
            a = a+1;
        }

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

        if ( existePaciente) {
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
            String contrasenaBCrypt = new BCryptPasswordEncoder().encode(contrasena);
            usuarioRepository.crearPaciente( paciente.getEmail(),  paciente.getNombre(),  paciente.getApellido(),  paciente.getTelefono(), paciente.getId(),  usuarioSession.getSedesIdsedes().getId(), paciente.getEdad(), paciente.getDireccion() , paciente.getSexo(), contrasenaBCrypt );
            attr.addFlashAttribute("msg","Paciente creado exitosamente");
            return "redirect:/administrador/usuarios";
        }

    }

    @GetMapping("/historialPaciente")
    public String verHistorial(Model model, @RequestParam("id") String id){
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        Usuario usuario = optionalUsuario.get();
        model.addAttribute("paciente",usuario);


        if (usuario.getHistorialmedicoIdhistorialmedico()!= null ) {
            List<Integer> idAlergias = historialMedicoHasAlergiaRepository2.listarAlergiasPorId(usuario.getHistorialmedicoIdhistorialmedico().getId());
            ArrayList<Alergia> listaAlergias = new ArrayList<>();
            for (Integer idAlergia : idAlergias) {
                listaAlergias.add(alergiaRepository.obtenerAlergia(idAlergia));
            }
            model.addAttribute("listaAlergias",listaAlergias);
            List<Cita> listaCitasPorUsuario = citaRepository.citasPorUsuario(id);
            model.addAttribute("listaCitasPorUsuario",listaCitasPorUsuario);

            return "administrador/historial";

        } else {
            model.addAttribute("msgSinHistorial","El usuario no tiene registros de historial clínico");
            List<Cita> listaCitasPorUsuario = citaRepository.citasPorUsuario(id);
            model.addAttribute("listaCitasPorUsuario",listaCitasPorUsuario);
            return "administrador/historial";
        }

    }



    @GetMapping("/calendario")
    public String calendario(){

        return "administrador/calendario";
    }

    @GetMapping("/finanzas")
    public String finanzas(){

        return "administrador/finanzas";
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
        }else {
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
    public String guardarFormulariosRegistro(@RequestParam(value = "seleccionados", required = false) List<String> idsSeleccionados, RedirectAttributes attr){
        //Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");

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
                String contrasena = generadorDeContrasenha.crearPassword();
                String contrasenaBCrypt = new BCryptPasswordEncoder().encode(contrasena);
                usuarioRepository.crearPaciente( formInvitacionOptional.get().getCorreo(), formInvitacionOptional.get().getNombres(),  formInvitacionOptional.get().getApellidos(),  formInvitacionOptional.get().getCelular(), formInvitacionOptional.get().getDni(),  Integer.parseInt(formInvitacionOptional.get().getIdSede()), Integer.parseInt(formInvitacionOptional.get().getEdad()), formInvitacionOptional.get().getDomicilio() , formInvitacionOptional.get().getSexo(), contrasenaBCrypt );
                // Actualizar el estado de pendiente a 0. ya no es pendiente.
                formInvitationRepository.actualizarEstadoFormRegistroRevisado(idInt);
                //ENVIAR CORREO CON CONTRASEÑA
                String destinatario = formInvitacionOptional.get().getCorreo() ;
                String asunto = "Invitación a la plataforma de Medical-Tec";
                String contenido = "<!DOCTYPE html>\n" +
                        "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
                        "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                        "  <title></title>\n" +
                        "  <!--[if mso]>\n" +
                        "  <style>\n" +
                        "    table {border-collapse:collapse;border-spacing:0;border:none;margin:0;}\n" +
                        "    div, td {padding:0;}\n" +
                        "    div {margin:0 !important;}\n" +
                        "  </style>\n" +
                        "  <noscript>\n" +
                        "    <xml>\n" +
                        "      <o:OfficeDocumentSettings>\n" +
                        "        <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                        "      </o:OfficeDocumentSettings>\n" +
                        "    </xml>\n" +
                        "  </noscript>\n" +
                        "  <![endif]-->\n" +
                        "  <style>\n" +
                        "    table, td, div, h1, p {\n" +
                        "      font-family: Arial, sans-serif;\n" +
                        "    }\n" +
                        "    @media screen and (max-width: 530px) {\n" +
                        "      .unsub {\n" +
                        "        display: block;\n" +
                        "        padding: 8px;\n" +
                        "        margin-top: 14px;\n" +
                        "        border-radius: 6px;\n" +
                        "        background-color: #555555;\n" +
                        "        text-decoration: none !important;\n" +
                        "        font-weight: bold;\n" +
                        "      }\n" +
                        "      .col-lge {\n" +
                        "        max-width: 100% !important;\n" +
                        "      }\n" +
                        "    }\n" +
                        "    @media screen and (min-width: 531px) {\n" +
                        "      .col-sml {\n" +
                        "        max-width: 27% !important;\n" +
                        "      }\n" +
                        "      .col-lge {\n" +
                        "        max-width: 73% !important;\n" +
                        "      }\n" +
                        "    }\n" +
                        "  </style>\n" +
                        "</head>\n" +
                        "<body style=\"margin:0;padding:0;word-spacing:normal;background-color:#939297;\">\n" +
                        "  <div role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"text-size-adjust:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;background-color:#939297;\">\n" +
                        "    <table role=\"presentation\" style=\"width:100%;border:none;border-spacing:0;\">\n" +
                        "      <tr>\n" +
                        "        <td align=\"center\" style=\"padding:0;\">\n" +
                        "          <!--[if mso]>\n" +
                        "          <table role=\"presentation\" align=\"center\" style=\"width:600px;\">\n" +
                        "          <tr>\n" +
                        "          <td>\n" +
                        "          <![endif]-->\n" +
                        "          <table role=\"presentation\" style=\"width:94%;max-width:600px;border:none;border-spacing:0;text-align:left;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;\">\n" +
                        "            <tr>\n" +
                        "              <td style=\"padding:40px 30px 30px 30px;text-align:center;font-size:24px;font-weight:bold;\">\n" +
                        "                <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"><img src=\"https://assets.codepen.io/210284/logo.png\" width=\"165\" alt=\"Logo\" style=\"width:165px;max-width:80%;height:auto;border:none;text-decoration:none;color:#ffffff;\"></a>\n" +
                        "              </td>\n" +
                        "            </tr>\n" +
                        "            <tr>\n" +
                        "              <td style=\"padding:30px;background-color:#ffffff;\">\n" +
                        "                <h1 style=\"margin-top:0;margin-bottom:16px;font-size:26px;line-height:32px;font-weight:bold;letter-spacing:-0.02em;\">Bienvenido a la plataforma de Medical-TEC!</h1>\n" +
                        "                <p style=\"margin:0;\"> TEXTO DE BIENVENIDA <a href=\"http://www.example.com/\" style=\"color:#e50d70;text-decoration:underline;\"></a></p>\n" +
                        " <p style=\"margin:0;\"> Su contraseña predeterminada es " + contrasena + " </p> " +
                        "              </td>\n" +
                        "            </tr>\n" +
                        "            <tr>\n" +
                        "              <td style=\"padding:0;font-size:24px;line-height:28px;font-weight:bold;\">\n" +
                        "                <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"><img src=\"https://assets.codepen.io/210284/1200x800-2.png\" width=\"600\" alt=\"\" style=\"width:100%;height:auto;display:block;border:none;text-decoration:none;color:#363636;\"></a>\n" +
                        "              </td>\n" +
                        "            </tr>\n" +
                        "            <tr>\n" +
                        "              <td style=\"padding:35px 30px 11px 30px;font-size:0;background-color:#ffffff;border-bottom:1px solid #f0f0f5;border-color:rgba(201,201,207,.35);\">\n" +
                        "                <!--[if mso]>\n" +
                        "                <table role=\"presentation\" width=\"100%\">\n" +
                        "                <tr>\n" +
                        "                <td style=\"width:145px;\" align=\"left\" valign=\"top\">\n" +
                        "                <![endif]-->\n" +
                        "                <div class=\"col-sml\" style=\"display:inline-block;width:100%;max-width:145px;vertical-align:top;text-align:left;font-family:Arial,sans-serif;font-size:14px;color:#363636;\">\n" +
                        "                  <img src=\"https://assets.codepen.io/210284/icon.png\" width=\"115\" alt=\"\" style=\"width:115px;max-width:80%;margin-bottom:20px;\">\n" +
                        "                </div>\n" +
                        "                <!--[if mso]>\n" +
                        "                </td>\n" +
                        "                <td style=\"width:395px;padding-bottom:20px;\" valign=\"top\">\n" +
                        "                <![endif]-->\n" +
                        "                <div class=\"col-lge\" style=\"display:inline-block;width:100%;max-width:395px;vertical-align:top;padding-bottom:20px;font-family:Arial,sans-serif;font-size:16px;line-height:22px;color:#363636;\">\n" +
                        "                  <p style=\"margin-top:0;margin-bottom:12px;\">Nullam mollis sapien vel cursus fermentum. Integer porttitor augue id ligula facilisis pharetra. In eu ex et elit ultricies ornare nec ac ex. Mauris sapien massa, placerat non venenatis et, tincidunt eget leo.</p>\n" +
                        "                  <p style=\"margin-top:0;margin-bottom:18px;\">Nam non ante risus. Vestibulum vitae eleifend nisl, quis vehicula justo. Integer viverra efficitur pharetra. Nullam eget erat nibh.</p>\n" +
                        "                  <p style=\"margin:0;\"><a href=\"https://example.com/\" style=\"background: #ff3884; text-decoration: none; padding: 10px 25px; color: #ffffff; border-radius: 4px; display:inline-block; mso-padding-alt:0;text-underline-color:#ff3884\"><!--[if mso]><i style=\"letter-spacing: 25px;mso-font-width:-100%;mso-text-raise:20pt\">&nbsp;</i><![endif]--><span style=\"mso-text-raise:10pt;font-weight:bold;\">Claim yours now</span><!--[if mso]><i style=\"letter-spacing: 25px;mso-font-width:-100%\">&nbsp;</i><![endif]--></a></p>\n" +
                        "                </div>\n" +
                        "                <!--[if mso]>\n" +
                        "                </td>\n" +
                        "                </tr>\n" +
                        "                </table>\n" +
                        "                <![endif]-->\n" +
                        "              </td>\n" +
                        "            </tr>\n" +
                        "            <tr>\n" +
                        "              <td style=\"padding:30px;font-size:24px;line-height:28px;font-weight:bold;background-color:#ffffff;border-bottom:1px solid #f0f0f5;border-color:rgba(201,201,207,.35);\">\n" +
                        "                <a href=\"http://www.example.com/\" style=\"text-decoration:none;\"><img src=\"https://assets.codepen.io/210284/1200x800-1.png\" width=\"540\" alt=\"\" style=\"width:100%;height:auto;border:none;text-decoration:none;color:#363636;\"></a>\n" +
                        "              </td>\n" +
                        "            </tr>\n" +
                        "            <tr>\n" +
                        "              <td style=\"padding:30px;background-color:#ffffff;\">\n" +
                        "                <p style=\"margin:0;\">Duis sit amet accumsan nibh, varius tincidunt lectus. Quisque commodo, nulla ac feugiat cursus, arcu orci condimentum tellus, vel placerat libero sapien et libero. Suspendisse auctor vel orci nec finibus.</p>\n" +
                        "              </td>\n" +
                        "            </tr>\n" +
                        "            <tr>\n" +
                        "              <td style=\"padding:30px;text-align:center;font-size:12px;background-color:#404040;color:#cccccc;\">\n" +
                        "                <p style=\"margin:0 0 8px 0;\"><a href=\"http://www.facebook.com/\" style=\"text-decoration:none;\"><img src=\"https://assets.codepen.io/210284/facebook_1.png\" width=\"40\" height=\"40\" alt=\"f\" style=\"display:inline-block;color:#cccccc;\"></a> <a href=\"http://www.twitter.com/\" style=\"text-decoration:none;\"><img src=\"https://assets.codepen.io/210284/twitter_1.png\" width=\"40\" height=\"40\" alt=\"t\" style=\"display:inline-block;color:#cccccc;\"></a></p>\n" +
                        "                <p style=\"margin:0;font-size:14px;line-height:20px;\">&reg; Someone, Somewhere 2021<br><a class=\"unsub\" href=\"http://www.example.com/\" style=\"color:#cccccc;text-decoration:underline;\">Unsubscribe instantly</a></p>\n" +
                        "              </td>\n" +
                        "            </tr>\n" +
                        "          </table>\n" +
                        "          <!--[if mso]>\n" +
                        "          </td>\n" +
                        "          </tr>\n" +
                        "          </table>\n" +
                        "          <![endif]-->\n" +
                        "        </td>\n" +
                        "      </tr>\n" +
                        "    </table>\n" +
                        "  </div>\n" +
                        "</body>\n" +
                        "</html>";

                try {
                    correoConEstilos.sendEmailEstilos(destinatario, asunto, contenido);
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
