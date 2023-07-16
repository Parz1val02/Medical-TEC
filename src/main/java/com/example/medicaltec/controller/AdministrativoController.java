package com.example.medicaltec.controller;

import com.example.medicaltec.dao.PersonaDao;
import com.example.medicaltec.dto.ValidarCitaDto;
import com.example.medicaltec.funciones.Regex;
import com.example.medicaltec.more.CorreoConEstilos;
import com.example.medicaltec.more.EmailSenderService;
import com.example.medicaltec.Entity.*;

import com.example.medicaltec.more.RandomLineGenerator;
import com.example.medicaltec.repository.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequestMapping("/administrativo")
@Controller
public class AdministrativoController {

    @Autowired
    PersonaDao personaDao;


    final CorreoConEstilos correoConEstilos;
    final UsuarioRepository usuarioRepository;
    final ApiRepository apiRepository;
    final SeguroRepository seguroRepository;
    final SedeRepository sedeRepository;
    final FormInvitationRepository formInvitationRepository;
    final VerificarRepository verificarRepository;
    final EmailSenderService senderService;
    final CitaRepository citaRepository;
    final BoletaRepository boletaRepository;

    @Autowired
    private HttpServletRequest request;
    private final FormAutoregistroRepository formAutoregistroRepository;

    public AdministrativoController(CorreoConEstilos correoConEstilos, UsuarioRepository usuarioRepository, ApiRepository apiRepository, SeguroRepository seguroRepository, SedeRepository sedeRepository, FormInvitationRepository formInvitationRepository, VerificarRepository verificarRepository, EmailSenderService senderService, CitaRepository citaRepository, BoletaRepository boletaRepository,
                                    FormAutoregistroRepository formAutoregistroRepository) {
        this.correoConEstilos = correoConEstilos;
        this.usuarioRepository = usuarioRepository;
        this.apiRepository = apiRepository;
        this.seguroRepository = seguroRepository;
        this.sedeRepository = sedeRepository;
        this.formInvitationRepository = formInvitationRepository;
        this.verificarRepository = verificarRepository;
        this.senderService = senderService;
        this.citaRepository = citaRepository;
        this.boletaRepository = boletaRepository;
        this.formAutoregistroRepository = formAutoregistroRepository;
    }




    @RequestMapping(value = {"/dashboard", ""},method = RequestMethod.GET)
    public String dashboard(Model model,HttpServletRequest httpServletRequest){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Usuario> listaUsuarios1 = usuarioRepository.listarTodosUsuarios();

        List<Usuario> listaUsuarios = new ArrayList<>();

        for(Usuario usuario : listaUsuarios1){

            if(Objects.equals(usuario.getSedesIdsedes().getId(), usuarioSession.getSedesIdsedes().getId()) && usuario.getRolesIdroles().getNombreRol().equalsIgnoreCase("paciente")){
                //System.out.println(usuario.getEstadosIdestado().getNombre());
                //System.out.println(usuario.getModoregistro());
                listaUsuarios.add(usuario);
            }
        }


        model.addAttribute("listaUsuarios",listaUsuarios);

        //Sede sede = sedeRepository.findById(usuarioSession.getSedesIdsedes().getId()).orElse(null);
        //System.out.println(sede.getNombre());
        //model.addAttribute("sede",sede);
        //System.out.println(listaUsuarios.get(0).getEstadosIdestado().getNombre());

        return "administrativo/dashboard";
    }

