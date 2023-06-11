package com.example.medicaltec.controller;

import com.example.medicaltec.Entity.Persona;
import com.example.medicaltec.dao.PersonaDao;
import com.example.medicaltec.more.EmailSenderService;
import com.example.medicaltec.Entity.*;

import com.example.medicaltec.more.RandomLineGenerator;
import com.example.medicaltec.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("/administrativo")
@Controller
public class AdministrativoController {

    @Autowired
    PersonaDao personaDao;

    final UsuarioRepository usuarioRepository;
    final ApiRepository apiRepository;
    final SeguroRepository seguroRepository;
    final SedeRepository sedeRepository;
    final FormInvitationRepository formInvitationRepository;

    final EmailSenderService senderService;


    public AdministrativoController(UsuarioRepository usuarioRepository, ApiRepository apiRepository, SeguroRepository seguroRepository, SedeRepository sedeRepository, FormInvitationRepository formInvitationRepository, EmailSenderService senderService) {
        this.usuarioRepository = usuarioRepository;
        this.apiRepository = apiRepository;
        this.seguroRepository = seguroRepository;
        this.sedeRepository = sedeRepository;
        this.formInvitationRepository = formInvitationRepository;
        this.senderService = senderService;
    }




    @RequestMapping(value = {"/dashboard", ""},method = RequestMethod.GET)
    public String dashboard(Model model,HttpServletRequest httpServletRequest){
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Usuario> listaUsuarios1 = usuarioRepository.findAll();

        List<Usuario> listaUsuarios = new ArrayList<>();

        for(Usuario usuario : listaUsuarios1){
            if(Objects.equals(usuario.getSedesIdsedes().getId(), usuarioSession.getSedesIdsedes().getId()) && usuario.getRolesIdroles().getNombreRol().equalsIgnoreCase("paciente")){
                listaUsuarios.add(usuario);
            }
        }

        model.addAttribute("listaUsuarios",listaUsuarios);

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

        model.addAttribute("formInvitacion",formInvitacion);
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




            String domicilioError=null;
            String domiciliohack=null;
            String correoError=null;
            String correoError2=null;
            String telefonoError=null;
            String telefonoError2=null;
            String medicamentosError=null;
            String medicamentoshack=null;
            String alergiaError=null;
            String alergiaHack=null;


            if(formInvitacion.getDomicilio().equals("")){
               domicilioError= "El campo domicilio no puede estar vacio.";
               error++;
            }
            if (!noScriptPlease(formInvitacion.getDomicilio())) {
                domiciliohack ="El campo domicilio no acepta estos caracteres.";
                error++;
            }

            if(formInvitacion.getCorreo().equals("")){
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

            if(formInvitacion.getCelular().equals("")){
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




            if(formInvitacion.getMedicamentos().equals("")){
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
            }


        if(error>0){
            model.addAttribute("formInvitacion",formInvitacion);
            model.addAttribute("listaseguros",seguroRepository.findAll());
            model.addAttribute("listasedes",sedeRepository.findAll());

            model.addAttribute("domicilioError",domicilioError);
            model.addAttribute("domiciliohack",domiciliohack);
            model.addAttribute("correoError",correoError);
            model.addAttribute("correoError2",correoError2);
            model.addAttribute("telefonoError",telefonoError);
            model.addAttribute("telefonoError2",telefonoError2);
            model.addAttribute("medicamentosError",medicamentosError);
            model.addAttribute("medicamentoshack",medicamentoshack);
            model.addAttribute("alergiaError",alergiaError);
            model.addAttribute("alergiaHack",alergiaHack);


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

        return "administrativo/mensajeria";
    }

    @RequestMapping(value = {"/notificaciones"},method = RequestMethod.GET)
    public String notificaciones(){

        return "administrativo/notificaciones";
    }

    //Cambio de contraseña
    @PostMapping("/change")
    public String changePassword(@RequestParam("pass1") String pass1,
                                 @RequestParam("pass2") String pass2,
                                 @RequestParam("pass3") String pass3, RedirectAttributes attr, HttpServletRequest httpServletRequest)
    {
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        String passwordAntiguabCrypt = usuarioRepository.buscarPasswordPropioUsuario(usuarioSession.getId());
        //String pass1BCrypt = new BCryptPasswordEncoder().encode(pass1);

        boolean passwordActualCoincide = BCrypt.checkpw(pass1, passwordAntiguabCrypt);
        if(pass1.equals("") || pass2.equals("") || pass3.equals("")){
            attr.addFlashAttribute("errorPass", "Los campos no pueden estar vacios");
            return "redirect:/administrativo/pass";

        } else if (!passwordActualCoincide ) {
            attr.addFlashAttribute("errorPass", "Ocurrió un error durante el cambio de contraseña. No se aplicaron cambios.");
            return "redirect:/administrativo/pass";
        } else if (!pass3.equals(pass2) ) {
            attr.addFlashAttribute("errorPass", "Las nuevas contraseñas no coinciden");
            return "redirect:/administrativo/pass";
        }else {
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
                boolean existe1 = false;
                boolean existe2 = false;
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


                if (!existe1 && !existe2) {

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




                } else if (existe2) {

                    attr.addFlashAttribute("errorenvio", "El usuario ya ha sido invitado a la plataforma");
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

        String randomNumberStr = RandomLineGenerator.generateRandomLine(Long.parseLong(id));

        senderService.sendEmail(correo,
                "Invitacion paciente para la clínica telesystem" ,
                "Bienvenido(a) "+nombres +" "+ apellidos + ", usted ha sido invitado(a) para ser parte de la plataforma telesystem \n"+
                "por tal motivo le solicitamos rellenar el formulario para completar sus datos de registro \n"+
                "34.28.24.16:8080/registro/formPaciente/"+randomNumberStr);

                attr.addFlashAttribute("envio","El correo de invitacion fue enviado correctamente");

        return "redirect:/administrativo/dashboard";
    }

    @PostMapping("/guardarFoto")
    public String guardarFoto(@RequestParam("file") MultipartFile file, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getDeclaringClass().getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(file.isEmpty()){
            attr.addFlashAttribute("foto", "Debe subir un archivo");
            return "redirect:/administrativo/perfil";
        }
        String filename = file.getOriginalFilename();
        if(filename.contains("..")){
            attr.addFlashAttribute("foto", "No se permiten caracteres especiales");
            return "redirect:/administrativo/perfil";
        }
        try{
            usuario.setFoto(file.getBytes());
            usuario.setFotonombre(filename);
            usuario.setFotocontenttype(file.getContentType());
            usuarioRepository.save(usuario);
            attr.addFlashAttribute("fotoSiu", "Foto actualizada de manera exitosa");
            return "redirect:/administrativo/perfil";
        } catch (IOException e) {
            e.printStackTrace();
            attr.addFlashAttribute("foto", "Error al intentar actualizar foto");
            return "redirect:/administrativo/perfil";
        }
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


}
