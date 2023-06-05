package com.example.medicaltec.controller;
import com.example.medicaltec.Entity.*;
import com.example.medicaltec.config.CustomUserDetails;
import com.example.medicaltec.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.medicaltec.controller.ExampController;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/superAdmin")
public class SuperController {

    final UsuarioRepository usuarioRepository;
    final FormulariosRegistroRepository formulariosRegistroRepository;
    final InformeRepository informeRepository;
    final CuestionarioRepository cuestionarioRepository;
    final PreguntaRepository preguntaRepository;
    final RespuestaRepository respuestaRepository;
    final EstadoRepository estadoRepository;
    final CitaRepository citaRepository;
    final HistorialMedicoRepository historialMedicoRepository;

    final SedeRepository sedeRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public SuperController(UsuarioRepository usuarioRepository, FormulariosRegistroRepository formulariosRegistroRepository, InformeRepository informeRepository, CuestionarioRepository cuestionarioRepository,
                           PreguntaRepository preguntaRepository,
                           RespuestaRepository respuestaRepository, EstadoRepository estadoRepository,
                           CitaRepository citaRepository, HistorialMedicoRepository historialMedicoRepository, EspecialidadeRepository especialidadeRepository, SedeRepository sedeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.formulariosRegistroRepository = formulariosRegistroRepository;
        this.informeRepository = informeRepository;
        this.cuestionarioRepository = cuestionarioRepository;
        this.preguntaRepository = preguntaRepository;
        this.respuestaRepository = respuestaRepository;
        this.estadoRepository = estadoRepository;
        this.citaRepository = citaRepository;
        this.historialMedicoRepository = historialMedicoRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.sedeRepository = sedeRepository;
    }

    @GetMapping(value = {"/dashboard", ""})
    public String dashboard(Model model, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
        List<Especialidade>listaEspecialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidadList", listaEspecialidades);
        List<Sede> listaSedes = sedeRepository.findAll();
        model.addAttribute("sedesList", listaSedes);
        List<Estado> listaEstados = estadoRepository.findAll();
        model.addAttribute("estadosList",listaEstados);

        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        model.addAttribute("pacientesList", listaPacientes);
        List<Usuario> listaDoctores = usuarioRepository.obtenerListaDoctores();
        model.addAttribute("doctoresList", listaDoctores);
        List<Usuario> listaAdministrativos = usuarioRepository.obtenerListaAdministrativos();
        model.addAttribute("administrativosList", listaAdministrativos);
        List<Usuario> listaAdministradores = usuarioRepository.obtenerListaAdministradores();
        model.addAttribute("administradoresList", listaAdministradores);
        return "superAdmin/dashboard";
    }

    @GetMapping(value = {"/dashboard/Doctor"})
    public String dashboardDoctor(Model model, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
        List<Especialidade>listaEspecialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidadList", listaEspecialidades);
        List<Sede> listaSedes = sedeRepository.findAll();
        model.addAttribute("sedesList", listaSedes);
        List<Estado> listaEstados = estadoRepository.findAll();
        model.addAttribute("estadosList",listaEstados);

        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        model.addAttribute("pacientesList", listaPacientes);
        List<Usuario> listaDoctores = usuarioRepository.obtenerListaDoctores();
        model.addAttribute("doctoresList", listaDoctores);
        List<Usuario> listaAdministrativos = usuarioRepository.obtenerListaAdministrativos();
        model.addAttribute("administrativosList", listaAdministrativos);
        List<Usuario> listaAdministradores = usuarioRepository.obtenerListaAdministradores();
        model.addAttribute("administradoresList", listaAdministradores);
        List<Sede> listaSede = sedeRepository.findAll();
        model.addAttribute("listaSede", listaSede);
        List<Estado> listaEstado = estadoRepository.findAll();
        model.addAttribute("listaEstado", listaEstado);
        return "superAdmin/dashboardDoctor";
    }

    @GetMapping(value = {"/dashboard/Paciente"})
    public String dashboardPaciente(Model model, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
        List<Especialidade>listaEspecialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidadList", listaEspecialidades);
        List<Sede> listaSedes = sedeRepository.findAll();
        model.addAttribute("sedesList", listaSedes);
        List<Estado> listaEstados = estadoRepository.findAll();
        model.addAttribute("estadosList",listaEstados);

        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        model.addAttribute("pacientesList", listaPacientes);
        List<Usuario> listaDoctores = usuarioRepository.obtenerListaDoctores();
        model.addAttribute("doctoresList", listaDoctores);
        List<Usuario> listaAdministrativos = usuarioRepository.obtenerListaAdministrativos();
        model.addAttribute("administrativosList", listaAdministrativos);
        List<Usuario> listaAdministradores = usuarioRepository.obtenerListaAdministradores();
        model.addAttribute("administradoresList", listaAdministradores);
        List<Sede> listaSede = sedeRepository.findAll();
        model.addAttribute("listaSede", listaSede);
        List<Estado> listaEstado = estadoRepository.findAll();
        model.addAttribute("listaEstado", listaEstado);
        return "superAdmin/dashboardPaciente";
    }