    @RequestMapping(value = {"/perfil"},method = RequestMethod.GET)
    public String VerPerfil(Model model){
        model.addAttribute("usuario",usuarioRepository.obtenerUsuario());

        return "administrativo/miperfil";
    }
    @RequestMapping(value = {"/pass"},method = RequestMethod.GET)
    public String CambiarPassword(){

        return "administrativo/CambiarPassword";
    }
    //ver formulario llenado por el paciente invitado
    @RequestMapping(value = {"/form"},method = RequestMethod.GET)
    public String editForm(@RequestParam("id")String dni, Model model, @ModelAttribute("FormInvitacion")FormInvitacion formInvitacion){

        formInvitacion=formInvitationRepository.findFormbyPacient(dni);



        model.addAttribute("FormInvitacion",formInvitacion);
        model.addAttribute("listaseguros",seguroRepository.findAll());
        model.addAttribute("listasedes",sedeRepository.findAll());

        model.addAttribute("correoLleno",true);
        model.addAttribute("numeroLleno",true);


        return "administrativo/formListos";
    }
    //save changes
    @PostMapping(value="/editForm")
    public String editarForm(@ModelAttribute("FormInvitacion") @Valid FormInvitacion formInvitacion, BindingResult bindingResult, Model model, RedirectAttributes attr){

        int error = 0;

        //System.out.println(formInvitacion.getId());


            String domicilioError=null;
            String domiciliohack=null;
            String correoError=null;
            String correoError2=null;
            String telefonoError=null;
            String telefonoError2=null;



            if(formInvitacion.getDomicilio().length()==0){
               domicilioError= "El campo domicilio no puede estar vacio.";
               error++;
            }
            if (!noScriptPlease(formInvitacion.getDomicilio())) {
                domiciliohack ="El campo domicilio no acepta estos caracteres.";
                error++;
            }

            if(formInvitacion.getCorreo().length()==0){
                correoError="El campo correo no puede estar vacio";
                error++;
                model.addAttribute("correoLleno",false);
            }
             else{

                model.addAttribute("correoLleno",true);
            }

            if(!isValidEmail(formInvitacion.getCorreo())){
                correoError2="El campo correo ingresado no es correcto";
                error++;
            }

            if(formInvitacion.getCelular().length()==0){
                telefonoError="El campo telefono celular no puede estar vacio";
                error++;
                model.addAttribute("numeroLleno",false);
            }  else{

                model.addAttribute("numeroLleno",true);
            }


            if(!isPositiveNumberWith8Digits(formInvitacion.getCelular())){
                telefonoError2="El campo telefono celular debe ser un numero de 9 digitos";
                error++;
            }




            /*if(formInvitacion.getMedicamentos().equals("")){
                medicamentosError="El campo medicamentos no puede estar vacio, si el paciente no toma medicamentos escriba ninguno";
                error++;
            }
            if (!noScriptPlease(formInvitacion.getMedicamentos())){
                medicamentoshack="El campo medicamento no acepta dichos caracteres";
                error++;
            }

            if(formInvitacion.getAlergias().equals("")){
               alergiaError="El campo alergias no puede estar vacio, si el paciente no presenta alergias escriba ninguna";
                error++;
            }
            if (!noScriptPlease(formInvitacion.getAlergias())) {
                alergiaHack="El campo alergia no acepta estos caracteres";
                error++;
            }*/


        if(error>0){
            model.addAttribute("FormInvitacion",formInvitacion);
            model.addAttribute("listaseguros",seguroRepository.findAll());
            model.addAttribute("listasedes",sedeRepository.findAll());

            model.addAttribute("domicilioError",domicilioError);
            model.addAttribute("domiciliohack",domiciliohack);
            model.addAttribute("correoError",correoError);
            model.addAttribute("correoError2",correoError2);
            model.addAttribute("telefonoError",telefonoError);
            model.addAttribute("telefonoError2",telefonoError2);



            return "administrativo/formListos";
        }else{
            attr.addFlashAttribute("msg","Formulario editado correctamente");
           formInvitationRepository.save(formInvitacion);
            return"redirect:/administrativo/dashboard";
        }


    }
    @GetMapping("/form1")
    public String preview(Model model){
        model.addAttribute("listaseguros",seguroRepository.findAll());
        model.addAttribute("listasedes",sedeRepository.findAll());
        return "administrativo/formpre";
    }



