package com.example.medicaltec.dao;

import com.example.medicaltec.Entity.Persona;
import com.example.medicaltec.Entity.Persona2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PersonaDao {

    public Persona2 obtenerPersona(String dni){

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.apis.net.pe/v2/reniec/dni?numero="+dni;
        ResponseEntity<Persona2> response = restTemplate.getForEntity(url,Persona2.class);

        return response.getBody();
    }

}
