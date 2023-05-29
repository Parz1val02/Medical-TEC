package com.example.medicaltec.controller;
import com.example.medicaltec.Entity.Alergia;
import com.example.medicaltec.Entity.Cita;
import com.example.medicaltec.Entity.Especialidade;
import com.example.medicaltec.Entity.Usuario;
import com.example.medicaltec.funciones.GeneradorDeContrasenha;
import com.example.medicaltec.repository.*;
import jakarta.servlet.http.*;
import jakarta.validation.Valid;
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


    final EspecialidadeRepository especialidadeRepository;
    final UsuarioRepository usuarioRepository;
    final CitaRepository citaRepository;
    final AlergiaRepository alergiaRepository;
    final HistorialMedicoHasAlergiaRepository2 historialMedicoHasAlergiaRepository2;
    public AdministradorController (
            CitaRepository citaRepository,
            UsuarioRepository usuarioRepository,
            EspecialidadeRepository especialidadeRepository,
            AlergiaRepository alergiaRepository,
            HistorialMedicoHasAlergiaRepository2 historialMedicoHasAlergiaRepository2
            ) {
        this.usuarioRepository = usuarioRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.citaRepository = citaRepository;
        this.alergiaRepository = alergiaRepository;
        this.historialMedicoHasAlergiaRepository2 = historialMedicoHasAlergiaRepository2;
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
            usuarioRepository.crearDoctor( doctor.getEmail(),  doctor.getNombre(),  doctor.getApellido(),  doctor.getTelefono(),  doctor.getEspecialidadesIdEspecialidad().getId(),  doctor.getId(),  usuarioSession.getSedesIdsedes().getId(), doctor.getEdad(), doctor.getDireccion(), doctor.getSexo(), contrasena );
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
        return "administrador/crearPaciente";
    }

    @PostMapping("/crearPaciente")
    public String crearPaciente(
            @ModelAttribute("paciente") @Valid Usuario paciente,
            BindingResult bindingResult,
            RedirectAttributes attr,
            Model model,
            HttpServletRequest httpServletRequest

    ){
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
        }*/
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
            usuarioRepository.crearPaciente( paciente.getEmail(),  paciente.getNombre(),  paciente.getApellido(),  paciente.getTelefono(), paciente.getId(),  paciente.getSedesIdsedes().getId(), paciente.getEdad(), paciente.getDireccion() , paciente.getSexo(), contrasena );
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



}
