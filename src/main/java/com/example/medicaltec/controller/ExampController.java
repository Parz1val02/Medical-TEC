package com.example.medicaltec.controller;

import com.example.medicaltec.Entity.*;
import com.example.medicaltec.funciones.Regex;
import com.example.medicaltec.more.CorreoConEstilos;
import com.example.medicaltec.more.RandomLineGenerator;
import com.example.medicaltec.repository.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class ExampController {

    final CorreoConEstilos correoConEstilos;
    final UsuarioRepository usuarioRepository;
    final SedeRepository sedeRepository;
    final EspecialidadeRepository especialidadeRepository;
    final ExamenMedicoRepository examenMedicoRepository;
    final SeguroRepository seguroRepository;
    final FormInvitationRepository formInvitationRepository;
    final CambioContraseniaRepository cambioContraseniaRepository;


    public ExampController(CorreoConEstilos correoConEstilos, UsuarioRepository usuarioRepository, SedeRepository sedeRepository, EspecialidadeRepository especialidadeRepository, ExamenMedicoRepository examenMedicoRepository, SeguroRepository seguroRepository, FormInvitationRepository formInvitationRepository, CambioContraseniaRepository cambioContraseniaRepository) {
        this.correoConEstilos = correoConEstilos;
        this.usuarioRepository = usuarioRepository;
        this.sedeRepository = sedeRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.examenMedicoRepository = examenMedicoRepository;
        this.seguroRepository = seguroRepository;
        this.formInvitationRepository = formInvitationRepository;
        this.cambioContraseniaRepository = cambioContraseniaRepository;
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

    @RequestMapping("/error404")
    public String pagenotfound404(Model model) {
        // Add model attributes
        return "/error404";
    }

    /*@GetMapping("/QR")
    public String qrcode() {
        return "/auth/genqr";
    }*/

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
            return "redirect:/";
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


    //***************************************************
    //Olvidar contraseña, validar correo, validar codigo enviado a correo, validar nueva contraseña

    //para la vista de la pagina de cambiar contraseña
    @GetMapping("/cambiarcontrasena")
    public String cambiarContrasena(){

        //Usuario usuario = (Usuario) session.getAttribute("usuario");



        return "auth/correoForContrasenia";



    }

    @PostMapping("/correoValido")
    public String validarCorreo(Model model, @RequestParam("correo") String correo){



        Regex regex = new Regex();
        String correoError = null;
        boolean correoExiste = false;
        String correoExisteMsg = null;

        if(!(regex.emailValid(correo))){
            correoError = "El correo ingresado no es valido";
        }

        for(Usuario usuario : usuarioRepository.findAll()){

            if(usuario.getEmail().equalsIgnoreCase(correo)){
                correoExiste = true;
                break;
            }


        }

        if(!correoExiste){
            correoExisteMsg = "El correo ingresado no se encuentra registrado en la plataforma";
        }


        if(correoError==null && correoExisteMsg==null) {
            model.addAttribute("correo",correo);


            String codigo = generateRandomCode(6);

            CambioContrasenia cambioContrasenia = new CambioContrasenia();
            cambioContrasenia.setCorreo(correo);
            LocalDateTime fechaHoraActual = LocalDateTime.now();
            DateTimeFormatter formateador = DateTimeFormatter.ofPattern("d-M-yyyy HH:mm");
            String fechaHoraFormateada = fechaHoraActual.format(formateador);
            cambioContrasenia.setFecha(fechaHoraFormateada);
            cambioContrasenia.setCodigo(codigo);
            cambioContraseniaRepository.save(cambioContrasenia);

            try {


                correoConEstilos.sendEmailEstilos(correo, "Codigo para cambiar contraseña",
                        "<table width=\"650\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #d8d8d8;border-collapse:collapse\">\n" +
                                "\t\t<tbody><tr>\n" +
                                "          <td valign=\"top\" bgcolor=\"#FFFFFF\" style=\"background:#fff;line-height:0\">\n" +
                                "\t\t\t\t<img alt=\"x\" src=\"https://res.cloudinary.com/dtnko1xwm/image/upload/v1689559827/medical-logo-cloud_ketqww.jpg\" width=\"650\" class=\"CToWUd\" data-bit=\"iit\">\n" +
                                "\t\t\t</td>\n" +
                                "\t\t</tr>\n" +
                                "\t\t<tr>\n" +
                                "\t\t\t<td valign=\"top\" bgcolor=\"#FFFFFF\" style=\"background:#fff;line-height:0\">\n" +
                                "\t\t\t</td>\n" +
                                "\t\t</tr>\n" +
                                "      \t\n" +
                                "\t\t<tr>\n" +
                                "\t\t\t<td valign=\"top\" bgcolor=\"#fff\" style=\"background:#fff;padding-right:30px;padding-left:30px;padding-bottom:25px;font-size:15px;font-family:Arial;color:#000;line-height:22px\">\n" +
                                "\t\t\t\t<p><span style=\"font-weight:400\"><strong>Codigo de verificacion</strong></span></p>\n" +
                                "<p>Con este codigo podra continuar el proceso para cambiar su contraseña</p>\n" +
                                "<p><strong>Recuerde que solo es válido por 30 minutos</strong>,Pasado ese tiempo tendra que solicitar un nuevo código si desea cambiar contraseña </p>\n" +

                                "Su codigo para continuar con el cambio de contraseña es: " + codigo + " </p> " +


                                "<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style='background-image: linear-gradient(-45deg, #014ba7, #0183d0)'>\n" +
                                "\t\t\t\t\t\t\t<tbody><tr>\n" +
                                "\t\t\t\t\t\t\t\t<td colspan=\"3\" style=\"font-size:0;height:15px;line-height:1\">&nbsp;</td>\n" +
                                "\t\t\t\t\t\t\t</tr>\n" +
                                "\t\t\t\t\t\t\t<tr>\n" +
                                "\t\t\t\t\t\t\t\t<td style=\"font-size:0;line-height:1\" width=\"50\">&nbsp;</td>\n" +
                                "\t\t\t\t\t\t\t\t<td align=\"center\" style=\"text-align:center;font-family:Arial,sans-serif;line-height:1.1\">\n" +

                                "\t\t\t\t\t\t\t\t</td>\n" +
                                "\t\t\t\t\t\t\t\t<td style=\"font-size:0;line-height:1\" width=\"50\">&nbsp;</td>\n" +
                                "\t\t\t\t\t\t\t</tr>\n" +
                                "\t\t\t\t\t\t\t<tr>\n" +
                                "\t\t\t\t\t\t\t\t<td colspan=\"3\" style=\"font-size:0;height:15px;line-height:1\">&nbsp;</td>\n" +
                                "\t\t\t\t\t\t\t</tr>\n" +
                                "\t\t\t\t\t\t</tbody></table>\n" +
                                "\t\t\t\t\t\t<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                                "\t\t\t\t\t\t\t<tbody><tr>\n" +
                                "\t\t\t\t\t\t\t\t<td style=\"font-size:0;height:20px;line-height:1\">&nbsp;</td>\n" +
                                "\t\t\t\t\t\t\t</tr>\n" +
                                "\t\t\t\t\t\t</tbody></table>\n" +
                                "\t\t\t\t\t\t<p><span style=\"font-weight:400\">Atentamente</span></p>\n" +
                                "<p><strong>Clínica Medical-Tec</strong></p>\n" +
                                "\n" +
                                "\t        </td>\n" +
                                "\t\t</tr>\n" +
                                "\t\t<tr>\n" +
                                "\t\t\t<td bgcolor=\"\" style='background-image: linear-gradient(-45deg, #014ba7, #0183d0)' styleheight=\"25\"> </td>\n" +
                                "\t\t</tr>\n" +
                                "\t</tbody></table>");
            }
         catch (MessagingException e) {
            // Manejar la excepción en caso de que ocurra un error al enviar el correo
            e.printStackTrace();
            return "redirect:/cambiarcontrasena";
        }
            String dni = usuarioRepository.dniFromCorreo(correo);
            String randomNumberStr = RandomLineGenerator.generateRandomLine(Long.parseLong(dni));


            return "redirect:/codeCorreo/"+randomNumberStr;
        }else{
            model.addAttribute("correoError",correoError);
            model.addAttribute("correoExisteMsg",correoExisteMsg);
            return "auth/correoForContrasenia";
        }


    }


    @GetMapping("/codeCorreo/{random}")
    public String codigoEnviadoFromCorreo (@PathVariable("random") String random,Model model){

        long idLong = RandomLineGenerator.convertToNumber(random);
        String idLongStr = String.valueOf(idLong);

        model.addAttribute("dni",idLongStr);

        return "auth/codigoFromCorreo";
    }

    @PostMapping("/codigoCorreo")
    public String codigoFromCorreoValidation (@RequestParam("dni") String dni,
                                              @RequestParam("codigo") String codigo,
                                              RedirectAttributes attr,
                                              Model model){

        String codigoValidoMsg ;

        String correo = usuarioRepository.correoFromDni(dni);


        List<CambioContrasenia> cambioContraseniaList = cambioContraseniaRepository.findAll();

        ArrayList<String> fechasCorreo = new ArrayList<>();
        for(CambioContrasenia cambioContrasenia : cambioContraseniaList){
            if(cambioContrasenia.getCorreo().equals(correo)){
                fechasCorreo.add(cambioContrasenia.getFecha());
            }
        }
        LocalDateTime fechaMasReciente = null;

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("d-M-yyyy HH:mm");
        for (String fechaString : fechasCorreo) {
            LocalDateTime fecha = LocalDateTime.parse(fechaString, formateador);
            if (fechaMasReciente == null || fecha.isAfter(fechaMasReciente)) {
                fechaMasReciente = fecha;
            }
        }

        String fechaMasRecienteFormateada = fechaMasReciente.format(formateador);
        LocalDateTime fechaHora = parseFechaHora(fechaMasRecienteFormateada);



        if(!(hanPasadoMasDe30Minutos(fechaHora))){
            if(codigo.equals(cambioContraseniaRepository.codigoByFecha(fechaMasRecienteFormateada))){


                String randomNumberStr = RandomLineGenerator.generateRandomLine(Long.parseLong(dni));


                return "redirect:/cambiarPassword/"+randomNumberStr;

            }else{
                codigoValidoMsg = "El codigo no es válido, recuerde que la validez del codigo es de 30 minutos, de lo contrario vuelva a solicitar el cambio de contraseña";
                model.addAttribute("codigoValidoMsg",codigoValidoMsg);
                model.addAttribute("dni",dni);

                return "auth/codigoFromCorreo";
            }
        }else {
            attr.addFlashAttribute("minutos","Han pasado mas de 30 minutos desde el envio del codigo a su correo, por favor solicite uno nuevo");
            return "redirect:/cambiarcontrasena";

        }






    }


    @GetMapping("/cambiarPassword/{random}")
    public String goChangePassword (@PathVariable("random") String random, Model model){

        long idLong = RandomLineGenerator.convertToNumber(random);
        String idLongStr = String.valueOf(idLong);

        model.addAttribute("dni",idLongStr);


        return "auth/cambiarcontrasena";

    }



    @PostMapping("/validacionCambiar")
    public String cambiandoContrasenia (Model model,
                               @RequestParam("pass1") String pass1,
                               @RequestParam("pass2") String pass2,
                               @RequestParam("pass3") String pass3,
                               @RequestParam("dni") String dni,
                               RedirectAttributes attr){

        String randomNumberStr = RandomLineGenerator.generateRandomLine(Long.parseLong(dni));

        String passwordAntiguabCrypt = usuarioRepository.buscarPasswordPropioUsuario(dni);
        boolean passwordActualCoincide = BCrypt.checkpw(pass1, passwordAntiguabCrypt);

        if(pass1.equals("") || pass2.equals("") || pass3.equals("")){
            attr.addFlashAttribute("errorPass", "Los campos no pueden estar vacios");



            return "auth/cambiarcontrasena";
        }else if (!passwordActualCoincide ) {
            attr.addFlashAttribute("errorPass", "Ocurrió un error durante el cambio de contraseña. No se aplicaron cambios.");

            return "auth/cambiarcontrasena";
        } else if (!pass3.equals(pass2) ) {
            attr.addFlashAttribute("errorPass", "Las nuevas contraseñas no coinciden");
            return "auth/cambiarcontrasena";
        }else {
            usuarioRepository.cambiarContra(new BCryptPasswordEncoder().encode(pass3), dni);
            attr.addFlashAttribute("msgContrasenia","Su contraseña ha sido cambiada exitosamente");
            return "redirect:/loginA";



    }

    }

    //************************************************************


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






        public static String generateRandomCode(int length) {
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            StringBuilder randomCode = new StringBuilder();

            Random random = new Random();
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(characters.length());
                char randomChar = characters.charAt(index);
                randomCode.append(randomChar);
            }

            return randomCode.toString();
        }

    public static LocalDateTime parseFechaHora(String fechaHoraString) {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("d-M-yyyy HH:mm");
        return LocalDateTime.parse(fechaHoraString, formateador);
    }

    public static boolean hanPasadoMasDe30Minutos(LocalDateTime fechaHora) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fechaHoraMas30Minutos = fechaHora.plusMinutes(30);
        return ahora.isAfter(fechaHoraMas30Minutos);
    }

}