    @GetMapping(value = {"/dashboard/AdmT"})
    public String dashboardAdmT(Model model, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
        List<Especialidade>listaEspecialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidadList", listaEspecialidades);
        List<Sede> listaSedes = sedeRepository.findAll();
        model.addAttribute("sedesList", listaSedes);
        List<Estado> listaEstados = estadoRepository.findAll();
        model.addAttribute("estadosList",listaEstados);

        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        model.addAttribute("pacientesList", listaPacientes);
        List<Usuario> listaDoctores = usuarioRepository.obtenerListaDoctores();
        model.addAttribute("doctoresList", listaDoctores);
        List<Usuario> listaAdministrativos = usuarioRepository.obtenerListaAdministrativos();
        model.addAttribute("administrativosList", listaAdministrativos);
        List<Usuario> listaAdministradores = usuarioRepository.obtenerListaAdministradores();
        model.addAttribute("administradoresList", listaAdministradores);
        List<Sede> listaSede = sedeRepository.findAll();
        model.addAttribute("listaSede", listaSede);
        List<Estado> listaEstado = estadoRepository.findAll();
        model.addAttribute("listaEstado", listaEstado);
        return "superAdmin/dashboardAdmT";
    }

    @GetMapping(value = {"/dashboard/Adm"})
    public String dashboardAdm(Model model, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
        List<Especialidade>listaEspecialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidadList", listaEspecialidades);
        List<Sede> listaSedes = sedeRepository.findAll();
        model.addAttribute("sedesList", listaSedes);
        List<Estado> listaEstados = estadoRepository.findAll();
        model.addAttribute("estadosList",listaEstados);

        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        model.addAttribute("pacientesList", listaPacientes);
        List<Usuario> listaDoctores = usuarioRepository.obtenerListaDoctores();
        model.addAttribute("doctoresList", listaDoctores);
        List<Usuario> listaAdministrativos = usuarioRepository.obtenerListaAdministrativos();
        model.addAttribute("administrativosList", listaAdministrativos);
        List<Usuario> listaAdministradores = usuarioRepository.obtenerListaAdministradores();
        model.addAttribute("administradoresList", listaAdministradores);
        List<Sede> listaSede = sedeRepository.findAll();
        model.addAttribute("listaSede", listaSede);
        List<Estado> listaEstado = estadoRepository.findAll();
        model.addAttribute("listaEstado", listaEstado);
        return "superAdmin/dashboardAdmSede";
    }
    @GetMapping("/editarAdmS")
    public String editarAdministrador(Model model, @ModelAttribute("admS") Usuario admS, @RequestParam("id") String dni, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(dni);
        if (optionalUsuario.isPresent()) {
            admS = optionalUsuario.get();
            model.addAttribute("admS", admS);
            model.addAttribute("sedesList", sedeRepository.findAll());
            model.addAttribute("estadosList", estadoRepository.findAll());
            return "superAdmin/editarAdmSede";
        } else {
            attr.addFlashAttribute("administrador_noexiste","El administrador a editar no existe");
            return "redirect:/superAdmin/dashboard";
        }
    }
    @PostMapping("/actualizarAdmS")
    public String actualizarAdministrador(@ModelAttribute("admS") @Valid Usuario admS, BindingResult bindingResult, RedirectAttributes attr, Model model, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(bindingResult.hasErrors()){
            model.addAttribute("sedesList", sedeRepository.findAll());
            model.addAttribute("estadosList", estadoRepository.findAll());
            return "superAdmin/editarAdmSede";
        }
        else{
            usuarioRepository.editarAdministradores(admS.getEmail(), admS.getNombre(), admS.getApellido(), admS.getSedesIdsedes().getId(), admS.getTelefono(), admS.getEstadosIdestado().getId(), admS.getId());
            attr.addFlashAttribute("administrador_actualizado", "Administrador actualizado exitosamente");
            return "redirect:/superAdmin/dashboard";
        }
        /*
        int a = 0;
        if(admS.getNombre().isEmpty()){
            attr.addFlashAttribute("nombremsg","El nombre no puede ser nulo");
            a = a+1;
        }
        if(admS.getApellido().isEmpty()){
            attr.addFlashAttribute("apellidomsg","El apellido no puede ser nulo");
            a = a+1;
        }
        if (admS.getEmail().isEmpty()){
            attr.addFlashAttribute("correomsg","El correo no puede ser nulo");
            a = a+1;
        }
        if (admS.getTelefono().isEmpty()){
            attr.addFlashAttribute("telefonomsg","El teléfono no puede ser nulo");
            a = a+1;
        }
        if (admS.getTelefono().length()!=9){
            attr.addFlashAttribute("telefonomsg", "El número de teléfono debe tener 9 dígitos");
            a = a+1;
        }
        if(a == 0){
            usuarioRepository.editarAdministradores(admS.getEmail(), admS.getNombre(), admS.getApellido(), admS.getSedesIdsedes().getId(), admS.getTelefono(), admS.getEstadosIdestado().getId(), admS.getId());
            attr.addFlashAttribute("msg","Administrador de Sede creado exitosamente");
            return "redirect:/superAdmin/dashboard/Adm";
        }else {
            attr.addFlashAttribute("msg1","Hubieron errores en el llenado de los campos");
            return "redirect:/superAdmin/editarAdmS";
        } */

    }
    @GetMapping("/editarAdmT")
    public String editarAdministrativo(Model model, @ModelAttribute("admT") Usuario admT, @RequestParam("id") String dni, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(dni);
        if (optionalUsuario.isPresent()) {
            admT = optionalUsuario.get();
            model.addAttribute("admT", admT);
            model.addAttribute("sedesList", sedeRepository.findAll());
            model.addAttribute("estadosList", estadoRepository.findAll());
            model.addAttribute("especialidadList", especialidadeRepository.findAll());
            return "superAdmin/editarAdmT";
        } else {
            attr.addFlashAttribute("administrativo_noexiste","El administrativo a editar no existe");
            return "redirect:/superAdmin/dashboard";
        }
    }
    @PostMapping("/actualizarAdmT")
    public String actualizarAdministrativo(@ModelAttribute("admT") @Valid Usuario admT,BindingResult bindingResult, Model model, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(bindingResult.hasErrors()){
            model.addAttribute("especialidadList", especialidadeRepository.findAll());
            model.addAttribute("sedesList", sedeRepository.findAll());
            model.addAttribute("estadosList", estadoRepository.findAll());
            return "superAdmin/editarAdmT";
        }
        else {
            attr.addFlashAttribute("administrativo_actualizado", "Administrativo actualizado exitosamente");
            usuarioRepository.editarAdministrativo(admT.getEmail(), admT.getNombre(), admT.getApellido(), admT.getSedesIdsedes().getId(), admT.getTelefono(), admT.getEstadosIdestado().getId(), admT.getEspecialidadesIdEspecialidad().getId(), admT.getId());
            return "redirect:/superAdmin/dashboard";
        }
    }
    @GetMapping("/editarDoctor")
    public String editarDoctor(Model model, @ModelAttribute("doctor") Usuario doctor, @RequestParam("id") String dni, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(dni);
        if (optionalUsuario.isPresent()) {
            doctor = optionalUsuario.get();
            model.addAttribute("doctor", doctor);
            model.addAttribute("sedesList", sedeRepository.findAll());
            model.addAttribute("estadosList", estadoRepository.findAll());
            model.addAttribute("especialidadList", especialidadeRepository.findAll());
            return "superAdmin/editarDoctor";
        } else {
            attr.addFlashAttribute("doctor_noexiste","El doctor a editar no existe");
            return "redirect:/superAdmin/dashboard";
        }
    }
    @PostMapping("/actualizarDoctor")
    public String actualizarDoctor(@ModelAttribute("doctor") @Valid Usuario doctor, BindingResult bindingResult, Model model, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(bindingResult.hasErrors()){
            model.addAttribute("sedesList", sedeRepository.findAll());
            model.addAttribute("estadosList", estadoRepository.findAll());
            model.addAttribute("especialidadList", especialidadeRepository.findAll());
            return "superAdmin/editarDoctor";
        }
        else {
            attr.addFlashAttribute("doctor_actualizado", "Doctor actualizado exitosamente");
            usuarioRepository.editarDoctor(doctor.getEmail(), doctor.getNombre(), doctor.getApellido(), doctor.getSedesIdsedes().getId(), doctor.getTelefono(), doctor.getEstadosIdestado().getId(), doctor.getEspecialidadesIdEspecialidad().getId(), doctor.getId());
            return "redirect:/superAdmin/dashboard";
        }
    }

