package com.example.medicaltec.dao;

import com.example.medicaltec.Entity.Persona;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PersonaDao {

    public Persona obtenerPersona(String dni){

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.ateneaperu.com/api/Reniec/Dni?sNroDocumento="+dni;
        ResponseEntity<Persona> response = restTemplate.getForEntity(url,Persona.class);

        return response.getBody();
    }

}
