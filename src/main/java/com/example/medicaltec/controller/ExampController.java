package com.example.medicaltec.controller;

        import com.example.medicaltec.Entity.Usuario;
        import com.example.medicaltec.repository.UsuarioRepository;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.servlet.mvc.support.RedirectAttributes;

        import java.util.List;

@Controller
public class ExampController {
    final UsuarioRepository usuarioRepository;

    public ExampController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @RequestMapping(value = {"/"},method = RequestMethod.GET)
    public String login(){
        return "auth/login";
    }

    @PostMapping(value = "/login")
    public String pagPrincipalDoctor(@RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     Model model,
                                     RedirectAttributes attr){
        boolean i=false;
        Usuario user1 = new Usuario();
        List<Usuario> userList = usuarioRepository.findAll();
        for(Usuario u : userList){
            if(u.getEmail().equals(email) && u.getContrasena().equals(password)) {
                i=true;
                user1 = u;
                break;
            }
        }
        if(i){
            switch(user1.getRolesIdroles().getNombreRol()){
                case "paciente":
                    return "redirect:/paciente/principal";
                case "administrativo":
                    return "redirect:/administrativo/dashboard";
                case "administrador":
                    return "redirect:/administrador/principal";
                case "superadmin":
                    return "redirect:/superadmin/dashboard";
                default:
                    return "redirect:/doctor/principal";
            }
        }else {
            attr.addFlashAttribute("errorLogin","Usuario y/o contraseña incorrectos");
            return "redirect:/";
        }
    }
}