    @RequestMapping(value = {"/mensajeria"},method = RequestMethod.GET)
    public String mensajeria(){

        return "chatroom/index";
    }

    @RequestMapping(value = {"/validarLaCita"},method = RequestMethod.GET)
    public String notificaciones(Model model, HttpServletRequest httpServletRequest){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<ValidarCitaDto> listaValidarCita = citaRepository.validarCitasList();
        ArrayList<ValidarCitaDto> listaValidarCitaFilter = new ArrayList<>();


        for(ValidarCitaDto c : listaValidarCita){

            if(Objects.equals(usuarioSession.getSedesIdsedes().getId(), c.getSedes_idsedes()) && !c.getPagada() && !c.getCitacancelada()){
                listaValidarCitaFilter.add(c);
            }
        }

        model.addAttribute("listaValidarCitaFilter",listaValidarCitaFilter);





        return "administrativo/validarCita";
    }

    //Cambio de contraseña
    @PostMapping("/change")
    public String changePassword(@RequestParam("pass1") String pass1,
                                 @RequestParam("pass2") String pass2,
                                 @RequestParam("pass3") String pass3, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication)
    {

        Regex regex = new Regex();
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        String passwordAntiguabCrypt = usuarioRepository.buscarPasswordPropioUsuario(usuarioSession.getId());
        boolean passwordActualCoincide = BCrypt.checkpw(pass1, passwordAntiguabCrypt);

        //String pass1BCrypt = new BCryptPasswordEncoder().encode(pass1);

        if(pass1.equals("") || pass2.equals("") || pass3.equals("")){
            attr.addFlashAttribute("errorPass", "Los campos no pueden estar vacios");
            return "redirect:/administrativo/pass";

        } else if (!passwordActualCoincide ) {
            attr.addFlashAttribute("errorPass", "Ocurrió un error durante el cambio de contraseña. No se aplicaron cambios.");
            return "redirect:/administrativo/pass";
        } else if (!pass3.equals(pass2) ) {
            attr.addFlashAttribute("errorPass", "Las nuevas contraseñas no coinciden");
            return "redirect:/administrativo/pass";
        } else if(!regex.contrasenaisValid(pass2)){
            attr.addFlashAttribute("errorPass", "La nueva contraseña no cumple con los requerimientos.");
            return "redirect:/administrativo/pass";
        } else {
            usuarioRepository.cambiarContra(new BCryptPasswordEncoder().encode(pass3), usuarioSession.getId());
            attr.addFlashAttribute("msgContrasenia","Su contraseña ha sido cambiada exitosamente");
            return "redirect:/administrativo/perfil";
        }


    }

    //Modal open, dni y correo
    @PostMapping("/enviar")
    public String enviarForm(@RequestParam("dni") String dni,
                             @RequestParam("correo") String correo,Model model,RedirectAttributes attr) {

        try {
            if (!isValidEmail(correo) || !isPositiveNumberWith8Digits1(dni)) {
                attr.addFlashAttribute("errorenvio", "Los datos ingresados no son correctos, el dni debe contener 8 digitos y el email el formato adecuado");

                return "redirect:/administrativo/dashboard";
            } else {


                List<String> dnispacientes = usuarioRepository.obtenerdnis();
                List<String> dnisInvitados = formInvitationRepository.dnisFormInvitacion();
                List<String> dnisAutoregistro = formAutoregistroRepository.ListaDnis();
                boolean existe1 = false;
                boolean existe2 = false;
                boolean existe3 = false;
                for (String dni1 : dnispacientes) {
                    if (dni.equals(dni1)) {
                        existe1 = true;
                        break;
                    }
                }

                for (String dni1 : dnisInvitados) {
                    if (dni.equals(dni1)) {
                        existe2 = true;
                        break;
                    }
                }
                for(String dni1 : dnisAutoregistro){
                    if(dni.equals(dni1)){
                        existe3 = true;
                        break;
                    }
                }


                if (!existe1 && !existe2 && !existe3) {


                    Persona3 persona3 = personaDao.obtenerPersona(dni);

                    if(persona3.getSuccess().equalsIgnoreCase("false")){
                        attr.addFlashAttribute("errorenvio", "Número de DNI no se encuentra en el padrón de RENIEC");
                        return "redirect:/administrativo/dashboard";
                    }else {
                        model.addAttribute("persona", personaDao.obtenerPersona(dni));
                        model.addAttribute("correo", correo);
                        model.addAttribute("dni", dni);
                        model.addAttribute("listaseguros", seguroRepository.findAll());
                        model.addAttribute("listasedes", sedeRepository.findAll());
                        return "administrativo/formEnvio";
                    }

                } else if (existe2 || existe3) {

                    attr.addFlashAttribute("errorenvio", "El usuario ya ha sido invitado y/o autoregistrado a la plataforma");
                    return "redirect:/administrativo/dashboard";

                } else {
                    attr.addFlashAttribute("errorenvio", "El usuario que ha invitado ya se encuentra registrado en la plataforma");
                    return "redirect:/administrativo/dashboard";
                }
            }
        }catch (Exception e){
            attr.addFlashAttribute("errorenvio", "Un error ha ocurrido al procesar los datos");
            return "redirect:/administrativo/dashboard";
        }







    }