    @GetMapping("/editarPaciente")
    public String editarPaciente(Model model, @ModelAttribute("paciente") Usuario paciente, @RequestParam("id") String dni, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(dni);
        if (optionalUsuario.isPresent()) {
            paciente = optionalUsuario.get();
            model.addAttribute("paciente", paciente);
            model.addAttribute("sedesList", sedeRepository.findAll());
            model.addAttribute("estadosList", estadoRepository.findAll());
            model.addAttribute("especialidadList", especialidadeRepository.findAll());
            return "superAdmin/editarPaciente";
        } else {
            attr.addFlashAttribute("paciente_noexiste","El paciente a editar no existe");
            return "redirect:/superAdmin/dashboard";
        }
    }
    @PostMapping("/actualizarPaciente")
    public String actualizarPaciente(@ModelAttribute("paciente") @Valid Usuario paciente, BindingResult bindingResult, Model model, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(bindingResult.hasErrors()){
            model.addAttribute("sedesList", sedeRepository.findAll());
            model.addAttribute("estadosList", estadoRepository.findAll());
            model.addAttribute("especialidadList", especialidadeRepository.findAll());
            return "superAdmin/editarPaciente";
        }
        attr.addFlashAttribute("paciente_actualizado", "Paciente actualizado exitosamente");
        usuarioRepository.editarPaciente(paciente.getEmail(), paciente.getNombre(), paciente.getApellido(), paciente.getSedesIdsedes().getId(), paciente.getTelefono(), paciente.getEstadosIdestado().getId(), paciente.getId());
        return "redirect:/superAdmin/dashboard";
    }
   /* @PostMapping("/editarPacientes")
    public String editarPaciente(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("id") String id,

            @RequestParam("telefono") String telefono,
            @RequestParam("estado") int estado,
            RedirectAttributes attr

    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Paciente actualizado exitosamente");
        usuarioRepository.editarPaciente( email,  nombre, sede, id, telefono, estado);
        return "redirect:/superAdmin/dashboard/Paciente";
    }
    @PostMapping("/editarDoctores")
    public String editarDoctor(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("email") String email,
            @RequestParam("id") String dni,
            @RequestParam("telefono") String telefono,
            @RequestParam("especialidad") int especialidad,
            @RequestParam("estado") int estado,
            RedirectAttributes attr


    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Doctor actualizado exitosamente");
        usuarioRepository.editarDoctor( email, nombre, apellido, sede, telefono, estado, especialidad, dni);
        return "redirect:/superAdmin/dashboard/Doctor";
    }
    @PostMapping("/editarAdministrativos")
    public String editarAdministrativo(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("email") String email,
            @RequestParam("id") String dni,
            @RequestParam("telefono") String telefono,
            @RequestParam("especialidad") int especialidad,
            @RequestParam("estado") int estado,
            RedirectAttributes attr

    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Administrativo actualizado exitosamente");
        usuarioRepository.editarAdministrativo( email, nombre, apellido, sede, telefono, estado, especialidad, dni);
        return "redirect:/superAdmin/dashboard/AdmT";
    }
    @PostMapping("/editarAdmS")
    public String editarAdministrador(
            @RequestParam("sede") int sede,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("email") String email,
            @RequestParam("id") String dni,
            @RequestParam("telefono") String telefono,
            @RequestParam("estado") int estado,
            RedirectAttributes attr

    ){
        System.out.println(nombre);
        attr.addFlashAttribute("msg","Administrador actualizado exitosamente");
        usuarioRepository.editarAdministradores( email, nombre, apellido, sede, telefono, estado, dni);
        return "redirect:/superAdmin/dashboard/Adm";
    }
    */
    @RequestMapping(value = {"/forms"},method = RequestMethod.GET)
    public String forms(Model model, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<FormulariosRegistro> listaFormularios = formulariosRegistroRepository.findAll();
        model.addAttribute("formularioList", listaFormularios);
        return "superAdmin/forms";
    }

