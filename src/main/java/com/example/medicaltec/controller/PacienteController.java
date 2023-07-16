package com.example.medicaltec.controller;

import com.example.medicaltec.dto.*;
import com.example.medicaltec.funciones.Regex;
import com.example.medicaltec.Entity.*;
import com.example.medicaltec.more.CorreoConEstilos;
import com.example.medicaltec.repository.HistorialMedicoRepository;
import com.example.medicaltec.repository.TipoCitaRepository;
import com.example.medicaltec.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    final HistorialMedicoRepository historialMedicoRepository;
    final SedeRepository sedeRepository;
    final SeguroRepository seguroRepository;

    final EspecialidadRepository especialidadRepository;

    final AlergiaRepository alergiaRepository;

    final BoletaRepository boletaRepository;

    final UsuarioRepository usuarioRepository;

    final RolesRepository rolesRepository;
    final TipoCitaRepository tipoCitaRepository;
    final CitaRepository citaRepository;

    final MedicamentoRepository medicamentoRepository;

    final PreguntaRepository preguntaRepository;

    final CuestionarioRepository cuestionarioRepository;
    final RptaRepository rptaRepository;
    final HistorialMedicoHasAlergiaRepository historialMedicoHasAlergiaRepository;

    final RecetaHasMedicamentoRepository recetaHasMedicamentoRepository;
    final RecetaRepository recetaRepository;
    final SedeHasEspecialidadeRepository sedeHasEspecialidadeRepository;
    final ExamenMedicoRepository examenMedicoRepository;
    final HorasDoctorRepository horasDoctorRepository;
    final DeliverymedicamentoRepository deliverymedicamentoRepository;

    public PacienteController(HistorialMedicoRepository historialMedicoRepository, SedeRepository sedeRepository, SeguroRepository seguroRepository, EspecialidadRepository especialidadRepository, AlergiaRepository alergiaRepository, BoletaRepository boletaRepository, UsuarioRepository usuarioRepository, RolesRepository rolesRepository,
                              TipoCitaRepository tipoCitaRepository, CitaRepository citaRepository, MedicamentoRepository medicamentoRepository, PreguntaRepository preguntaRepository, RptaRepository rptaRepository, HistorialMedicoHasAlergiaRepository historialMedicoHasAlergiaRepository, RecetaHasMedicamentoRepository recetaHasMedicamentoRepository,
                              CuestionarioRepository cuestionarioRepository, RecetaRepository recetaRepository, SedeHasEspecialidadeRepository sedeHasEspecialidadeRepository, ExamenMedicoRepository examenMedicoRepository, HorasDoctorRepository horasDoctorRepository, DeliverymedicamentoRepository deliverymedicamentoRepository){
        this.historialMedicoRepository = historialMedicoRepository;
        this.sedeRepository = sedeRepository;
        this.seguroRepository = seguroRepository;
        this.especialidadRepository = especialidadRepository;
        this.alergiaRepository = alergiaRepository;
        this.boletaRepository = boletaRepository;
        this.usuarioRepository = usuarioRepository;
        this.rolesRepository = rolesRepository;
        this.tipoCitaRepository = tipoCitaRepository;
        this.citaRepository = citaRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.preguntaRepository = preguntaRepository;
        this.rptaRepository = rptaRepository;
        this.historialMedicoHasAlergiaRepository=historialMedicoHasAlergiaRepository;
        this.recetaHasMedicamentoRepository = recetaHasMedicamentoRepository;
        this.cuestionarioRepository = cuestionarioRepository;
        this.recetaRepository = recetaRepository;
        this.sedeHasEspecialidadeRepository = sedeHasEspecialidadeRepository;
        this.examenMedicoRepository = examenMedicoRepository;
        this.horasDoctorRepository = horasDoctorRepository;
        this.deliverymedicamentoRepository = deliverymedicamentoRepository;
    }

    @RequestMapping(value = "/principal")
    public String paginaprincipal(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        SedeDto sedeUsuario = sedeRepository.getSede(usuario.getId());
        model.addAttribute("sedeUsuario", sedeUsuario);
        List<Sede1Dto> listaSedes1 = sedeRepository.sedeMapa();
        model.addAttribute("listaSedes1",listaSedes1);
        return "paciente/principal";
   }


   //para cambio sede
    @GetMapping(value = "/escogerSede")
    public String escogerSede(Model model, HttpSession session, Authentication authentication, HttpServletRequest httpServletRequest){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        session.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        model.addAttribute("sedes", sedeRepository.findAll());

        if (usuario.getSedesIdsedes().getId()==null){

            return "/paciente/sedeInicial";
        } else {

            return "redirect:/paciente/principal";
        }

        //return "/paciente/sedeInicial";
    }

    @PostMapping(value = "/escogerSede")
    public String sedeEscogida(Sede sede, @RequestParam("idSede") String sedeId, HttpSession session, Authentication authentication, HttpServletRequest httpServletRequest){

        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        session.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");


        sede.setId(Integer.parseInt(sedeId));
        sedeRepository.verificaridSede(sedeId);


        usuarioRepository.actualizarSede2(Integer.parseInt(sedeId), usuario.getId() );

        return "redirect:/paciente/principal";
    }

    @RequestMapping(value = "/tracking")
    public String tracking(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        SedeDto sedeUsuario = sedeRepository.getSede(usuario.getId());
        model.addAttribute("sedeUsuario", sedeUsuario);
        List<Sede1Dto> listaSedes1 = sedeRepository.sedeMapa();
        model.addAttribute("listaSedes1",listaSedes1);

        Sede sede = sedeRepository.findById(SPA.getSedesIdsedes().getId()).orElse(null);
        model.addAttribute("sede",sede);

        String apiKey = "AIzaSyC2fZLkVLhfgmyjt4sC_c4E61ibz_fa7yQ";
        String direccion = SPA.getDireccion();
        double latitud= 0;
        double longitud= 0;

        try {
            // Codificar la dirección para incluirla en la URL de la solicitud
            String direccionCodificada = URLEncoder.encode(direccion, "UTF-8");
            System.out.println(direccionCodificada);

            // Construir la URL de la solicitud a la API Geocoding de Google Maps
            String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + direccionCodificada + "&key="+apiKey;

            // Realizar la solicitud GET a la API
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            // Leer la respuesta de la API
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            // Procesar los datos de respuesta en formato JSON
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Obtener el arreglo de resultados
            JSONArray resultsArray = jsonResponse.getJSONArray("results");

            // Verificar si se encontraron resultados
            if (resultsArray.length() > 0) {
                // Obtener el primer resultado
                JSONObject firstResult = resultsArray.getJSONObject(0);

                // Obtener la ubicación del primer resultado
                JSONObject location = firstResult.getJSONObject("geometry").getJSONObject("location");

                // Obtener la latitud y la longitud
                latitud = location.getDouble("lat");
                longitud = location.getDouble("lng");

                // Mostrar los resultados
                System.out.println("Dirección: " + direccion);
                System.out.println("Latitud: " + latitud);
                System.out.println("Longitud: " + longitud);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        assert sede != null;
        double origen_latitud = sede.getLatitud();

        double origen_longitud = sede.getLongitud();
        System.out.println("Latitud es:"+origen_latitud+" y longitud es"+origen_longitud);

        String origen = origen_latitud+","+origen_longitud;
        String destino = latitud+","+longitud;
        String rutaOptima = "";
        String tiempoDemora = "";

        try {
            // Construye la URL de la solicitud
            String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origen +
                    "&destination=" + destino + "&key=" + apiKey;

            // Envía la solicitud y obtén la respuesta
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Analiza la respuesta
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode data = objectMapper.readTree(response.toString());

            if (data.get("status").asText().equals("OK")) {
                JsonNode routes = data.get("routes");
                JsonNode route = routes.get(0);
                rutaOptima = route.get("overview_polyline").get("points").asText();
                tiempoDemora = route.get("legs").get(0).get("duration").get("text").asText();

                System.out.println("Ruta óptima: " + rutaOptima);
                System.out.println("Tiempo de demora: " + tiempoDemora);
            } else {
                System.out.println("No se pudo encontrar una ruta óptima.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("longitud_actual");
        model.addAttribute("ruta",rutaOptima);
        model.addAttribute("tiempo",tiempoDemora);

        return "paciente/tracking";
    }

    @RequestMapping("/perfil")
    public String perfilpaciente(@ModelAttribute("alergia")Alergia alergia, Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        SeguroDto seguroUsuario = seguroRepository.getSeguro(usuario.getId());
        List<Integer> idAlergias = historialMedicoHasAlergiaRepository.listarAlergiasPorId(usuario.getHistorialmedicoIdhistorialmedico().getId());
        ArrayList<Alergia> alergias = new ArrayList<>();
        for(int i=0; i<idAlergias.size(); i++){
            alergias.add(alergiaRepository.obtenerAlergia(idAlergias.get(i)));
        }
        model.addAttribute("alergias", alergias);
        model.addAttribute("seguros", seguroRepository.findAll());
        model.addAttribute("seguroUsuario", seguroUsuario);
        return "paciente/perfil";
    }

    @GetMapping("/password")
    public String cambiarContra(Model model, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        return "paciente/cambioContrasena";
    }

    @RequestMapping("/consultas")
    public String citas(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Cita> citas = citaRepository.historialCitas2(usuario.getId());
        List<RecetasGa> listaMedicamentosxReceta = new ArrayList<>(); // 1 -> 1 Lista medicamentos
        for (Cita c: citas) {
            Optional<Receta> recetaOptional = recetaRepository.findById(c.getRecetaIdreceta().getId());
            if(recetaOptional.isPresent()){
                Receta receta = recetaOptional.get();
                List<RecetaHasMedicamento> recetaHasMedicamentos = recetaHasMedicamentoRepository.listarMedxId(receta.getId());
                Double precio = 0.0;
                Boolean recetaPagada = false;
                for(RecetaHasMedicamento r: recetaHasMedicamentos){
                    precio += r.getMedicamentosIdmedicamentos().getPrecio()*r.getCantidad();
                }
                Boleta boleta = boletaRepository.obtenerCitaxBoleta(c.getId());
                if(boleta.getRecetaIdreceta()!=null){
                    recetaPagada=true;
                }
                RecetasGa recetasGa = new RecetasGa();
                Double porcentaje = Double.parseDouble(boleta.getSeguro().getPorcSeguro());
                precio = (precio*porcentaje)/100;
                recetasGa.setPrecioTotal(precio);
                recetasGa.setReceta(receta);
                recetasGa.setRecetaPagada(recetaPagada);
                recetasGa.setMedicamentos(recetaHasMedicamentos);
                listaMedicamentosxReceta.add(recetasGa);
            }
        }
        model.addAttribute("listaRecetas", listaMedicamentosxReceta);
        model.addAttribute("citas", citas);
        return "paciente/consultas";
    }


    @RequestMapping("/notificaciones")
    public String notificaciones(Model model, HttpServletRequest httpServletRequest,HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
       return "paciente/notificaciones";
    }
    @RequestMapping("/mensajeria")
    public String mensajeria(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
       return "paciente/mensajeria";
    }

    @RequestMapping("/pagos")
    public String pagos(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        model.addAttribute("usuario", usuario);

        List<Cita> citaspas =  citaRepository.historialCitas2(usuario.getId());
        ArrayList<Boleta> boletas = new ArrayList<>();
        for (int i = 0; i < citaspas.size(); i++) {
            boletas.add(boletaRepository.obtenerCitaxBoleta(citaspas.get(i).getId()));
        }
        model.addAttribute("boletas", boletas);
        model.addAttribute("citas", citaspas);
        model.addAttribute("medicamentos", medicamentoRepository.findAll());
       return "paciente/pagos";
    }

    @RequestMapping("/listaDoctores")
    public String doctores(@RequestParam(value = "idEspecialidad", required = false)String idEspecialidad,Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        SedeDto sede = sedeRepository.getSede(usuario.getId());
        List<DoctorDto> doctores = null;
        if(idEspecialidad==null){
            doctores = usuarioRepository.obtenerlistaDoctores(sede.getId());
        }else{
            Integer especialidadId = sedeHasEspecialidadeRepository.verficarEspecialidadSede(sede.getId(), idEspecialidad);
            if(especialidadId!=null) {
                doctores = usuarioRepository.obtenerDoctoresEspecialidad(sede.getId(), especialidadId);
            }else{
                doctores = usuarioRepository.obtenerlistaDoctores(sede.getId());
            }
        }
        model.addAttribute("doctores", doctores);
        List<Integer> especialidadesxSedeId = sedeHasEspecialidadeRepository.listarEspecialidadesPorId(sede.getId());
        ArrayList<Especialidade> listaEspecialidades = new ArrayList<>();
        for(int i=0;i<especialidadesxSedeId.size();i++){
            listaEspecialidades.add(especialidadRepository.obtenerEspecialidadId(especialidadesxSedeId.get(i)));
        }
        model.addAttribute("sede", sede);
        model.addAttribute("especialidades", listaEspecialidades);
        return "paciente/listarDoctores";
    }

    @Autowired
    CuestionariosRepository cuestionariosRepository;
    @Autowired
    CuestionariosUsuariosRepository cuestionariosUsuariosRepository;

    @RequestMapping("/cuestionarios")
    public String cuestionarios(Model model,HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<CuestionariosUsuarios> cuestionariosUsuariosList = cuestionariosUsuariosRepository.cuestionarioXPaciente(usuario.getId());
        System.out.println(usuario.getId());
        for (CuestionariosUsuarios cuest : cuestionariosUsuariosList){
            System.out.println(cuest.getDnidoctor());
            System.out.println("paciente");
            System.out.println(cuest.getDnipaciente());
            System.out.println(cuest.getRespondido());
        }
        model.addAttribute("cuestionarios",cuestionariosUsuariosList);
       return "paciente/cuestionarios";
    }

    @GetMapping("/otraSede")
    public String otraSede(@RequestParam("sedeId")String sedeId, Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication, RedirectAttributes attr){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        List<Sede> sedes = sedeRepository.findAll();
        Sede sedeActual = null;
        String idSede = sedeRepository.verificaridSede(sedeId);
        try{
            Optional<Sede> sedeOptional = sedeRepository.findById(Integer.parseInt(idSede));
            if(sedeOptional.isPresent()){
                sedeActual = sedeOptional.get();
            }else{
                attr.addFlashAttribute("error","Error en el parámetro enviado");
                return "redirect:/paciente/agendarCita";
            }
        }catch (NumberFormatException e){
            System.out.println("Arch");
            attr.addFlashAttribute("error","Error en el parámetro enviado");
            return "redirect:/paciente/agendarCita";
        }
        model.addAttribute("sedeActual", sedeActual);
        model.addAttribute("sedes", sedes);
        return "paciente/otraSede";
    }
    @GetMapping("/agendarCita")
    public String agendarCita(@RequestParam(value = "idSede", required = false)String idSede, Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        SedeDto sede = new SedeDto() {
            @Override
            public Integer getId() {
                return null;
            }

            @Override
            public String getNombre() {
                return null;
            }
        };
        if(idSede!=null){
            String sedeId=sedeRepository.verificaridSede(idSede);
            if(sedeId!=null){
                sede = sedeRepository.getSedeId(sedeId);
            }else{
                //REgresar a pagina para elegir
                sede = sedeRepository.getSede(usuario.getId());
            }
        }else{
            sede = sedeRepository.getSede(usuario.getId());
        }
        List<Integer> especialidadesxSedeId = sedeHasEspecialidadeRepository.listarEspecialidadesPorId(sede.getId());
        ArrayList<Especialidade> listaEspecialidades = new ArrayList<>();
        for(int i=0;i<especialidadesxSedeId.size();i++){
            listaEspecialidades.add(especialidadRepository.obtenerEspecialidadId(especialidadesxSedeId.get(i)));
        }
        List<ExamenMedico> examenMedicos = examenMedicoRepository.findAll();
        model.addAttribute("sedeUsuario", sede);
        model.addAttribute("especialidades", listaEspecialidades);
        model.addAttribute("tipos", tipoCitaRepository.findAll());
        model.addAttribute("examenes", examenMedicos);
       return "paciente/agendar";
    }

    @PostMapping("/cambiarSeguro")
    public String cambiarSeguro(@RequestParam("seguro") String seguro, RedirectAttributes attr, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        String id = seguroRepository.verificaridSeguro(seguro);
        if(id!=null){
            usuarioRepository.cambiarSeguro(id, usuario.getId());
            attr.addFlashAttribute("msg1", "Se cambió el seguro exitosamente");
        }else{
            attr.addFlashAttribute("msg3", "Error al intentar cambiar el seguro");
        }
        return "redirect:/paciente/perfil";
    }

    @PostMapping("/guardarAlergias")
    public String guardarAlergias(@ModelAttribute("alergia") @Valid Alergia alergia, BindingResult bindingResult,
                                  RedirectAttributes attr, Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication) {
        Regex regex = new Regex();
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        if (bindingResult.hasErrors()) {
            List<Integer> idAlergias = historialMedicoHasAlergiaRepository.listarAlergiasPorId(usuario.getHistorialmedicoIdhistorialmedico().getId());
            ArrayList<Alergia> alergias = new ArrayList<>();
            for (int i = 0; i < idAlergias.size(); i++) {
                alergias.add(alergiaRepository.obtenerAlergia(idAlergias.get(i)));
            }
            model.addAttribute("alergias", alergias);
            model.addAttribute("seguros", seguroRepository.findAll());
            model.addAttribute("arch", "windowzzz");
            return "paciente/perfil";
        }else if(!regex.inputisValid(alergia.getNombre())){
            List<Integer> idAlergias = historialMedicoHasAlergiaRepository.listarAlergiasPorId(usuario.getHistorialmedicoIdhistorialmedico().getId());
            ArrayList<Alergia> alergias = new ArrayList<>();
            for(int i = 0; i < idAlergias.size(); i++) {
                alergias.add(alergiaRepository.obtenerAlergia(idAlergias.get(i)));
            }
            model.addAttribute("alergias", alergias);
            model.addAttribute("seguros", seguroRepository.findAll());
            model.addAttribute("arch", "windowzzz");
            attr.addFlashAttribute("msgRegex", "Ingresar solo texto en el input");
            return "redirect:/paciente/perfil";
        }else{
            alergia.setEnabled(true);
            alergiaRepository.save(alergia);
            Integer key = alergiaRepository.lastID();
            historialMedicoHasAlergiaRepository.aea(usuario.getHistorialmedicoIdhistorialmedico().getId(), key);
            attr.addFlashAttribute("msg2", "Se agregó la alergia exitosamente");
            return "redirect:/paciente/perfil";
        }
    }

    //Adaptarlo para sesiones
    @GetMapping("/responderCuestionario")
    public String responderCues(@RequestParam("idCuestionario") String idcuestionario,Model model,
                                HttpServletRequest httpServletRequest,
            RedirectAttributes attr, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        System.out.println(idcuestionario);
        int id  = Integer.parseInt(idcuestionario);
        Optional<CuestionariosUsuarios> optionalCuestionariosUsuarios= cuestionariosUsuariosRepository.findById(id);
        if (optionalCuestionariosUsuarios.isPresent()) {
            CuestionariosUsuarios cuestionariosUsuarios= optionalCuestionariosUsuarios.get();
            String entrada = cuestionariosUsuarios.getIdcuestionario().getPreguntas();
            List<String> listapreguntas = List.of(entrada.split("#!%&%!#"));
            cuestionariosUsuarios.getIdcuestionario().setListapreguntas(listapreguntas);
            model.addAttribute("cuestionario", cuestionariosUsuarios);
            return "paciente/responderCuestionario";
        } else {
            attr.addFlashAttribute("cuestionario_noexiste","El cuestionario a responder no existe");
            return "redirect:/paciente/cuestionarios";
        }
    }

    @GetMapping("/verRespuestas")
    public String verRespuestas(@RequestParam("idCuestionario") String idcuestionario,Model model,
                                HttpServletRequest httpServletRequest,
                                RedirectAttributes attr, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        System.out.println(idcuestionario);
        int id  = Integer.parseInt(idcuestionario);
        Optional<CuestionariosUsuarios> optionalCuestionariosUsuarios= cuestionariosUsuariosRepository.findById(id);
        if (optionalCuestionariosUsuarios.isPresent()) {
            CuestionariosUsuarios cuestionariosUsuarios= optionalCuestionariosUsuarios.get();
            String entrada = cuestionariosUsuarios.getIdcuestionario().getPreguntas();
            List<String> listapreguntas = List.of(entrada.split("#!%&%!#"));
            cuestionariosUsuarios.getIdcuestionario().setListapreguntas(listapreguntas);
            String entrada2 = cuestionariosUsuarios.getRespuestas();
            List<String> listarespuestas = List.of(entrada2.split("#!%&%!#"));
            cuestionariosUsuarios.setListarespuestas(listarespuestas);
            model.addAttribute("cuestionario", cuestionariosUsuarios);
            return "paciente/verRespuestas";
        } else {
            attr.addFlashAttribute("cuestionario_noexiste","El cuestionario a responder no existe");
            return "redirect:/paciente/cuestionarios";
        }
    }

        //BCryptPasswordEncoder().encode(plainTextPassword);

    @PostMapping("/guardarRespuestas")
    public String guardarRptas( @RequestParam("listarespuestas") String respuestas,
                                @RequestParam("cuestionarioid") String iddelcuestionario,
                                RedirectAttributes attr, Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){

        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        int idcues = Integer.parseInt(iddelcuestionario);
        String[] respuestasSeparadas = respuestas.split(">%%%%%<%%%%>%%%%%<");
        String salida ="";
        String separador ="#!%&%!#";
        int i = 0;
        for (String respuesta : respuestasSeparadas){
            System.out.println(respuesta);
            System.out.println(i);
            if (i==0){
                salida = salida+respuesta;
            }else {
                salida = salida + separador + respuesta;
            }
            i++;
        }
        System.out.println(salida);
        cuestionariosUsuariosRepository.responderCuestionario(salida,idcues);
        attr.addFlashAttribute("respondido","Cuestionario respondido con éxito");
        return "redirect:/paciente/cuestionarios";


    }


    @GetMapping("/borrarAlergia")
    public String borrarAlergia(@RequestParam("id")String id,
                                @RequestParam("id2")String id2,
                                RedirectAttributes attr){
        String idAlergia = alergiaRepository.verificaridAlergia(id);
        String idHistorialMedico = historialMedicoRepository.verificaridHistorialMedico(id2);
        if(idAlergia!=null && idHistorialMedico!=null){
            alergiaRepository.borrarAlergia(idAlergia);
            historialMedicoHasAlergiaRepository.borrarweas(idHistorialMedico, idAlergia);
            attr.addFlashAttribute("msg4", "Se borró la alergia exitosamente");
        }else{
            attr.addFlashAttribute("msg5", "Error al intentar borrar la alergia");
        }
        return "redirect:/paciente/perfil";
    }
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
        if(pass1.equals("") || pass2.equals("") || pass3.equals("")){
            attr.addFlashAttribute("errorPass", "Los campos no pueden estar vacios");
            return "redirect:/paciente/password";
        } else if (!passwordActualCoincide ) {
            attr.addFlashAttribute("errorPass", "Ocurrió un error durante el cambio de contraseña. No se aplicaron cambios.");
            return "redirect:/paciente/password";
        } else if (!pass3.equals(pass2) ) {
            attr.addFlashAttribute("errorPass", "Las nuevas contraseñas no coinciden");
            return "redirect:/paciente/password";
        }else if(!regex.contrasenaisValid(pass2)){
            attr.addFlashAttribute("errorPass", "La nueva contraseña no coincide con los requerimientos.");
            return "redirect:/paciente/password";
        }else {
            usuarioRepository.cambiarContra(new BCryptPasswordEncoder().encode(pass3), usuarioSession.getId());
            attr.addFlashAttribute("msgContrasenia","Su contraseña ha sido cambiada exitosamente");
            return "redirect:/paciente/perfil";
        }
    }

    @GetMapping("/cancelarCita")
    public String cancelarCita(@RequestParam("citaId") String citaId,
                               RedirectAttributes attr,HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        CorreoConEstilos correoConEstilos = new CorreoConEstilos();
        httpSession.setAttribute("usuario",SPA);
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        try{
            Optional<Cita> cita = citaRepository.findById(Integer.parseInt(citaId));
            if(cita.isPresent()){
                Cita citaA = cita.get();
                if(Objects.equals(citaA.getPaciente().getId(), usuarioSession.getId())){
                    citaRepository.cancelarCita(citaA.getId());
                    attr.addFlashAttribute("exitoCancelar", "Su cita se canceló de manera exitosa");
                    //Enviar correo cita cancelada
                    if(citaA.getEspecialidadesIdEspecialidad()!=null){
                        correoConEstilos.sendEmailEstilos( usuarioSession.getEmail()   , "Cita cancelada" , "Su consulta médica agendada para la fecha " + citaA.getFecha() + " en la especialidad " + citaA.getEspecialidadesIdEspecialidad().getNombreEspecialidad() + " fue cancelada.");
                    }else if(citaA.getExamenMedico()!=null){
                        correoConEstilos.sendEmailEstilos( usuarioSession.getEmail()   , "Cita cancelada" , "Su examen médico agendado para la fecha " + citaA.getFecha() + " en la especialidad " + citaA.getExamenMedico().getNombre() + " fue cancelado.");
                    }
                }else{
                    attr.addFlashAttribute("errorCancelar", "Error al intentar cancelar la cita");
                }
            }
        }catch (NumberFormatException e){
            System.out.printf(e.getMessage());
            attr.addFlashAttribute("errorCancelar", "Id erróneo de cita");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/paciente/consultas";
    }

    @PostMapping("/pagosTarjeta")
    public String pagosTarjeta(@RequestParam("citaId") String citaId,
                               @RequestParam("precio") String precio,
                               @RequestParam("cardNumber") String cardNumber,
                               @RequestParam("expDate")String expDate,
                               @RequestParam("cvv") String cvv,
                               RedirectAttributes attr,HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Regex regex = new Regex();
        CorreoConEstilos correoConEstilos = new CorreoConEstilos();
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        try{
            Optional<Cita> cita = citaRepository.findById(Integer.parseInt(citaId));
            if(cita.isPresent()){
                Cita citaA = cita.get();
                if(regex.cardNumberValid(cardNumber) && regex.expDateValid(expDate) && regex.cvvValid(cvv)){
                    if(Objects.equals(citaA.getPaciente().getId(), usuarioSession.getId())){
                        try{
                            String concpeto = "Consulta médica: " + citaA.getEspecialidadesIdEspecialidad().getNombreEspecialidad();
                            boletaRepository.crearBoletaCita(concpeto, Double.parseDouble(precio), citaA.getId(), usuarioSession.getSegurosIdSeguro().getId());
                            citaRepository.pagarCita(citaA.getId());
                            citaRepository.estadoPagada(citaA.getId());
                            attr.addFlashAttribute("exitoPagar", "Su cita se pagó de manera exitosa");
                            //Enviar correo pago con tarjeta correcto
                            correoConEstilos.sendEmailEstilos( usuarioSession.getEmail()   , "Pago de cita exitoso" , "Su consulta médica agendada para la fecha " + citaA.getFecha() + " en la especialidad " + citaA.getEspecialidadesIdEspecialidad().getNombreEspecialidad() + " fue pagada de manera exitosa.");
                        }catch (NumberFormatException e){
                            attr.addFlashAttribute("errorPagar", "Monto a pagar erróneo");
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        attr.addFlashAttribute("errorPagar", "Error al intentar pagar la cita");
                    }
                }else{
                    attr.addFlashAttribute("errorPagar", "Error en el ingreso de datos de la tarjeta");
                }
            }
        }catch (NumberFormatException e){
            System.out.printf(e.getMessage());
            attr.addFlashAttribute("errorPagar", "Id erróneo de cita");
        }
        return "redirect:/paciente/consultas";
    }
    @PostMapping("/pagosTarjeta2")
    public String pagosTarjeta2(@RequestParam("cita") String citaId,
                               @RequestParam("precioReceta") String precio,
                               @RequestParam("cardNumber") String cardNumber,
                               @RequestParam("expDate")String expDate,
                               @RequestParam("cvv") String cvv,
                               RedirectAttributes attr,HttpServletRequest httpServletRequest, HttpSession httpSession, Authentication authentication){
        Regex regex = new Regex();
        CorreoConEstilos correoConEstilos = new CorreoConEstilos();
        Usuario SPA = usuarioRepository.findByEmail(authentication.getName());
        httpSession.setAttribute("usuario",SPA);
        Usuario usuarioSession = (Usuario) httpServletRequest.getSession().getAttribute("usuario");
        try{
            Optional<Cita> cita = citaRepository.findById(Integer.parseInt(citaId));
            if(cita.isPresent()){
                Cita citaA = cita.get();
                if(regex.cardNumberValid(cardNumber) && regex.expDateValid(expDate) && regex.cvvValid(cvv)){
                    if(Objects.equals(citaA.getPaciente().getId(), usuarioSession.getId())){
                        try{
                            Boleta boleta = boletaRepository.obtenerCitaxBoleta(citaA.getId());
                            boletaRepository.boletaReceta(citaA.getRecetaIdreceta().getId(), Double.parseDouble(precio), boleta.getId());
                            attr.addFlashAttribute("exitoReceta", "Su receta se pagó de manera exitosa");
                            //Enviar correo pago con tarjeta correcto
                            correoConEstilos.sendEmailEstilos( usuarioSession.getEmail()   , "Pago de receta exitoso" , "La receta de consulta médica agendada para la fecha " + citaA.getFecha() + " en la especialidad " + citaA.getEspecialidadesIdEspecialidad().getNombreEspecialidad() + " fue pagada de manera exitosa.");
                        }catch (NumberFormatException e){
                            attr.addFlashAttribute("errorReceta", "Monto a pagar erróneo");
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        attr.addFlashAttribute("errorReceta", "Error al intentar pagar la receta");
                    }
                }else{
                    attr.addFlashAttribute("errorReceta", "Error en el ingreso de datos de la tarjeta");
                }
            }
        }catch (NumberFormatException e){
            System.out.printf(e.getMessage());
            attr.addFlashAttribute("errorReceta", "Id erróneo de cita");
        }
        return "redirect:/paciente/consultas";
    }
}
