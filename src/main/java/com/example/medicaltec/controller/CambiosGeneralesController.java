package com.example.medicaltec.controller;

import com.example.medicaltec.Entity.Deliverymedicamento;
import com.example.medicaltec.Entity.Usuario;
import com.example.medicaltec.Entity.UxUi;
import com.example.medicaltec.repository.DeliverymedicamentoRepository;
import com.example.medicaltec.repository.UsuarioRepository;
import com.example.medicaltec.repository.UxUiRepository;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class CambiosGeneralesController {
    @Autowired
    final UsuarioRepository usuarioRepository;
    final UxUiRepository uxUiRepository;
    final DeliverymedicamentoRepository deliverymedicamentoRepository;

    public CambiosGeneralesController(UsuarioRepository usuarioRepository, UxUiRepository uxUiRepository, DeliverymedicamentoRepository deliverymedicamentoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.uxUiRepository = uxUiRepository;
        this.deliverymedicamentoRepository = deliverymedicamentoRepository;
    }

    @GetMapping("/logo")
    public ResponseEntity<byte[]> mostrarLogo(){
        int id=5;
        Optional<UxUi> opt = uxUiRepository.findById(id);
        if(opt.isPresent()){
            UxUi uxUi= opt.get();
            byte[] imagenComoBytes = uxUi.getLogo();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.parseMediaType(uxUi.getLogoContentType()));
            return new ResponseEntity<>(imagenComoBytes, httpHeaders, HttpStatus.OK);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/editarUxUi")
    public String guardarColor(@RequestParam("colorPicker") String colorPicker, @RequestParam("colorPicker1") String colorPicker1) {
        UxUi uxUi = uxUiRepository.findById(5).orElse(null);
        assert uxUi != null;
        uxUi.setColorBar(colorPicker);
        uxUi.setColorBack(colorPicker1);
        uxUiRepository.editarUxUi(colorPicker, colorPicker1);

        // Crear un objeto ModelAndView y establecer la vista y los datos del modelo
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/superAdmin/dashboard");

        // Actualizar los colores en el almacenamiento local
        actualizarColoresEnAlmacenamientoLocal(colorPicker, colorPicker1, modelAndView);

        return modelAndView.getViewName();
    }
    private void actualizarColoresEnAlmacenamientoLocal(String color1, String color2, ModelAndView modelAndView) {
        // Crear un objeto JSON con los colores
        JsonObject colorsJson = new JsonObject();
        colorsJson.addProperty("color1", color1);
        colorsJson.addProperty("color2", color2);

        // Convertir el objeto JSON a una cadena de texto
        String colorsJsonString = colorsJson.toString();

        // Pasar los colores al cliente a través de un atributo de modelo en la redirección
        // para que puedan ser utilizados en el lado del cliente
        // Aquí asumimos que tienes un objeto ModelAndView llamado "modelAndView"
        // que se utiliza para la redirección
        modelAndView.addObject("colorsJson", colorsJsonString);
    }
    @GetMapping("/color")
    public ResponseEntity<Map<String, String>> getColor() {
        UxUi uxUi = uxUiRepository.findById(5).orElse(null); // Obtener el último color guardado desde la base de datos

        Map<String, String> colorMap = new HashMap<>();
        assert uxUi != null;
        colorMap.put("color1", uxUi.getColorBar());
        colorMap.put("color2", uxUi.getColorBack());

        return ResponseEntity.ok(colorMap);
    }

}