    @RequestMapping(value = {"/edit/CAntecedentes"},method = RequestMethod.POST)
    public String cuestionarioAntecedente(Model model, @RequestParam("num") int num, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        //List<FormulariosRegistro> listaFormularios = formulariosRegistroRepository.findAll();
        //model.addAttribute("formularioList", listaFormularios);
        String vista = "superAdmin/cuestionario" + num; // Concatenar el valor de num con el nombre de la vista
        return vista; // Retornar el nombre de la vista concatenado
    }

    @RequestMapping(value = {"/Crear/AdmSede"},method = RequestMethod.GET)
    public String crearAdmSEDE(Model model, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
        List<Especialidade>listaEspecialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidadList", listaEspecialidades);

        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        model.addAttribute("pacientesList", listaPacientes);
        List<Usuario> listaDoctores = usuarioRepository.obtenerListaDoctores();
        model.addAttribute("doctoresList", listaDoctores);
        List<Usuario> listaAdministrativos = usuarioRepository.obtenerListaAdministrativos();
        model.addAttribute("administrativosList", listaAdministrativos);
        List<Usuario> listaAdministradores = usuarioRepository.obtenerListaAdministradores();
        model.addAttribute("administradoresList", listaAdministradores);
        List<Sede> listaSede = sedeRepository.findAll();
        model.addAttribute("listaSede", listaSede);
        return "superAdmin/crearAdmSede";
    }