    @PostMapping("/enviarEmail")

    public String enviarEmailPaciente (@RequestParam("id") String id,@RequestParam("nombres")String nombres, @RequestParam("apellidos")String apellidos,@RequestParam("correo")String correo, RedirectAttributes attr){

        // Obtener la fecha y hora actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Crear un formateador de fecha y hora con el formato deseado (d M yyyy hh mm)
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("d-M-yyyy HH-mm");

        // Formatear la fecha y hora en el formato deseado
        String fechaHoraFormateada = fechaHoraActual.format(formateador);

        //guardar fecha-hora, codigo y dni
        verificarRepository.crearInicioInvitacion(id,fechaHoraFormateada);

        String randomNumberStr = RandomLineGenerator.generateRandomLine(Long.parseLong(id));

        /*senderService.sendEmail(correo,
                "Invitacion paciente para la clínica telesystem" ,
                "Bienvenido(a) "+nombres +" "+ apellidos + ", usted ha sido invitado(a) para ser parte de la plataforma telesystem \n"+
                "por tal motivo le solicitamos rellenar el formulario para completar sus datos de registro \n"+request.getRemoteAddr()+
                ":8080/registro/formPaciente/"+randomNumberStr +"\n"+
                "Usted tiene 30 minutos desde el momento que se ha enviado este correo. Fecha Envio: "+fechaHoraFormateada);
*/
                attr.addFlashAttribute("envio","El correo de invitacion fue enviado correctamente");

        try {
            correoConEstilos.sendEmailEstilos(correo,"Invitacion paciente para la clínica telesystem",
                    "<!DOCTYPE html>\n" +
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
                            " <p style=\"margin:0;\"> Bienvenido(a) "+nombres +" "+ apellidos + ", usted ha sido invitado(a) para ser parte de la plataforma telesystem \n"+
                    "por tal motivo le solicitamos rellenar el formulario para completar sus datos de registro \n"+request.getRemoteAddr()+
                            ":8080/registro/formPaciente/"+randomNumberStr +"\n"+
                            "Usted tiene 30 minutos desde el momento que se ha enviado este correo. Fecha Envio: "+fechaHoraFormateada +" </p> " +
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
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/administrativo/dashboard";
    }


    //verificar numero correcto
    public static boolean isPositiveNumberWith8Digits(String input) {
        return input.matches("\\d{9}");
    }

    public static boolean isPositiveNumberWith8Digits1(String input) {
        return input.matches("\\d{8}");
    }

    //verificar email correcto
    public static boolean isValidEmail(String input) {
        String emailRegex = "^[A-Za-zñÑáéíóúÁÉÍÓÚ0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return input.matches(emailRegex);
    }

    public static boolean noScriptPlease(String input){
        String emailRegex = "^[A-Za-zñÑáéíóúÁÉÍÓÚ0-9\\s]+$";
        return input.matches(emailRegex);
    }


    @PostMapping("/validarPagos")
    public String pagosTarjeta(@RequestParam("citaId") String citaId,
                               @RequestParam("precio") String precio,
                               @RequestParam("concepto") String concepto,
                               @RequestParam("seguro") String seguro,
                               RedirectAttributes attr,HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        /*Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<ValidarCitaDto> listaValidarCita = citaRepository.validarCitasList();
        ArrayList<ValidarCitaDto> listaValidarCitaFilter = new ArrayList<>();


        for(ValidarCitaDto c : listaValidarCita){

            if(Objects.equals(usuarioSession.getSedesIdsedes().getId(), c.getSedes_idsedes()) && !c.getPagada() && !c.getCitacancelada()){
                listaValidarCitaFilter.add(c);
            }
        }*/
        try{
            Optional<Cita> citaOptional = citaRepository.findById(Integer.valueOf(citaId));
            if(citaOptional.isPresent()){
                Cita citaA = citaOptional.get();
                if(!citaA.getPagada() && !citaA.getCitacancelada()){
                    Double precioParsed = Double.valueOf(precio);
                    Seguro seguro1 = seguroRepository.obtenerSegurobyName(seguro);

                    Boleta boleta = new Boleta();
                    boleta.setConceptopago(concepto);
                    boleta.setMontoCita(precioParsed);
                    boleta.setCitaIdcita(citaA);
                    boleta.setSeguro(seguro1);
                    citaRepository.pagarCita(citaA.getId());

                    boletaRepository.save(boleta);

                    attr.addFlashAttribute("msg1","Cita validada correctamente");
                    return "redirect:/administrativo/validarLaCita";
                }else{
                    attr.addFlashAttribute("msg","La cita no es correcta");
                    return "redirect:/administrativo/validarLaCita";
                }
            }
        }catch (Exception e){
            attr.addFlashAttribute("msg","Hubo errores en el envio de datos");
            return "redirect:/administrativo/validarLaCita";
        }
            return "redirect:/administrativo/validarLaCita";
        }








        /*Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        try{
            Optional<Cita> cita = citaRepository.findById(Integer.parseInt(citaId));
            if(cita.isPresent()){
                Cita citaA = cita.get();
                if(Objects.equals(citaA.getPaciente().getId(), usuarioSession.getId())){
                    try{
                        String concpeto = null;
                        if(citaA.getEspecialidadesIdEspecialidad()!=null){
                            concpeto = "Consulta médica: " + citaA.getEspecialidadesIdEspecialidad().getNombreEspecialidad();
                        }else if(citaA.getExamenMedico()!=null){
                            concpeto = "Examen médico: " +  citaA.getExamenMedico().getNombre();
                        }
                        boletaRepository.crearBoletaCita(concpeto, Double.parseDouble(precio), citaA.getId(), usuarioSession.getSegurosIdSeguro().getId());
                        citaRepository.pagarCita(citaA.getId());
                        citaRepository.estadoPagada(citaA.getId());
                        attr.addFlashAttribute("exitoPagar", "Su cita se pagó de manera exitosa");
                        //Enviar correo pago con tarjeta correcto
                    }catch (NumberFormatException e){
                        attr.addFlashAttribute("errorPagar", "Monto a pagar erróneo");
                    }
                    //Enviar correo cita cancelada
                }else{
                    attr.addFlashAttribute("errorPagar", "Error al intentar pagar la cita");
                }
            }




        }catch (NumberFormatException e){
            System.out.printf(e.getMessage());
            attr.addFlashAttribute("errorPagar", "Id erróneo de cita");
        }
        return "redirect:/administrativo/";
    }*/





}
