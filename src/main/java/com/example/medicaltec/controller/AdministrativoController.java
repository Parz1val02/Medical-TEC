package com.example.medicaltec.controller;

import com.example.medicaltec.EmailSenderService;
import com.example.medicaltec.Entity.*;

import com.example.medicaltec.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RequestMapping("/administrativo")
@Controller
public class AdministrativoController {

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
    public String dashboard(Model model){
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
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

    @RequestMapping(value = {"/form"},method = RequestMethod.GET)
    public String editForm(@RequestParam("id")String dni, Model model, @ModelAttribute("FormInvitacion")FormInvitacion formInvitacion){

        formInvitacion=formInvitationRepository.findFormbyPacient(dni);

        model.addAttribute("formInvitacion",formInvitacion);
        model.addAttribute("listaseguros",seguroRepository.findAll());
        model.addAttribute("listasedes",sedeRepository.findAll());




        return "administrativo/formListos";
    }
    //save changes
    @PostMapping(value="/editForm")
    public String editarForm(@ModelAttribute("FormInvitacion") @Valid FormInvitacion formInvitacion, BindingResult bindingResult, Model model, RedirectAttributes attr){

        if(bindingResult.hasErrors()){
            model.addAttribute("formInvitacion",formInvitacion);
            model.addAttribute("listaseguros",seguroRepository.findAll());
            model.addAttribute("listasedes",sedeRepository.findAll());

            if(formInvitacion.getDomicilio().equals("")){
                model.addAttribute("domicilioError","El campo domicilio no puede estar vacio");
            }
            if(formInvitacion.getCorreo().equals("")){
                model.addAttribute("correoError","El campo correo no puede estar vacio");
            }
            if(!isValidEmail(formInvitacion.getCorreo())){
                model.addAttribute("correoError2","El campo correo ingresado no es correcto");
            }

            if(formInvitacion.getCelular().equals("")){
                model.addAttribute("telefonoError","El campo telefono celular no puede estar vacio");
            }

            if(!isPositiveNumberWith8Digits(formInvitacion.getCelular())){
                model.addAttribute("telefonoError2","El campo telefono celular debe ser un numero de 9 digitos");
            }




            if(formInvitacion.getMedicamentos().equals("")){
                model.addAttribute("medicamentosError","El campo medicamentos no puede estar vacio, si el paciente no toma medicamentos escriba ninguno");
            }
            if(formInvitacion.getAlergias().equals("")){
                model.addAttribute("alergiaError","El campo alergias no puede estar vacio, si el paciente no presenta alergias escriba ninguna");
            }


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

    @PostMapping("/change")
    public String changePassword(@RequestParam("pass1") String pass1,
                                 @RequestParam("pass2") String pass2,
                                 @RequestParam("pass3") String pass3, RedirectAttributes attr, HttpServletRequest httpServletRequest)
    {
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if(pass1.equals("") || pass2.equals("") || pass3.equals("")){
            attr.addFlashAttribute("errorPass", "Los campos no pueden estar vacios");
            return "redirect:/administrativo/pass";
        //}else if(!pass1.equals(usuarioRepository.passAdmv())){
          //  attr.addFlashAttribute("errorPass", "La contraseña actual no coincide");
          //  return "redirect:/administrativo/pass";
        } else if (!pass3.equals(pass2) ) {
            attr.addFlashAttribute("errorPass", "Las nuevas contraseñas no son iguales");
            return "redirect:/administrativo/pass";
        }else {
            usuarioRepository.cambiarContra(new BCryptPasswordEncoder().encode(pass3), usuario.getId());
            attr.addFlashAttribute("msgContrasenia","Su contraseña ha sido cambiada exitosamente");
            return "redirect:/administrativo/perfil";
        }


    }


    @PostMapping("/enviar")
    public String enviarForm(@RequestParam("dni") String dni,
                             @RequestParam("correo") String correo,Model model,RedirectAttributes attr){

        try{
            Integer dniInteger = Integer.valueOf(dni);
        }catch (Exception e){
            attr.addFlashAttribute("errorenvio","El dni no puede contener letras y/o estar vacio");
            return "redirect:/administrativo/dashboard";
        }



        List<String> dnispacientes = usuarioRepository.obtenerdnis();
        boolean existe = false;
        for (String dni1:dnispacientes) {
            if(dni.equals(dni1)){
                existe=true;
                break;
            }
        }

        if(!existe){

            Optional<Api> apiOptional = apiRepository.findById(dni);
            if(apiOptional.isPresent()){
                Api api = apiOptional.get();
                model.addAttribute("dni",dni);
                model.addAttribute("correo",correo);
                model.addAttribute("api",api);
                model.addAttribute("listaseguros",seguroRepository.findAll());
                model.addAttribute("listasedes",sedeRepository.findAll());
                return "administrativo/formEnvio";

            }else{
                attr.addFlashAttribute("errorenvio","El dni no fue encontrado");
                return "redirect:/administrativo/dashboard";
            }




        }else{
            attr.addFlashAttribute("errorenvio","El usuario que ha invitado ya se encuentra registrado en la plataforma");
            return "redirect:/administrativo/dashboard";
        }


    }

    @PostMapping("/enviarEmail")

    public String enviarEmailPaciente (@RequestParam("id") String id,@RequestParam("nombres")String nombres, @RequestParam("apellidos")String apellidos,@RequestParam("correo")String correo){
        senderService.sendEmail(correo,
                "Invitacion paciente para la clínica telesystem" ,
                "Bienvenido(a) "+nombres +" "+ apellidos + ", usted ha sido invitado para ser parte de la plataforma telesystem \n"+
                "por tal motivo le solicitamos rellenar el formulario para completar sus datos de registro \n"+
                "");

        return "redirect:/administrativo/dashboard";
    }


    //verificar dni correcto
    public static boolean isPositiveNumberWith8Digits(String input) {
        return input.matches("\\d{9}");
    }
    //verificar email correcto
    public static boolean isValidEmail(String input) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return input.matches(emailRegex);
    }





}
