package com.example.medicaltec.controller;

import com.example.medicaltec.Entity.Api;
import com.example.medicaltec.Entity.FormInvitacion;
import com.example.medicaltec.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/registro")
public class RegistroController {
    final ApiRepository apiRepository;
    final SeguroRepository seguroRepository;
    final SedeRepository sedeRepository;
    final FormInvitationRepository formInvitationRepository;
    final UsuarioRepository usuarioRepository;


    public RegistroController(ApiRepository apiRepository, SeguroRepository seguroRepository, SedeRepository sedeRepository, FormInvitationRepository formInvitationRepository, UsuarioRepository usuarioRepository
                              ) {
        this.apiRepository = apiRepository;
        this.seguroRepository = seguroRepository;
        this.sedeRepository = sedeRepository;
        this.formInvitationRepository = formInvitationRepository;
        this.usuarioRepository = usuarioRepository;

    }

    @GetMapping("/formPaciente/{id}")
    public String formPaciente (@PathVariable("id") String id, Model model){

        boolean existe = false;
        List<FormInvitacion> formInvitacionList = formInvitationRepository.findAll();
        for(FormInvitacion formInvitacion : formInvitacionList){
            if(id.equals(formInvitacion.getDni())){
                existe = true;
                break;
            }
        }

        if(!existe){
            Optional<Api> apiOptional = apiRepository.findById(id);

            if(apiOptional.isPresent()){

                Api api = apiOptional.get();
                model.addAttribute("api",api);
                model.addAttribute("listaseguros",seguroRepository.findAll());
                model.addAttribute("listasedes",sedeRepository.findAll());

                model.addAttribute("sedeid",1);
                model.addAttribute("sexo","");
                model.addAttribute("domicilio","");
                model.addAttribute("correo","");
                model.addAttribute("seguroid",1);
                model.addAttribute("celular","");
                model.addAttribute("medicamento","");
                model.addAttribute("alergia","");
                model.addAttribute("edad","");

                return "administrativo/registroPaciente";
            }else{
                return "auth/register";
            }

        }else{
            return "auth/register";
        }






    }

    @GetMapping("/out")
    public String salirPrincipal(){

        return "auth/login";

    }

    @PostMapping("/llenadoform")
    public String formLlenado(@RequestParam("nombres") String nombres,
                              @RequestParam("apellidos") String apellidos,
                              @RequestParam("id") String id,
                              @RequestParam("sedeid") String sedeid,
                              @RequestParam("sexo") String sexo,
                              @RequestParam("domicilio") String domicilio,
                              @RequestParam("correo")String correo,
                              @RequestParam("seguroid") String seguroid,
                              @RequestParam("celular") String celular,
                              @RequestParam("medicamento") String medicamento,
                              @RequestParam("alergia")String alergia,
                              @RequestParam("edad")String edad,Model model){

        int numErrors = 0;
        String domicilioError = null;
        String correoError = null;
        String correoError2 = null;
        String telefonoError = null;
        String telefonoError2 = null;
        String medicamentosError = null;
        String alergiaError = null;
        String edadError = null;
        String edadError1 = null;

            if(domicilio.equals("")){
                numErrors++;
                domicilioError="El campo domicilio no puede estar vacio";
    }
            if(correo.equals("")){
                numErrors++;
                correoError="El campo correo no puede estar vacio";
    }
            if(!isValidEmail(correo)){
                numErrors++;
                correoError2="El campo correo ingresado no es correcto";
    }

            if(celular.equals("")){
                numErrors++;
                telefonoError="El campo telefono celular no puede estar vacio";
    }

            if(!isPositiveNumberWith8Digits(celular)){
                numErrors++;
             telefonoError2="El campo telefono celular debe ser un numero de 9 digitos";
    }

            if(!isValidNumber(edad)){
                numErrors++;
                edadError1 = "la edad debe ser un numero";
            }
            if(edad.equals("")){
                edadError="El campo de edad no puede estar vacio";
            }




            if(medicamento.equals("")){
                numErrors++;
        medicamentosError="El campo medicamentos no puede estar vacio, si el paciente no toma medicamentos escriba ninguno";
    }
            if(alergia.equals("")){
                numErrors++;
        alergiaError="El campo alergias no puede estar vacio, si el paciente no presenta alergias escriba ninguna";
    }




            if(numErrors>0){
                Optional<Api> apiOptional = apiRepository.findById(id);
                Api api = apiOptional.get();
                model.addAttribute("api",api);
                model.addAttribute("listaseguros",seguroRepository.findAll());
                model.addAttribute("listasedes",sedeRepository.findAll());

                model.addAttribute("sedeid",sedeid);
                model.addAttribute("sexo",sexo);
                model.addAttribute("domicilio",domicilio);
                model.addAttribute("correo",correo);
                model.addAttribute("seguroid",seguroid);
                model.addAttribute("celular",celular);
                model.addAttribute("medicamento",medicamento);
                model.addAttribute("alergia",alergia);
                model.addAttribute("edad",edad);

                //msgs
                model.addAttribute("domicilioError",domicilioError);
                model.addAttribute("correoError",correoError);
                model.addAttribute("correoError2",correoError2);
                model.addAttribute("telefonoError",telefonoError);
                model.addAttribute("telefonoError2",telefonoError2);
                model.addAttribute("medicamentosError",medicamentosError);
                model.addAttribute("alergiaError",alergiaError);
                model.addAttribute("edaderror",edadError);
                model.addAttribute("edaderror1",edadError1);


                return "administrativo/registroPaciente";


            }else{

                FormInvitacion formInvitacion = new FormInvitacion();
                formInvitacion.setNombres(nombres);
                formInvitacion.setApellidos(apellidos);
                formInvitacion.setDni(id);
                formInvitacion.setSexo(sexo);
                formInvitacion.setDomicilio(domicilio);
                formInvitacion.setEdad(edad);
                formInvitacion.setIdSede(sedeid);
                formInvitacion.setCorreo(correo);
                formInvitacion.setIdSeguro(seguroid);
                formInvitacion.setCelular(celular);
                formInvitacion.setMedicamentos(medicamento);
                formInvitacion.setAlergias(alergia);


                formInvitationRepository.save(formInvitacion);



                return "administrativo/registroExito";
            }







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
    //validate age
    public static boolean isValidNumber(String input) {
        try {
            int number = Integer.parseInt(input);
            return number > 0 && number <= 120;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