    @RequestMapping(value = {"/Crear/AdmT"},method = RequestMethod.GET)
    public String crearAdmT(Model model, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Usuario> lista = usuarioRepository.findAll();
        model.addAttribute("usuarioList", lista);
        List<Especialidade>listaEspecialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidadList", listaEspecialidades);

        List<Usuario> listaPacientes = usuarioRepository.obtenerListaPacientes();
        model.addAttribute("pacientesList", listaPacientes);
        List<Usuario> listaDoctores = usuarioRepository.obtenerListaDoctores();
        model.addAttribute("doctoresList", listaDoctores);
        List<Usuario> listaAdministrativos = usuarioRepository.obtenerListaAdministrativos();
        model.addAttribute("administrativosList", listaAdministrativos);
        List<Usuario> listaAdministradores = usuarioRepository.obtenerListaAdministradores();
        model.addAttribute("administradoresList", listaAdministradores);
        List<Sede> listaSede = sedeRepository.findAll();
        model.addAttribute("listaSede", listaSede);
        return "superAdmin/crearAdmT";
    }
    @PostMapping("/save/AdmS")
    public String guardarAdministrador(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,
                                       @RequestParam("email") String email,
                                        @RequestParam("telefono") String telefono,
                                       @RequestParam("sede") int sede,
                                       @RequestParam("id") String dni, @RequestParam("sede") int estado, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
       usuarioRepository.editAdmS(nombre,apellido,email,telefono,sede, estado,dni);
        attr.addFlashAttribute("msg","Administrador actualizado exitosamente");
        return "redirect:/superAdmin/dashboard";
    }
    @PostMapping(value = "/Guardar/AdmSede")
    public String guardarAdmSede(Model model, RedirectAttributes attr,
                                 @RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,
                                 @RequestParam("correo") String correo, @RequestParam("password") String password,
                                 @RequestParam(value = "edad",required = false) Integer edad, @RequestParam("telefono") String telefono,
                                 @RequestParam("address") String address, @RequestParam("sede") int sede,
                                 @RequestParam("dni") String dni, @RequestParam("sexo") String sexo, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        int a = 0;
        if(nombre.isEmpty()){
            attr.addFlashAttribute("nombremsg","El nombre no puede ser nulo");
            a = a+1;
        }
        if(apellido.isEmpty()){
            attr.addFlashAttribute("apellidomsg","El apellido no puede ser nulo");
            a = a+1;
        }
        if (correo.isEmpty()){
            attr.addFlashAttribute("correomsg","El correo no puede ser nulo");
            a = a+1;
        }
        if (password.isEmpty()){
            attr.addFlashAttribute("passwordmsg","La contraseña no puede ser nula");
            a = a+1;
        }
        if (edad == null){
            attr.addFlashAttribute("edadmsg","La edad no puede ser nula");
            a = a+1;
        } else {
            if (edad <0){
                attr.addFlashAttribute("edadmsg","La edad no puede ser negativa");
                a = a+1;
            }
        }
        if (telefono.isEmpty()){
            attr.addFlashAttribute("telefonomsg","El teléfono no puede ser nulo");
            a = a+1;
        }
        if (telefono.length()!=9){
            attr.addFlashAttribute("telefonomsg", "El número de teléfono debe tener 9 dígitos");
            a = a+1;
        }
        if(address.isEmpty()) {
            attr.addFlashAttribute("addressmsg","La dirección no puede ser nula");
            a = a+1;
        }
        if(dni.isEmpty()){
            attr.addFlashAttribute("dnimsg","El DNI no puede ser nulo");
            a = a+1;
        } else if (dni.length()!=8) {
            attr.addFlashAttribute("dnimsg","El DNI tiene que tener 8 dígitos");
            a = a+1;
        } else {
            Optional<Usuario> u = usuarioRepository.findById(dni);
            if(u.isPresent()){
                attr.addFlashAttribute("dnimsg","El DNI ya se encuentra registrado.");
                a = a+1;
            }
        }

        if(a == 0){
            int estado=1;
            usuarioRepository.crearAdmSede(dni,password,correo, nombre,apellido,  edad,  telefono,  sexo,  address, sede, estado);
            attr.addFlashAttribute("msg","Administrador de Sede creado exitosamente");
            return "redirect:/superAdmin/dashboard/Adm";
        }else {
            attr.addFlashAttribute("msg1","Hubieron errores en el llenado de los campos");
            return "redirect:/superAdmin/Crear/AdmSede";
        }
    }

