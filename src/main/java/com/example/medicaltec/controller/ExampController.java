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


                correoConEstilos.sendEmailEstilos(correo, "Codigo para validar contraseña", "<!DOCTYPE html>\n" +
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
                        " <p style=\"margin:0;\"> Su codigo para continuar con el cambio de contraseña es: " + codigo + " </p> " +
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
                        "</html>");
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
