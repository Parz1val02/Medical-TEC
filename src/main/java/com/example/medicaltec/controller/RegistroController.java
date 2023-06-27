package com.example.medicaltec.controller;

import com.example.medicaltec.Entity.*;
import com.example.medicaltec.dao.PersonaDao;
import com.example.medicaltec.funciones.Regex;
import com.example.medicaltec.more.RandomLineGenerator;
import com.example.medicaltec.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

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
    final FormAutoregistroRepository formAutoregistroRepository;
    final VerificarRepository verificarRepository;

    public RegistroController(ApiRepository apiRepository, SeguroRepository seguroRepository, SedeRepository sedeRepository, FormInvitationRepository formInvitationRepository, UsuarioRepository usuarioRepository,
                              EspecialidadeRepository especialidadeRepository, FormAutoregistroRepository formAutoregistroRepository, VerificarRepository verificarRepository) {
        this.apiRepository = apiRepository;
        this.seguroRepository = seguroRepository;
        this.sedeRepository = sedeRepository;
        this.formInvitationRepository = formInvitationRepository;
        this.usuarioRepository = usuarioRepository;

        this.especialidadeRepository = especialidadeRepository;
        this.formAutoregistroRepository = formAutoregistroRepository;
        this.verificarRepository = verificarRepository;
    }

    @RequestMapping(value = {"/index"},method = RequestMethod.GET)
    public String paginaPrincipalfromRegistro(Model model){
        List<Especialidade> especialidadeList = especialidadeRepository.findAll();
        List<Sede> sedeList = sedeRepository.findAll();
        model.addAttribute("especialidadesList",especialidadeList);
        model.addAttribute("sedeList",sedeList);
        return "auth/principalpage";
    }

    @GetMapping("/formPaciente/{id}")
    public String formPaciente (@PathVariable("id") String id, Model model, RedirectAttributes attr) throws ParseException {

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


            String fecha = verificarRepository.obtenerFecha(idLongStr);

            if(!haPasadoMasDe30Minutos(fecha)){

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
                    //model.addAttribute("edad", "");
                    model.addAttribute("fecha","");
                    //model.addAttribute("codigo","");

                    return "administrativo/registroPaciente";
                }catch(Exception e){
                    attr.addFlashAttribute("error","ha ocurrido un error");
                    return "redirect:/registro/index";
                }
            }else{
                attr.addFlashAttribute("error","Han pasado mas de 30 minutos desde que la invitacion fue enviadada");
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

                              @RequestParam("fecha")String birthday,
                              //@RequestParam("codigo")String codigo,
                              Model model, RedirectAttributes attr) throws ParseException {




            Regex regex = new Regex();

            int numErrors = 0;
            String domicilioError = null;
            String domiciliohack = null;
            String correoError = null;
            String correoError2 = null;
            String telefonoError = null;
            String telefonoError2 = null;

            String birthdayerror = null;
            String birthdayerror1 = null;

            String sedeIderror = null;
            String seguroIderror = null;

            int sedeidInt = 0;
            int seguroIdInt = 0;

            if (sedeid.equalsIgnoreCase("1") || sedeid.equalsIgnoreCase("2") || sedeid.equalsIgnoreCase("3")) {
                sedeidInt = Integer.parseInt(sedeid);
            } else {
                sedeIderror = "El id de la sede enviado no es correcto";
                numErrors++;
            }

            if (seguroid.equalsIgnoreCase("1") || seguroid.equalsIgnoreCase("2") || seguroid.equalsIgnoreCase("3")
                    || seguroid.equalsIgnoreCase("4") || seguroid.equalsIgnoreCase("5") || seguroid.equalsIgnoreCase("6")
                    || seguroid.equalsIgnoreCase("7")) {
                seguroIdInt = Integer.parseInt(seguroid);
            } else {
                seguroIderror = "El id del seguro enviado no es el correcto";
            }


            if (domicilio.length() == 0) {
                numErrors++;
                domicilioError = "El campo domicilio no puede estar vacio";
            }
            if (!noScriptPlease(domicilio)) {
                domiciliohack = "El campo domicilio no acepta estos caracteres.";
                numErrors++;
            }

            if (correo.length() == 0) {
                numErrors++;
                correoError = "El campo correo no puede estar vacio";
            }
            if (!isValidEmail(correo)) {
                numErrors++;
                correoError2 = "El campo correo ingresado no es correcto";
            }

            if (celular.length() == 0) {
                numErrors++;
                telefonoError = "El campo telefono celular no puede estar vacio";
            }

            if (!isPositiveNumberWith9Digits(celular)) {
                numErrors++;
                telefonoError2 = "El campo telefono celular debe ser un numero de 9 digitos";
            }

            /*if(!isValidNumber(edad)){
                numErrors++;
                edadError1 = "la edad debe ser un numero";
            }
            if(edad.equals("")){
                edadError="El campo de edad no puede estar vacio";
            }*/




            /*if(medicamento.length()==0){
                numErrors++;
        medicamentosError="El campo medicamentos no puede estar vacio, si el paciente no toma medicamentos escriba 'ninguno'";
    }
            if(!noScriptPlease(medicamento)){
                numErrors++;
                medicamentoshack="El campo medicamento no acepta dichos caracteres";
            }

            if(alergia.length()==0){
                numErrors++;
        alergiaError="El campo alergias no puede estar vacio, si el paciente no presenta alergias escriba 'ninguna'";
    }

            if(!noScriptPlease(alergia)){
                numErrors++;
                alergiahack="El campo alergia no acepta estos caracteres";
            }*/

            /*if(!checkboxValue){
                checkBoxError="Debe aceptar los terminos y condiciones";
            }*/

            if (birthday.length() == 0) {
                numErrors++;
                birthdayerror = "El campo de fecha de nacimiento no puede ir vacio";
            }

            if (regex.fechaValid(birthday)) {


                DateTimeFormatter formatter;
                LocalDate parsedDate;
                LocalDate currentDate;


                formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                parsedDate = LocalDate.parse(birthday, formatter);
                currentDate = LocalDate.now();

                LocalDate eighteenYearsAgo = currentDate.minusYears(18);
                if (!(parsedDate.isBefore(eighteenYearsAgo) || parsedDate.isEqual(eighteenYearsAgo))) {
                    birthdayerror1 = "El usuario debe tener 18 años o más";
                    numErrors++;
                }


            } else {
                birthdayerror1 = "Ocurrio un error con la fecha de nacimiento";
                numErrors++;
            }


            if (numErrors > 0) {

                model.addAttribute("api", personaDao.obtenerPersona(id));
                model.addAttribute("listaseguros", seguroRepository.findAll());
                model.addAttribute("listasedes", sedeRepository.findAll());

                model.addAttribute("sedeid", sedeid);
                model.addAttribute("sexo", sexo);
                model.addAttribute("domicilio", domicilio);
                model.addAttribute("correo", correo);
                model.addAttribute("seguroid", seguroid);
                model.addAttribute("celular", celular);
                //model.addAttribute("medicamento",medicamento);
                //model.addAttribute("alergia",alergia);
                //model.addAttribute("edad",edad);
                model.addAttribute("fecha", birthday);

                //msgs
                model.addAttribute("domicilioError", domicilioError);
                model.addAttribute("domiciliohack", domiciliohack);
                model.addAttribute("correoError", correoError);
                model.addAttribute("correoError2", correoError2);
                model.addAttribute("telefonoError", telefonoError);
                model.addAttribute("telefonoError2", telefonoError2);
                model.addAttribute("birthdayerror", birthdayerror);
                model.addAttribute("birthdayerror1", birthdayerror1);
                model.addAttribute("sedeIderror", sedeIderror);
                model.addAttribute("seguroIderror", seguroIderror);
                //model.addAttribute("medicamentosError",medicamentosError);
                //model.addAttribute("medicamentoshack",medicamentoshack);
                //model.addAttribute("alergiaError",alergiaError);
                //model.addAttribute("edaderror",edadError);
                //model.addAttribute("edaderror1",edadError1);
                //model.addAttribute("alergiaHack",alergiahack);


                return "administrativo/registroPaciente";


            } else {

                String fecha = verificarRepository.obtenerFecha(id);
                if(!haPasadoMasDe30Minutos(fecha)){
                    FormInvitacion formInvitacion = new FormInvitacion();
                    formInvitacion.setNombres(nombres);
                    formInvitacion.setApellidos(apellidos);
                    formInvitacion.setDni(id);
                    formInvitacion.setSexo(sexo);
                    formInvitacion.setDomicilio(domicilio);
                    formInvitacion.setFechanacimiento(birthday);
                    formInvitacion.setIdSede(sedeid);
                    formInvitacion.setCorreo(correo);
                    formInvitacion.setIdSeguro(seguroid);
                    formInvitacion.setCelular(celular);


                    //Para el administrador
                    formInvitacion.setPendiente(true);

                    formInvitationRepository.save(formInvitacion);


                    attr.addFlashAttribute("success", "Su registro fue exitoso, pronto le llegará un correo con sus credenciales de acceso");
                    return "redirect:/registro/index";
                }else{
                    attr.addFlashAttribute("error", "Han pasado mas de 30 minutos desde la invitacion por correo");
                    return "redirect:/registro/index";
                }


            }







    }



    @PostMapping("/autoregistro")
    public String autoRegistro(@RequestParam("dni") String dni,
                               @RequestParam("nombres") String nombres,
                               @RequestParam("apellidos")String apellidos,
                               @RequestParam("fecha")String birthday,
                               @RequestParam("domicilio")String domicilio,
                               @RequestParam("sexo")String sexo,
                               @RequestParam("celular") String celular,
                               @RequestParam("seguroId") String seguroId,
                               @RequestParam("sedeId") String sedeId,
                               @RequestParam("correo") String correo,
                               @RequestParam("contrasenia") String contrasenia,Model model, RedirectAttributes attr){


        int sedeIdInt = 0;
        int seguroIdInt = 0;

        Regex regex = new Regex();

        int numErrors = 0;
        String domicilioError = null;
        String domiciliohack = null;
        String correoError = null;
        String correoError2 = null;
        String telefonoError = null;
        String telefonoError2 = null;

        String passwordError = null;
        String passwordValid = null;

        String birthdayerror = null;
        String birthdayerror1 = null;

        String sedeIderror = null;
        String seguroIderror = null;
        //ID SEDE Y SEGURO

        if(sedeId.equalsIgnoreCase("1") || sedeId.equalsIgnoreCase("2") || sedeId.equalsIgnoreCase("3")){
            sedeIdInt= Integer.parseInt(sedeId);
        }else{
            sedeIderror = "El id de la sede enviado no es correcto";
            numErrors++;
        }

        if(seguroId.equalsIgnoreCase("1") || seguroId.equalsIgnoreCase("2") || seguroId.equalsIgnoreCase("3")
          || seguroId.equalsIgnoreCase("4") || seguroId.equalsIgnoreCase("5") || seguroId.equalsIgnoreCase("6")
        || seguroId.equalsIgnoreCase("7")){
            seguroIdInt = Integer.parseInt(seguroId);
        }else{
            seguroIderror="El id del seguro enviado no es el correcto";
        }



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

        if(birthday.length()==0){
            numErrors++;
            birthdayerror="El campo de fecha de nacimiento no puede ir vacio";
        }

        if(regex.fechaValid(birthday)){



            DateTimeFormatter formatter;
            LocalDate parsedDate;
            LocalDate currentDate;


            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            parsedDate = LocalDate.parse(birthday, formatter);
            currentDate = LocalDate.now();

            LocalDate eighteenYearsAgo = currentDate.minusYears(18);
            if (!(parsedDate.isBefore(eighteenYearsAgo) || parsedDate.isEqual(eighteenYearsAgo))){
                birthdayerror1="El usuario debe tener 18 años o más";
                numErrors++;
            }






        }else{
            birthdayerror1="Ocurrio un error con la fecha de nacimiento";
            numErrors++;
        }



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
            model.addAttribute("fecha",birthday);
            model.addAttribute("sedeid",sedeId);
            model.addAttribute("sexo",sexo);
            model.addAttribute("domicilio",domicilio);
            model.addAttribute("correo",correo);
            model.addAttribute("seguroid",seguroId);
            model.addAttribute("celular",celular);
            model.addAttribute("contrasenia",contrasenia);

            //model.addAttribute("edad",edad);

            //msgs
            model.addAttribute("domicilioError",domicilioError);
            model.addAttribute("domiciliohack",domiciliohack);
            model.addAttribute("correoError",correoError);
            model.addAttribute("correoError2",correoError2);
            model.addAttribute("telefonoError",telefonoError);
            model.addAttribute("telefonoError2",telefonoError2);


            model.addAttribute("passwordValid",passwordValid);
            model.addAttribute("passwordError",passwordError);
            model.addAttribute("sedeIderror",sedeIderror);
            model.addAttribute("seguroIderror",seguroIderror);
            model.addAttribute("birthdayerror",birthdayerror);
            model.addAttribute("birthdayerror1",birthdayerror1);


            return "auth/register";

        }else{

            /*Usuario usuario = new Usuario();

            usuario.setId(dni);
            usuario.setNombre(nombres);
            usuario.setApellido(apellidos);
            usuario.setEdad(Integer.valueOf(edad));
            usuario.setDireccion(domicilio);
            usuario.setSexo(sexo);
            usuario.setTelefono(celular);
            usuario.setContrasena(new BCryptPasswordEncoder().encode(contrasenia));
            usuario.setEmail(correo);
            Sede sede = new Sede();
            sede.setId(sedeIdInt);
            usuario.setSedesIdsedes(sede);
            Seguro seguro = new Seguro();
            seguro.setId(seguroIdInt);
            usuario.setSegurosIdSeguro(seguro);
            Role role = new Role();
            role.setId(2);
            usuario.setRolesIdroles(role);
            usuarioRepository.save(usuario);

            attr.addFlashAttribute("success","Ha sido registrado en la plataforma con exito");*/




            FormAutoregistro formAutoregistro = new FormAutoregistro();
            formAutoregistro.setDni(dni);
            formAutoregistro.setNombres(nombres);
            formAutoregistro.setApellidos(apellidos);
            formAutoregistro.setFechanacimiento(birthday);
            formAutoregistro.setDomicilio(domicilio);
            formAutoregistro.setSexo(sexo);
            formAutoregistro.setCelular(celular);
            formAutoregistro.setSeguroid(seguroIdInt);
            formAutoregistro.setSedeid(sedeIdInt);
            formAutoregistro.setCorreo(correo);
            formAutoregistro.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));

            //Para administrador
            formAutoregistro.setPendiente(true);

            formAutoregistroRepository.save(formAutoregistro);
            attr.addFlashAttribute("success","Ha completado con éxito el formulario de autoregistro, pronto le llegará un correo de confirmación");
            return "redirect:/registro/index";





        }



    }

    @PostMapping("/verify")
    public void handleRequest(@RequestBody Map<String, String> requestParams){

       // verificarRepository.verifyRegistro(requestParams.get("parametro"));

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

    public static boolean haPasadoMasDe30Minutos(String fechaHoraFormateada) throws ParseException {
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Crear un formateador de fecha y hora con el formato deseado (d M yyyy hh mm)
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("d-M-yyyy hh-mm");

        // Formatear la fecha y hora en el formato deseado
        String fechaActualFormateada = fechaHoraActual.format(formateador);

        SimpleDateFormat formatoFecha = new SimpleDateFormat("d-M-yyyy HH-mm");

        boolean late;
        // Calcular la diferencia en minutos entre la fecha y hora actual y la fecha y hora formateada
        if((formatoFecha.parse(fechaActualFormateada).getTime() - formatoFecha.parse(fechaHoraFormateada).getTime())/(60*1000)>=30){
            late=true;
        }else{
            late=false;
        }

        // Verificar si han pasado más de 30 minutos
        return late;
    }

}