    @PostMapping(value = "/Guardar/AdmT")
    public String guardarAdmT(Model model, RedirectAttributes attr,
                                       @RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,
                                       @RequestParam("correo") String correo, @RequestParam("password") String password,
                                       @RequestParam(value = "edad", required = false) Integer edad, @RequestParam("telefono") String telefono,
                                       @RequestParam("address") String address, @RequestParam("sede") int sede, @RequestParam("especialidad") int especialidad,
                                       @RequestParam("dni") String dni, @RequestParam("sexo") String sexo, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        int b = 0;
        if(nombre.isEmpty()){
            attr.addFlashAttribute("nombremsg","El nombre no puede ser nulo");
            b = b+1;
        }
        if(apellido.isEmpty()){
            attr.addFlashAttribute("apellidomsg","El apellido no puede ser nulo");
            b = b+1;
        }
        if (correo.isEmpty()){
            attr.addFlashAttribute("correomsg","El correo no puede ser nulo");
            b = b+1;
        }
        if (password.isEmpty()){
            attr.addFlashAttribute("passwordmsg","La contraseña no puede ser nula");
            b = b+1;
        }
        if (edad == null){
            attr.addFlashAttribute("edadmsg","La edad no puede ser nula");
            b = b+1;
        } else {
            if (edad <0){
                attr.addFlashAttribute("edadmsg","La edad no puede ser negativa");
                b = b+1;
            }
        }
        if (telefono.isEmpty()){
            attr.addFlashAttribute("telefonomsg","El teléfono no puede ser nulo");
            b = b+1;
        }
        if (telefono.length()!=9){
            attr.addFlashAttribute("telefonomsg", "El número de teléfono debe tener 9 dígitos");
            b = b+1;
        }
        if(address.isEmpty()) {
            attr.addFlashAttribute("addressmsg","La dirección no puede ser nula");
            b = b+1;
        }
        if(dni.isEmpty()){
            attr.addFlashAttribute("dnimsg","El DNI no puede ser nulo");
            b = b+1;
        } else if (dni.length()!=8) {
            attr.addFlashAttribute("dnimsg","El DNI tiene que tener 8 dígitos");
            b = b+1;
        } else {
            Optional<Usuario> u = usuarioRepository.findById(dni);
            if(u.isPresent()){
                attr.addFlashAttribute("dnimsg","El DNI ya se encuentra registrado.");
                b = b+1;
            }
        }
        if(b == 0){
            int estado=1;
            usuarioRepository.crearAdmT( dni,  password, correo, nombre,apellido,  edad,  telefono,  sexo,  address,  sede, estado, especialidad);
            attr.addFlashAttribute("msg","Administrativo creado exitosamente");
            return "redirect:/superAdmin/dashboard/AdmT";
        }else {
            attr.addFlashAttribute("msg","Hubieron errores en el llenado de los campos");
            return "redirect:/superAdmin/Crear/AdmT";
        }

    }
    @GetMapping("/forms/delete")
    public String borrarFormulario(Model model,
                                   @RequestParam("id") int id,
                                   RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");

        Optional<FormulariosRegistro> optionalFormulariosRegistro = formulariosRegistroRepository.findById(id);

        if (optionalFormulariosRegistro.isPresent()) {
            formulariosRegistroRepository.deleteById(id);
            attr.addFlashAttribute("msg","Formulario borrado exitosamente");
        }
        return "redirect:/superAdmin/forms";
    }
    @RequestMapping(value = {"/informes"},method = RequestMethod.GET)
    public String informes(Model model, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        model.addAttribute("informeList", informeRepository.listarInforme());
        return "superAdmin/informes";
    }
    @GetMapping("/informes/delete")
    public String borrarInforme(Model model,
                                          @RequestParam("id") int id, @RequestParam("active") boolean active,
                                          RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");

        Optional<Informe> optionalInforme = informeRepository.findById(id);

        if (optionalInforme.isPresent() && active) {
            informeRepository.updateActivoByActivo(false,id);
            attr.addFlashAttribute("msg","Informe borrado exitosamente");
        }
        return "redirect:/superAdmin/informes";
    }
    @RequestMapping(value = {"/confSup"},method = RequestMethod.GET)
    public String confSup(Model model, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession){
        System.out.println("Entrando A Perfil de SuperAdmin");
        //Usuario user1 =
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        System.out.println("Se saco el usuario de sesión");
        Usuario usuario_doctor = (Usuario) httpSession.getAttribute("usuario");
        System.out.println("se saco usuario_doctor");
        System.out.println(usuario_doctor.getNombre() + " y su " + usuario_doctor.getTelefono());
        //System.out.println(superadmin.getNombre() + " y su " + superadmin.getTelefono());
        //Usuario user = usuarioRepository.obtenerSuperAdmin();
        model.addAttribute("superadmin",usuario_doctor);
        return "superAdmin/confSup";
    }
    @RequestMapping(value = {"/superPass"},method = RequestMethod.GET)
    public String superPass(Model model, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        Usuario user = usuarioRepository.obtenerSuperAdmin();
        model.addAttribute("usuario",superadmin);
        return "superAdmin/superPass";
    }

    public boolean verificarRequisitos(String password) {
        // Verificar al menos un número
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // Verificar al menos un carácter especial
        if (!password.matches(".*[!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*")) {
            return false;
        }

        // Verificar al menos una mayúscula
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Si la contraseña cumple con todos los requisitos, retornar true
        return true;
    }



    @PostMapping(value = "/NewPassword")
    public String newPassword(Model model, RedirectAttributes attr, @RequestParam("actual") String actual,
                              @RequestParam("nueva1") String nueva1, @RequestParam("nueva2") String nueva2,
                              HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        String pastpassw = superadmin.getContrasena();
        boolean requi = verificarRequisitos(nueva1);
        if (actual.equals("") || nueva1.equals("") || nueva2.equals("")){
            attr.addFlashAttribute("vac", "Los campos no pueden estar vacios");
            return "redirect:/superAdmin/superPass";
        }else {
            if (requi){
                BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
                System.out.println(superadmin.getContrasena());
                System.out.println(actual);
                actual =  enc.encode(actual);
                System.out.println(actual);
                nueva1 = enc.encode(nueva1);
                nueva2 = enc.encode(nueva2);
                if (pastpassw.equals(actual)){
                    if(pastpassw.equals(nueva1)){
                        attr.addFlashAttribute("msg2","La nueva contraseña debe ser diferente a la actual");
                        return "redirect:/superAdmin/superPass";
                    }else {
                        if(nueva2.equals(nueva1)){
                            attr.addFlashAttribute("msg1","Repita su nueva contraseña correctamente");
                            return "redirect:/superAdmin/superPass";
                        }else {
                            attr.addFlashAttribute("msg3","Contraseña cambiada con éxito");
                            usuarioRepository.cambiarPasswSA(nueva1,superadmin.getId());
                            return "redirect:/superAdmin/superPass";
                        }
                    }

                }else {
                    attr.addFlashAttribute("msg0","Ingrese correctamente la contraseña actual");
                    return "redirect:/superAdmin/superPass";
                }
            }else {
                attr.addFlashAttribute("req", "La nueva contraseña no cumple con las restricciones estipuladas");
                return "redirect:/superAdmin/superPass";
            }
        }
    }



