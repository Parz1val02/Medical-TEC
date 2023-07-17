package com.example.medicaltec.controller;

import com.example.medicaltec.dao.PersonaDao;
import com.example.medicaltec.dto.RecetaValidarDto;
import com.example.medicaltec.dto.Sede1Dto;
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

        Sede1Dto sede = sedeRepository.sedeA(usuarioSession.getSedesIdsedes().getId());
        model.addAttribute("sede",sede);
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

            Usuario usuario = usuarioRepository.usuarioForm(formInvitacion.getDni());
            usuario.setSegurosIdSeguro(seguroRepository.seguroByID(Integer.valueOf(formInvitacion.getIdSede())));
            usuario.setSedesIdsedes(sedeRepository.sedeByID(Integer.valueOf(formInvitacion.getIdSede())));
            usuario.setDireccion(formInvitacion.getDomicilio());
            usuario.setSexo(formInvitacion.getSexo());
            usuario.setEmail(formInvitacion.getCorreo());
            usuario.setTelefono(formInvitacion.getCelular());

            usuarioRepository.save(usuario);

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

        return "administrativo/mensajeria";
    }

    @RequestMapping(value = {"/validarLaCita"},method = RequestMethod.GET)
    public String notificaciones(Model model, HttpServletRequest httpServletRequest){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<ValidarCitaDto> listaValidarCita = citaRepository.validarCitasList();
        ArrayList<ValidarCitaDto> listaValidarCitaFilter = new ArrayList<>();

        List<Boleta> listaBoletas = boletaRepository.findAll();
        List<RecetaValidarDto> listaValidarReceta = citaRepository.listaValidReceta();
        ArrayList<RecetaValidarDto> listaValidarRecetaFilter = new ArrayList<>();

        for(ValidarCitaDto c : listaValidarCita){

            if(Objects.equals(usuarioSession.getSedesIdsedes().getId(), c.getSedes_idsedes()) && !c.getPagada() && !c.getCitacancelada()){
                listaValidarCitaFilter.add(c);
            }
        }

        for(Boleta b : listaBoletas){
            for(RecetaValidarDto recetaValidarDto : listaValidarReceta){
                if(Objects.equals(b.getCitaIdcita().getId(), recetaValidarDto.getIdcita()) && b.getRecetaIdreceta()==null && recetaValidarDto.getComentario()!=null){
                    listaValidarRecetaFilter.add(recetaValidarDto);
                }
            }
        }


        model.addAttribute("listaValidarCitaFilter",listaValidarCitaFilter);
        model.addAttribute("listaValidarRecetaFilter",listaValidarRecetaFilter);




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
                            "\t\t\t\t<p><span style=\"font-weight:400\"><strong>Invitacion Paciente</strong></span></p>\n" +


                            "Bienvenido(a) "+nombres +" "+ apellidos + ", usted ha sido invitado(a) para ser parte de la plataforma telesystem \n"+
                    "por tal motivo le solicitamos rellenar el formulario para completar sus datos de registro \n"+request.getRemoteAddr()+
                            ":8080/registro/formPaciente/"+randomNumberStr +"\n"+
                            "Usted tiene 30 minutos desde el momento que se ha enviado este correo. Fecha Envio: "+fechaHoraFormateada +


                            "<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style='background-image: linear-gradient(-45deg, #014ba7, #0183d0)'>\n" +
                            "\t\t\t\t\t\t\t<tbody><tr>\n" +
                            "\t\t\t\t\t\t\t\t<td colspan=\"3\" style=\"font-size:0;height:15px;line-height:1\">&nbsp;</td>\n" +
                            "\t\t\t\t\t\t\t</tr>\n" +
                            "\t\t\t\t\t\t\t<tr>\n" +
                            "\t\t\t\t\t\t\t\t<td style=\"font-size:0;line-height:1\" width=\"50\">&nbsp;</td>\n" +
                            "\t\t\t\t\t\t\t\t<td align=\"center\" style=\"text-align:center;font-family:Arial,sans-serif;line-height:1.1\">\n" +
                            "\t\t\t\t\t\t\t\t\t<a href=\"\" style=\"color:#fff;text-decoration:none;font-weight:bold;display:inline-block;line-height:inherit\" target=\"_blank\" data-saferedirecturl=\"\">Ir a la pagina de inicio de sesión</a>\n" +
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
                               @RequestParam("dni") String dni,
                               RedirectAttributes attr){

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

                    String correo = usuarioRepository.emailFromdni(dni);


                    try {
                        correoConEstilos.sendEmailEstilos(correo,"Cita validada","<table width=\"650\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid #d8d8d8;border-collapse:collapse\">\n" +
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
                                        "\t\t\t\t<p><span style=\"font-weight:400\"><strong>Cita Validada</strong></span></p>\n" +
                                        "<p>Se ha procesado el pago de su cita por S/</p>"+precio +"\n"+
                                        "<p><strong>Pago validado</strong>n" +

                                        "Estimado usuario, su pago de receta ha sido validado.  </p> "+"Fecha cita:"+citaA.getFecha()+


                                        "<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style='background-image: linear-gradient(-45deg, #014ba7, #0183d0)'>\n" +
                                        "\t\t\t\t\t\t\t<tbody><tr>\n" +
                                        "\t\t\t\t\t\t\t\t<td colspan=\"3\" style=\"font-size:0;height:15px;line-height:1\">&nbsp;</td>\n" +
                                        "\t\t\t\t\t\t\t</tr>\n" +
                                        "\t\t\t\t\t\t\t<tr>\n" +
                                        "\t\t\t\t\t\t\t\t<td style=\"font-size:0;line-height:1\" width=\"50\">&nbsp;</td>\n" +
                                        "\t\t\t\t\t\t\t\t<td align=\"center\" style=\"text-align:center;font-family:Arial,sans-serif;line-height:1.1\">\n" +
                                        "\t\t\t\t\t\t\t\t\t<a href=\"\" style=\"color:#fff;text-decoration:none;font-weight:bold;display:inline-block;line-height:inherit\" target=\"_blank\" data-saferedirecturl=\"\">Ir a la pagina de inicio de sesión</a>\n" +
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
                                        "\t</tbody></table>"
                                );
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }



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


    @PostMapping("/validarReceta")
    public String pagosReceta(@RequestParam("citaId") String citaId,
                              @RequestParam("recetaId") String recetaId,
                              @RequestParam("precio") String precio,
                              @RequestParam("correo") String correo,
                              RedirectAttributes attr){

        try{
            Integer citaIdInteger = Integer.valueOf(citaId);
            Integer recetaIdInteger = Integer.valueOf(recetaId);
            Double precioDouble = Double.valueOf(precio);
            Optional<Cita> citaOptional = citaRepository.findById(citaIdInteger);

            if(citaOptional.isPresent()){
                Cita citaa = citaOptional.get();
                if(!citaa.getCitacancelada() && isValidEmail(correo)){
                    citaRepository.updateBoletaReceta(recetaIdInteger,precioDouble,citaa.getId());
                    attr.addFlashAttribute("msg1","El pago de la receta ha sido validado correctamente");

                    try {
                        correoConEstilos.sendEmailEstilos(correo,"Receta validada",
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
                                        "\t\t\t\t<p><span style=\"font-weight:400\"><strong>Receta Validada</strong></span></p>\n" +
                                        "<p>Se ha procesado el pago de su receta por S/</p>"+precio +"\n"+
                                        "<p><strong>Pago validado</strong>n" +

                                        "Estimado usuario, su pago de receta ha sido validado.  </p> "+"Correspondiente a la cita con fecha:"+citaa.getFecha()+


                                        "<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style='background-image: linear-gradient(-45deg, #014ba7, #0183d0)'>\n" +
                                        "\t\t\t\t\t\t\t<tbody><tr>\n" +
                                        "\t\t\t\t\t\t\t\t<td colspan=\"3\" style=\"font-size:0;height:15px;line-height:1\">&nbsp;</td>\n" +
                                        "\t\t\t\t\t\t\t</tr>\n" +
                                        "\t\t\t\t\t\t\t<tr>\n" +
                                        "\t\t\t\t\t\t\t\t<td style=\"font-size:0;line-height:1\" width=\"50\">&nbsp;</td>\n" +
                                        "\t\t\t\t\t\t\t\t<td align=\"center\" style=\"text-align:center;font-family:Arial,sans-serif;line-height:1.1\">\n" +
                                        "\t\t\t\t\t\t\t\t\t<a href=\"\" style=\"color:#fff;text-decoration:none;font-weight:bold;display:inline-block;line-height:inherit\" target=\"_blank\" data-saferedirecturl=\"\">Ir a la pagina de inicio de sesión</a>\n" +
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
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }

                    return "redirect:/administrativo/validarLaCita";
                }else{
                    attr.addFlashAttribute("msg","Hubo errores en el envio de datos");
                    return "redirect:/administrativo/validarLaCita";
                }
            }else{
                attr.addFlashAttribute("msg","Hubo errores en el envio de datos");
                return "redirect:/administrativo/validarLaCita";
            }

        }catch (Exception e){
            attr.addFlashAttribute("msg","Hubo errores en el envio de datos");
            return "redirect:/administrativo/validarLaCita";
        }

    }
    @GetMapping("/cancelarCita")
    public String cancelarCita(@RequestParam("citaId") String citaId,@RequestParam("dni") String dni,
                               RedirectAttributes attr,HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Optional<Usuario> usuario = usuarioRepository.findById(dni);
        if(usuario.isPresent()){
            Usuario usuarioSession = usuario.get();
            try{
                Optional<Cita> cita = citaRepository.findById(Integer.parseInt(citaId));
                if(cita.isPresent()){
                    Cita citaA = cita.get();
                    if(Objects.equals(citaA.getPaciente().getId(), usuarioSession.getId())){
                        citaRepository.cancelarCita(citaA.getId());
                        attr.addFlashAttribute("exitoCancelar", "Se canceló de manera exitosa la cita");
                        //Enviar correo cita cancelada
                        if(citaA.getEspecialidadesIdEspecialidad()!=null){
                            //correoConEstilos.sendEmailEstilos( usuarioSession.getEmail()   , "Cita cancelada" , "Su consulta médica agendada para la fecha " + citaA.getFecha() + " en la especialidad " + citaA.getEspecialidadesIdEspecialidad().getNombreEspecialidad() + " fue cancelada.");
                            try {
                                correoConEstilos.sendEmailEstilos(usuarioSession.getEmail(),"Cita cancelada",
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
                                                "\t\t\t\t<p><span style=\"font-weight:400\"><strong>Cita Cancelada</strong></span></p>\n" +
                                                "<p>Debido al plazo de espera de pago máximo de 10 minutos previo al inicio de la sesion vencido</p>\n" +
                                                "<p><strong>Su cita del dia de hoy ha sido cancelada</strong>, Recuerde tomar en consideracion el horario pactado para estas sesiones para asi evitar inconvenientes. </p>\n" +

                                                "Su consulta médica agendada para la fecha " + citaA.getFecha() + " en la especialidad "
                                                + citaA.getEspecialidadesIdEspecialidad().getNombreEspecialidad() + " fue cancelada."+


                                                "<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style='background-image: linear-gradient(-45deg, #014ba7, #0183d0)'>\n" +
                                                "\t\t\t\t\t\t\t<tbody><tr>\n" +
                                                "\t\t\t\t\t\t\t\t<td colspan=\"3\" style=\"font-size:0;height:15px;line-height:1\">&nbsp;</td>\n" +
                                                "\t\t\t\t\t\t\t</tr>\n" +
                                                "\t\t\t\t\t\t\t<tr>\n" +
                                                "\t\t\t\t\t\t\t\t<td style=\"font-size:0;line-height:1\" width=\"50\">&nbsp;</td>\n" +
                                                "\t\t\t\t\t\t\t\t<td align=\"center\" style=\"text-align:center;font-family:Arial,sans-serif;line-height:1.1\">\n" +
                                                "\t\t\t\t\t\t\t\t\t<a href=\"\" style=\"color:#fff;text-decoration:none;font-weight:bold;display:inline-block;line-height:inherit\" target=\"_blank\" data-saferedirecturl=\"\">Ir a la pagina de inicio de sesión</a>\n" +
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
                            } catch (MessagingException e) {
                                throw new RuntimeException(e);
                            }
                        }else if(citaA.getExamenMedico()!=null){
                            //correoConEstilos.sendEmailEstilos( usuarioSession.getEmail()   , "Cita cancelada" , "Su examen médico agendado para la fecha " + citaA.getFecha() + " en la especialidad " + citaA.getExamenMedico().getNombre() + " fue cancelado.");
                            try {
                                correoConEstilos.sendEmailEstilos(usuarioSession.getEmail(),"Cita cancelada",
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
                                                "\t\t\t\t<p><span style=\"font-weight:400\"><strong>Cita Cancelada</strong></span></p>\n" +
                                                "<p>Debido al plazo de espera de pago máximo de 10 minutos previo al inicio de la sesion vencido</p>\n" +
                                                "<p><strong>Su cita del dia de hoy ha sido cancelada</strong>, Recuerde tomar en consideracion el horario pactado para estas sesiones para asi evitar inconvenientes. </p>\n" +

                                                "Su examen médico agendado para la fecha  "+citaA.getFecha() +
                                                " en la especialidad "+ citaA.getExamenMedico().getNombre() + ", fue cancelado \n" +


                                                "<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style='background-image: linear-gradient(-45deg, #014ba7, #0183d0)'>\n" +
                                                "\t\t\t\t\t\t\t<tbody><tr>\n" +
                                                "\t\t\t\t\t\t\t\t<td colspan=\"3\" style=\"font-size:0;height:15px;line-height:1\">&nbsp;</td>\n" +
                                                "\t\t\t\t\t\t\t</tr>\n" +
                                                "\t\t\t\t\t\t\t<tr>\n" +
                                                "\t\t\t\t\t\t\t\t<td style=\"font-size:0;line-height:1\" width=\"50\">&nbsp;</td>\n" +
                                                "\t\t\t\t\t\t\t\t<td align=\"center\" style=\"text-align:center;font-family:Arial,sans-serif;line-height:1.1\">\n" +
                                                "\t\t\t\t\t\t\t\t\t<a href=\"\" style=\"color:#fff;text-decoration:none;font-weight:bold;display:inline-block;line-height:inherit\" target=\"_blank\" data-saferedirecturl=\"\">Ir a la pagina de inicio de sesión</a>\n" +
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
                            } catch (MessagingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }else{
                        attr.addFlashAttribute("errorCancelar", "Error al intentar cancelar la cita");
                    }
                }
            }catch (NumberFormatException e){
                System.out.printf(e.getMessage());
                attr.addFlashAttribute("errorCancelar", "Id erróneo de cita");
            }
        }else{
            attr.addFlashAttribute("errorCancelar", "Id erróneo de usuario");
        }
        return "redirect:/administrativo/validarLaCita";
    }



}
