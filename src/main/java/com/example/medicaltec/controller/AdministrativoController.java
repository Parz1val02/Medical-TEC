package com.example.medicaltec.controller;

import com.example.medicaltec.Entity.*;

import com.example.medicaltec.repository.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    public AdministrativoController(UsuarioRepository usuarioRepository, ApiRepository apiRepository, SeguroRepository seguroRepository, SedeRepository sedeRepository, FormInvitationRepository formInvitationRepository) {
        this.usuarioRepository = usuarioRepository;
        this.apiRepository = apiRepository;
        this.seguroRepository = seguroRepository;
        this.sedeRepository = sedeRepository;
        this.formInvitationRepository = formInvitationRepository;
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
    public String previewForm(@RequestParam("id")String dni, Model model, @ModelAttribute("FormInvitacion")FormInvitacion formInvitacion){

        formInvitacion=formInvitationRepository.findFormbyPacient(dni);

        model.addAttribute("usuario",formInvitacion);
        model.addAttribute("listaseguros",seguroRepository.findAll());
        model.addAttribute("listasedes",sedeRepository.findAll());




        return "administrativo/formListos";
    }
    //save changes
    @PostMapping(value="/editForm")
    public String editarForm(@ModelAttribute("FormInvitacion") FormInvitacion formInvitacion,Model model, RedirectAttributes attr){



        formInvitationRepository.save(formInvitacion);
        return"redirect:/administrativo/dashboard";
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
                                 @RequestParam("pass3") String pass3, RedirectAttributes attr)
    {

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
            usuarioRepository.cambiarContra(new BCryptPasswordEncoder().encode(pass3));
            attr.addFlashAttribute("msgContrasenia","Su contraseña ha sido cambiada exitosamente");
            return "redirect:/administrativo/perfil";
        }


    }


    @PostMapping("/enviar")
    public String enviarForm(@RequestParam("dni") String dni,
                             @RequestParam("correo") String correo,Model model,RedirectAttributes attr){

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
                return "administrativo/clash";

            }else{
                attr.addFlashAttribute("errorenvio","El dni no fue encontrado");
                return "redirect:/administrativo/dashboard";
            }




        }else{
            attr.addFlashAttribute("errorenvio","El usuario que ha invitado ya se encuentra registrado en la plataforma");
            return "redirect:/administrativo/dashboard";
        }


    }


}