    @RequestMapping(value = {"/formulario"},method = RequestMethod.GET)
    public String formulario( HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        return "superAdmin/formulario";
    }

    @RequestMapping(value = {"/reporte"},method = RequestMethod.GET)
    public String reporte( HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        return "superAdmin/reporte";
    }
    @RequestMapping(value = {"/cuestionario"},method = RequestMethod.GET)
    public String cuestionario( HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        return "cuestionario1";
    }
    @RequestMapping(value = {"/cuestionarios"},method = RequestMethod.GET)
    public String cuestionarios(Model model, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Cuestionario> listaCuestionarios = cuestionarioRepository.findAll();
        model.addAttribute("cuestionarioList", listaCuestionarios);
        return "superAdmin/cuestionarios";
    }
    @GetMapping("/cuestionarios/delete")
    public String borrarCuestionarioLleno(Model model,
                                          @RequestParam("id") int id, @RequestParam("active") boolean active,
                                          RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");

        Optional<Cuestionario> optionalCuestionario = cuestionarioRepository.findById(id);

        if (optionalCuestionario.isPresent() && active) {
            cuestionarioRepository.updateActivoByActivo(false,id);
            attr.addFlashAttribute("msg","Cuestionario borrado exitosamente");
        }
        return "redirect:/superAdmin/cuestionarios";
    }

    @RequestMapping(value = {"/editarPerfil"},method = RequestMethod.GET)
    public String editarPerfil(Model model, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        //Usuario user = usuarioRepository.obtenerSuperAdmin();
        model.addAttribute("superadmin",superadmin);
        return "superAdmin/editarPerfil";
    }

    public boolean esNumeroEntero(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



    @PostMapping(value = "/editSave/Perfil")
    public String editSuperAdmin(Model model, RedirectAttributes attr,
                              @RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,
                              @RequestParam("correo") String correo, @RequestParam("telefono") String telefono,
                              @RequestParam("dni") String dni, HttpServletRequest httpServletRequest){
    /*public String editSuperAdmin(@ModelAttribute("sA") @Valid Usuario sA,BindingResult bindingResult,
                                 Model model, RedirectAttributes attr,
                                 HttpServletRequest httpServletRequest){
        */Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        Usuario user = usuarioRepository.obtenerSuperAdmin();/*
        if(bindingResult.hasErrors()){
            attr.addFlashAttribute("msg1","Hubieron errores en el llenado de los campos");
            return "redirect:/superAdmin/confSup";
        }
        else {
            usuarioRepository.editSuperAdmin(sA.getNombre(),sA.getApellido(),sA.getEmail(),sA.getTelefono(),sA.getId(),superadmin.getId());
            attr.addFlashAttribute("msg","SuperAdmin editado exitosamente");
            return "redirect:/superAdmin/confSup";
        }
        }*/
        int c = 0;
        if(nombre.isEmpty()){
            attr.addFlashAttribute("nombremsg","El nombre no puede ser nulo");
            c = c+1;
            nombre = superadmin.getNombre();
        }
        if(apellido.isEmpty()){
            attr.addFlashAttribute("apellidomsg","El apellido no puede ser nulo");
            c = c+1;
            apellido = superadmin.getApellido();
        }
        if (correo.isEmpty()){
            attr.addFlashAttribute("correomsg","El correo no puede ser nulo");
            c = c+1;
            correo = superadmin.getEmail();
        }
        if (telefono.isEmpty()){
            attr.addFlashAttribute("telefonomsg","El teléfono no puede ser nulo");
            c = c+1;
            telefono = superadmin.getTelefono();
        }
        if (esNumeroEntero(telefono)){
            if (telefono.length()!=9){
                attr.addFlashAttribute("telefonomsg", "El número de teléfono debe tener 9 dígitos");
                c = c+1;
                telefono = superadmin.getTelefono();
            }
        }else {
            c=c+1;
            attr.addFlashAttribute("telefonomsg","El número de teléfono debe ser un número entero");
            telefono = superadmin.getTelefono();
        }
        if(dni.isEmpty()){
            attr.addFlashAttribute("dnimsg","El DNI no puede ser nulo");
            c = c+1;
            dni = superadmin.getId();
        } else if (esNumeroEntero(dni)) {
            if (dni.length()!=8) {
                attr.addFlashAttribute("dnimsg","El DNI debe tener una longitud de 8 dígitos");
                c = c+1;
                dni = superadmin.getId();
            } else {
                Optional<Usuario> u = usuarioRepository.findById(dni);
                if(u.isPresent()){
                    if(u.get().getId().equals(superadmin.getId())){

                    } else {
                        attr.addFlashAttribute("dnimsg","El DNI ya se encuentra registrado.");
                        c = c+1;
                        dni = superadmin.getId();
                    }
                }
            }
        } else {
            c=c+1;
            attr.addFlashAttribute("dnimsg","El número de DNI debe ser un número entero");
            dni = superadmin.getId();
        }
        if(c == 0){
            usuarioRepository.editSuperAdmin(nombre,apellido,correo,telefono,dni,superadmin.getId());
            Usuario nuevaUsuario = usuarioRepository.findByid(dni);
            attr.addFlashAttribute("msg","Usuario(SuperAdmin) editado exitosamente; por favor, vuelva a iniciar sesión para ver los cambios");
            httpServletRequest.getSession().setAttribute("usuario",nuevaUsuario);
            return "redirect:/superAdmin/confSup";
        }else {
            attr.addFlashAttribute("msg1","Hubieron errores en el llenado de los campos");
            return "redirect:/superAdmin/editarPerfil";
        }


    }

    @PostMapping("/editCuest")
    public String editCuest(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,
                                       @RequestParam("email") String email,
                                       @RequestParam("telefono") String telefono,
                                       @RequestParam("sede") int sede,
                                       @RequestParam("id") String dni, @RequestParam("sede") int estado, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Usuario superadmin = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        usuarioRepository.editAdmS(nombre,apellido,email,telefono,sede, estado,dni);
        attr.addFlashAttribute("msg","Administrador actualizado exitosamente");
        return "redirect:/superAdmin/dashboard";
    }
    private AuthenticationManager authenticationManager;

    public void iniciarSesion(String username, String password) {
        // Crea un objeto UsernamePasswordAuthenticationToken con las credenciales proporcionadas
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // Autentica al usuario
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Establece la autenticación en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


// ...

    private boolean isCurrentUserSuperAdmin() {
        // Obtener la autenticación actual desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verificar si la autenticación es válida y si el usuario tiene el rol de superadmin
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("superadmin"));
        }

        return false;
    }


