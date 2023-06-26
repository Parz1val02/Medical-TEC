package com.example.medicaltec.controller;

        import com.example.medicaltec.Entity.*;
        import com.example.medicaltec.repository.*;
        import jakarta.servlet.http.HttpSession;
        import org.springframework.http.ResponseEntity;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.servlet.mvc.support.RedirectAttributes;

        import java.util.List;

@Controller
public class ExampController {


    final UsuarioRepository usuarioRepository;
    final SedeRepository sedeRepository;
    final EspecialidadeRepository especialidadeRepository;
    final ExamenMedicoRepository examenMedicoRepository;
    final SeguroRepository seguroRepository;
    final FormInvitationRepository formInvitationRepository;

    public ExampController(UsuarioRepository usuarioRepository, SedeRepository sedeRepository, EspecialidadeRepository especialidadeRepository, ExamenMedicoRepository examenMedicoRepository, SeguroRepository seguroRepository, FormInvitationRepository formInvitationRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sedeRepository = sedeRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.examenMedicoRepository = examenMedicoRepository;
        this.seguroRepository = seguroRepository;
        this.formInvitationRepository = formInvitationRepository;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String paginaPrincipal(Model model) {
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        List<Sede> sedeList = sedeRepository.findAll();
        model.addAttribute("especialidadesList", especialidadeList);
        model.addAttribute("sedeList", sedeList);
        return "auth/principalpage";
    }

    @RequestMapping(value = {"/loginA"}, method = RequestMethod.GET)
    public String login(HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        //validando por rol para evitar que se escriba la ruta y se vaya manualmente estando autenticado a otra
        if (usuario == null) {

            return "auth/login";
        } else {
            switch (usuario.getRolesIdroles().getNombreRol()) {
                case "paciente":
                    return "redirect:/paciente/principal";
                case "administrativo":
                    return "redirect:/administrativo/dashboard";
                case "administrador":
                    return "redirect:/administrador/principal";
                case "superadmin":
                    return "redirect:/superAdmin/dashboard";
                default:
                    return "redirect:/doctor/principal";
            }//return "redirect:/paciente/principal";
        }
        //System.out.printf(usuario.getId());


    }

    @RequestMapping("/403.html")
    public String forbidden403(Model model) {
        // Add model attributes
        return "/denegado";
    }

    @GetMapping("/QR")
    public String qrcode() {
        return "/auth/genqr";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registro(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {

            model.addAttribute("nombres", "");
            model.addAttribute("apellidos", "");
            model.addAttribute("dni", "");


            model.addAttribute("sedeid", 1);
            model.addAttribute("sexo", "M");
            model.addAttribute("domicilio", "");
            model.addAttribute("correo", "");
            model.addAttribute("seguroid", 1);
            model.addAttribute("celular", "");
            model.addAttribute("contrasenia", "");

            model.addAttribute("edad", "");


            model.addAttribute("listaseguros", seguroRepository.findAll());
            model.addAttribute("listasedes", sedeRepository.findAll());
            return "auth/register";
        } else {
            return "redirect:/auth/principalpage";
        }

    }

    //@PostMapping(value = "/login")
    //public String pagPrincipalDoctor(@RequestParam("email") String email,
    //                                 @RequestParam("password") String password,
    //                                 Model model,
    //                                 RedirectAttributes attr){
    //    boolean i=false;
    //    Usuario user1 = new Usuario();
    //    List<Usuario> userList = usuarioRepository.findAll();
    //    for(Usuario u : userList){
    //        if(u.getEmail().equals(email) && u.getContrasena().equals(password)) {
    //            i=true;
    //            user1 = u;
    //            break;
    //        }
    //    }
    //    if(i){
    //        switch(user1.getRolesIdroles().getNombreRol()){
    //            case "paciente":
    //                return "redirect:/paciente/principal";
    //            case "administrativo":
    //                return "redirect:/administrativo/dashboard";
    //            case "administrador":
    //                return "redirect:/administrador/principal";
    //            case "superadmin":
    //                return "redirect:/superAdmin/dashboard";
    //            default:
    //                return "redirect:/doctor/principal";
    //        }
    //    }else {
    //        attr.addFlashAttribute("errorLogin","Usuario y/o contraseña incorrectos");
    //        return "redirect:/";
    //    }
    //}

    @GetMapping("/servicios")
    public String servicios(Model model) {
        List<ExamenMedico> examenMedicoList = examenMedicoRepository.findAll();
        model.addAttribute("examenesMedicos", examenMedicoList);
        model.addAttribute("especialidadesList", especialidadeRepository.findAll());
        return "auth/servicios";
    }

    @GetMapping("/staffmedico")
    public String staffMedico(Model model) {
        model.addAttribute("doctoresList", usuarioRepository.listarDoctores());
        model.addAttribute("segurosList", seguroRepository.findAll());
        model.addAttribute("especialidadesList", especialidadeRepository.findAll());
        return "auth/staffmedico";
    }

    @GetMapping("/ingreso")
    public String ingreso(Model model) {
        return "auth/ingreso";
    }

    @GetMapping("/ingresoUsuario")
    public String ingresoAdministrativo(@RequestParam("rol") int rolbase, RedirectAttributes attr, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/loginA";
        } else {
            if (usuario.getRolesIdroles().getId() == rolbase) {
                switch (rolbase) {
                    case 2:
                        return "redirect:/paciente/principal";
                    case 3:
                        return "redirect:/administrativo/dashboard";
                    case 4:
                        return "redirect:/administrador/principal";
                    case 5:
                        return "redirect:/superAdmin/dashboard";
                    default:
                        return "redirect:/doctor/principal";
                }
            } else {
                attr.addFlashAttribute("error", "Elección de rol equivocada. Elija una opción correcta");
                return "redirect:/ingreso";
            }
        }
    }

    //Chequear si dni existe


    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> checkDniExists(@PathVariable("dni") String dni) {


        System.out.println(dni);
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        List<FormInvitacion> invitados = formInvitationRepository.findAll();

        int i = 0;


        boolean exists=false;
        for(Usuario usuario : listaUsuarios){
            if(dni.equalsIgnoreCase(usuario.getId())){
                i++;
                break;
            }
        }

        for (FormInvitacion formInvitacion: invitados){
            if(formInvitacion.getDni().equals(dni)){
                i++;
                break;
            }
        }

        if(i!=0){
            exists=true;
        }

        return ResponseEntity.ok(exists);
    }




}
