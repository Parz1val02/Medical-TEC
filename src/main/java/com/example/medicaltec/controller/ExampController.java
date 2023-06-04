package com.example.medicaltec.controller;

        import com.example.medicaltec.Entity.Especialidade;
        import com.example.medicaltec.Entity.ExamenMedico;
        import com.example.medicaltec.Entity.Sede;
        import com.example.medicaltec.Entity.Usuario;
        import com.example.medicaltec.repository.*;
        import jakarta.servlet.http.HttpSession;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;

        import java.util.List;

@Controller
public class ExampController {


    final UsuarioRepository usuarioRepository;
    final SedeRepository sedeRepository;
    final EspecialidadeRepository especialidadeRepository;
    final ExamenMedicoRepository examenMedicoRepository;
    final SeguroRepository seguroRepository;

    public ExampController(UsuarioRepository usuarioRepository, SedeRepository sedeRepository, EspecialidadeRepository especialidadeRepository, ExamenMedicoRepository examenMedicoRepository, SeguroRepository seguroRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sedeRepository = sedeRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.examenMedicoRepository = examenMedicoRepository;
        this.seguroRepository = seguroRepository;
    }

    @RequestMapping(value = {"/"},method = RequestMethod.GET)
    public String paginaPrincipal(Model model){
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        List<Sede> sedeList = sedeRepository.findAll();
        model.addAttribute("especialidadesList",especialidadeList);
        model.addAttribute("sedeList",sedeList);
        return "auth/principalpage";
    }
    @RequestMapping(value = {"/loginA"},method = RequestMethod.GET)
    public String login(HttpSession session){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        //validando por rol para evitar que se escriba la ruta y se vaya manualmente estando autenticado a otra
        if (usuario ==null){

            return "auth/login";
        } else {
            switch(usuario.getRolesIdroles().getNombreRol()) {
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
    public String qrcode(){
        return "/auth/genqr";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registro(HttpSession session, Model model){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario ==null){
            model.addAttribute("listaseguros",seguroRepository.findAll());
            model.addAttribute("listasedes",sedeRepository.findAll());
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
    public String servicios(Model model){
        List<ExamenMedico> examenMedicoList = examenMedicoRepository.findAll();
        model.addAttribute("examenesMedicos",examenMedicoList);
        model.addAttribute("especialidadesList",especialidadeRepository.findAll());
        return "auth/servicios";
    }

    @GetMapping("/staffmedico")
    public String staffMedico(Model model){
        model.addAttribute("doctoresList",usuarioRepository.listarDoctores());
        model.addAttribute("segurosList",seguroRepository.findAll());
        model.addAttribute("especialidadesList",especialidadeRepository.findAll());
        return "auth/staffmedico";
    }
}