    @PostMapping("/loginAsUser")
    public String loginAsUser(@RequestParam("userId") String userId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Validar que el usuario actual sea un superadministrador
        if (!isCurrentUserSuperAdmin()) {
            // Manejar el caso cuando el usuario actual no es un superadministrador
            return "redirect:/403.html"; // Página de acceso denegado
        }
        System.out.println(userId);
        // Obtener el usuario por su ID (userId) desde tu repositorio de usuarios
        Usuario user = usuarioRepository.findById(userId).orElse(null);

        if (user != null) {
            // Crear una implementación personalizada de UserDetails
            UserDetails userDetails = new CustomUserDetails(user);
            System.out.println("se creo userdetails");

            String hashedPassword = user.getContrasena();
            System.out.println(hashedPassword);

            // Crear una autenticación personalizada para el usuario
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, hashedPassword, userDetails.getAuthorities());
            System.out.println("se creo la autenticación sin contraseña");

            // Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("establece la autenticación");

            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("usuario", usuarioRepository.findByEmail(authentication.getName()));
            System.out.println("se establecio la sesión");
            Usuario aaaaa = (Usuario) request.getSession().getAttribute("usuario");
            System.out.println(aaaaa.getEmail());
            System.out.println(((Usuario) request.getSession().getAttribute("usuario")).getEmail());

            // Redireccionar al usuario a la página o ruta deseada según su rol
            String rol = "";
            for (GrantedAuthority role : authentication.getAuthorities()) {
                rol = role.getAuthority();
                break;
            }
            switch (rol) {
                case "paciente":
                    return "redirect:/paciente/principal";
                case "administrativo":
                    return "redirect:/administrativo/dashboard";
                case "administrador":
                    return "redirect:/administrador/principal";
                case "doctor":
                    return "redirect:/doctor/principal";
                case "superadmin":
                    return "redirect:/superAdmin/dashboard";
                default:
                    return "redirect:/"; // Redireccionar a una página predeterminada si el rol no coincide con ninguno de los casos anteriores

            }
        } else {
            // Manejar el caso cuando no se encuentra el usuario
            return "redirect:/404.html"; // Página de no encontrado
        }
    }
    @PostMapping("/guardarFoto")
    public String guardarFoto(@RequestParam("file") MultipartFile file, RedirectAttributes attr, HttpServletRequest httpServletRequest){
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(file.isEmpty()){
            attr.addFlashAttribute("foto", "Debe subir un archivo");
            return "redirect:/superAdmin/editarPerfil";
        }
        String filename = file.getOriginalFilename();
        if(filename.contains("..")){
            attr.addFlashAttribute("foto", "No se permiten caracteres especiales");
            return "redirect:/superAdmin/editarPerfil";
        }
        try{
            usuario.setFoto(file.getBytes());
            usuario.setFotonombre(filename);
            usuario.setFotocontenttype(file.getContentType());
            usuarioRepository.save(usuario);
            attr.addFlashAttribute("fotoSiu", "Foto actualizada de manera exitosa");
            return "redirect:/superAdmin/editarPerfil";
        } catch (IOException e) {
            e.printStackTrace();
            attr.addFlashAttribute("foto", "Error al intentar actualizar foto");
            return "redirect:/superAdmin/editarPerfil";
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