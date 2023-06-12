package com.example.medicaltec.controller;

import com.example.medicaltec.Entity.*;
import com.example.medicaltec.dao.PersonaDao;
import com.example.medicaltec.funciones.Regex;
import com.example.medicaltec.more.RandomLineGenerator;
import com.example.medicaltec.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    PersonaDao personaDao;
    final ApiRepository apiRepository;
    final SeguroRepository seguroRepository;
    final SedeRepository sedeRepository;
    final FormInvitationRepository formInvitationRepository;
    final UsuarioRepository usuarioRepository;
    final EspecialidadeRepository especialidadeRepository;


    public RegistroController(ApiRepository apiRepository, SeguroRepository seguroRepository, SedeRepository sedeRepository, FormInvitationRepository formInvitationRepository, UsuarioRepository usuarioRepository,
                              EspecialidadeRepository especialidadeRepository) {
        this.apiRepository = apiRepository;
        this.seguroRepository = seguroRepository;
        this.sedeRepository = sedeRepository;
        this.formInvitationRepository = formInvitationRepository;
        this.usuarioRepository = usuarioRepository;

        this.especialidadeRepository = especialidadeRepository;
    }

    @RequestMapping(value = {"/index"},method = RequestMethod.GET)
    public String paginaPrincipalfromRegistro(Model model){
        List<Usuario> usuarioList = usuarioRepository.listarDoctores();
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        List<Sede> sedeList = sedeRepository.findAll();
        model.addAttribute("usuarioList",usuarioList);
        model.addAttribute("especialidadesList",especialidadeList);
        model.addAttribute("sedeList",sedeList);
        return "auth/principalpage";
    }

    @GetMapping("/formPaciente/{id}")
    public String formPaciente (@PathVariable("id") String id, Model model, RedirectAttributes attr){

        long idLong = RandomLineGenerator.convertToNumber(id);
        String idLongStr = String.valueOf(idLong);

        boolean existe = false;
        List<FormInvitacion> formInvitacionList = formInvitationRepository.findAll();
        for(FormInvitacion formInvitacion : formInvitacionList){
            if(idLongStr.equals(formInvitacion.getDni())){
                existe = true;
                break;
            }
        }
        //valido si el usuario no fue invitado antes
        if(!existe){

            //valido si el dni brindado se encuentra en la tabla api
            try {



                model.addAttribute("api",personaDao.obtenerPersona(idLongStr) );
                model.addAttribute("listaseguros", seguroRepository.findAll());
                model.addAttribute("listasedes", sedeRepository.findAll());

                model.addAttribute("sedeid", 1);
                model.addAttribute("sexo", "");
                model.addAttribute("domicilio", "");
                model.addAttribute("correo", "");
                model.addAttribute("seguroid", 1);
                model.addAttribute("celular", "");
                model.addAttribute("medicamento", "");
                model.addAttribute("alergia", "");
                model.addAttribute("edad", "");

                return "administrativo/registroPaciente";
            }catch(Exception e){
                attr.addFlashAttribute("error","ha ocurrido un error");
                return "redirect:/registro/index";
            }

        }else{
            attr.addFlashAttribute("error","el usuario ya fue invitado a la plataforma");
            return "redirect:/registro/index";
        }






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
                              @RequestParam("edad")String edad,

                              Model model, RedirectAttributes attr){

        int numErrors = 0;
        String domicilioError = null;
        String domiciliohack = null;
        String correoError = null;
        String correoError2 = null;
        String telefonoError = null;
        String telefonoError2 = null;
        String medicamentosError = null;
        String medicamentoshack = null;
        String alergiaError = null;
        String alergiahack = null;
        String edadError = null;
        String edadError1 = null;


            if(domicilio.equals("")){
                numErrors++;
                domicilioError="El campo domicilio no puede estar vacio";
    }
            if(!noScriptPlease(domicilio)){
                domiciliohack ="El campo domicilio no acepta estos caracteres.";
                numErrors++;
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

            if(!isPositiveNumberWith9Digits(celular)){
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
        medicamentosError="El campo medicamentos no puede estar vacio, si el paciente no toma medicamentos escriba 'ninguno'";
    }
            if(!noScriptPlease(medicamento)){
                numErrors++;
                medicamentoshack="El campo medicamento no acepta dichos caracteres";
            }

            if(alergia.equals("")){
                numErrors++;
        alergiaError="El campo alergias no puede estar vacio, si el paciente no presenta alergias escriba 'ninguna'";
    }

            if(!noScriptPlease(alergia)){
                numErrors++;
                alergiahack="El campo alergia no acepta estos caracteres";
            }

            /*if(!checkboxValue){
                checkBoxError="Debe aceptar los terminos y condiciones";
            }*/




            if(numErrors>0){

                model.addAttribute("api",personaDao.obtenerPersona(id));
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
                model.addAttribute("domiciliohack",domiciliohack);
                model.addAttribute("correoError",correoError);
                model.addAttribute("correoError2",correoError2);
                model.addAttribute("telefonoError",telefonoError);
                model.addAttribute("telefonoError2",telefonoError2);
                model.addAttribute("medicamentosError",medicamentosError);
                model.addAttribute("medicamentoshack",medicamentoshack);
                model.addAttribute("alergiaError",alergiaError);
                model.addAttribute("edaderror",edadError);
                model.addAttribute("edaderror1",edadError1);
                model.addAttribute("alergiaHack",alergiahack);



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

                //Para el administrador
                formInvitacion.setPendiente(true);

                formInvitationRepository.save(formInvitacion);



                attr.addFlashAttribute("success","Su registro fue exitoso, pronto le llegará un correo con sus credenciales de acceso");
                return "redirect:/registro/index";
            }







    }



    @PostMapping("/autoregistro")
    public String autoRegistro(@RequestParam("dni") String dni,
                               @RequestParam("nombres") String nombres,
                               @RequestParam("apellidos")String apellidos,
                               @RequestParam("edad")String edad,
                               @RequestParam("domicilio")String domicilio,
                               @RequestParam("sexo")String sexo,
                               @RequestParam("celular") String celular,
                               @RequestParam("seguroId") String seguroId,
                               @RequestParam("sedeId") String sedeId,
                               @RequestParam("correo") String correo,
                               @RequestParam("contrasenia") String contrasenia,Model model, RedirectAttributes attr){


        int numErrors = 0;
        String domicilioError = null;
        String domiciliohack = null;
        String correoError = null;
        String correoError2 = null;
        String telefonoError = null;
        String telefonoError2 = null;
        String edadError = null;
        String edadError1 = null;

        String passwordError = null;
        String passwordValid = null;



        if(domicilio.equalsIgnoreCase("")){
            domicilioError = "El campo domicilio no puede ir vacio";
            numErrors++;
        }
        if(!noScriptPlease(domicilio)){
            domiciliohack = "El campo domicilio no acepta esos caracteres";
            numErrors++;
        }
        if(correo.equalsIgnoreCase("")){
            correoError = "El campo correo no puede ir vacio";
            numErrors++;
        }
        if(!isValidEmail(correo)){
            correoError2 = "El campo correo no acepta ese formato";
            numErrors++;
        }
        if(celular.equalsIgnoreCase("")){
            telefonoError = "El campo celular no puede ir vacio";
            numErrors++;
        }
        if(!noScriptPlease(celular)){
            telefonoError2 = "El campo celular acepta esos caracteres";
            numErrors++;
        }

        if(!isValidNumber(edad)){
            numErrors++;
            edadError1 = "la edad debe ser un numero";
        }
        if(edad.equals("")){
            edadError="El campo de edad no puede estar vacio";
            numErrors++;
        }

        Regex regex = new Regex();

        if(!regex.contrasenaisValid(contrasenia)){
            passwordValid="La contraseña no cumple con los parametros establecidos";
            numErrors++;
        }

        if(contrasenia.equalsIgnoreCase("")){
            passwordError="El campo contraseña no puede estar vacio";
            numErrors++;
        }

        if(numErrors>0){
            model.addAttribute("nombres",nombres);
            model.addAttribute("apellidos",apellidos);
            model.addAttribute("dni",dni);
            model.addAttribute("listaseguros",seguroRepository.findAll());
            model.addAttribute("listasedes",sedeRepository.findAll());

            model.addAttribute("sedeid",sedeId);
            model.addAttribute("sexo",sexo);
            model.addAttribute("domicilio",domicilio);
            model.addAttribute("correo",correo);
            model.addAttribute("seguroid",seguroId);
            model.addAttribute("celular",celular);
            model.addAttribute("contrasenia",contrasenia);

            model.addAttribute("edad",edad);

            //msgs
            model.addAttribute("domicilioError",domicilioError);
            model.addAttribute("domiciliohack",domiciliohack);
            model.addAttribute("correoError",correoError);
            model.addAttribute("correoError2",correoError2);
            model.addAttribute("telefonoError",telefonoError);
            model.addAttribute("telefonoError2",telefonoError2);

            model.addAttribute("edaderror",edadError);
            model.addAttribute("edaderror1",edadError1);
            model.addAttribute("passwordValid",passwordValid);
            model.addAttribute("passwordError",passwordError);

            return "auth/register";

        }else{

            Usuario usuario = new Usuario();

            usuario.setId(dni);
            usuario.setNombre(nombres);
            usuario.setApellido(apellidos);
            usuario.setEdad(Integer.valueOf(edad));
            usuario.setDireccion(domicilio);
            usuario.setSexo(sexo);
            usuario.setTelefono(celular);
            usuario.setContrasena(contrasenia);
            usuario.setEmail(correo);

            Role role = new Role();
            role.setId(2);
            usuario.setRolesIdroles(role);
            usuarioRepository.save(usuario);

            attr.addFlashAttribute("success","Ha sido registrado en la plataforma con exito");
            return "redirect:/registro/index";

        }



    }

    //verificar numero correcto
    public static boolean isPositiveNumberWith9Digits(String input) {
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

    public static boolean noScriptPlease(String input){
        String emailRegex = "^[A-Za-zñÑáéíóúÁÉÍÓÚ0-9\\s]+$";
        return input.matches(emailRegex);
    }



}
