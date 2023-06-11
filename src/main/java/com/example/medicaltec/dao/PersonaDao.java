package com.example.medicaltec.dao;

import com.example.medicaltec.Entity.Persona;
import com.example.medicaltec.Entity.Persona2;
import com.example.medicaltec.Entity.Persona3;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PersonaDao {

    public Persona3 obtenerPersona(String dni)  {

        RestTemplate restTemplate = new RestTemplate();
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImVkdXBpc2NvdGUuY2VwdEBnbWFpbC5jb20ifQ.EBZ7aEbbb51PcGiWtZwV1RopnJsYV3m8XqyRv4xotok";
        String url = "https://dniruc.apisperu.com/api/v1/dni/"+dni+"?token="+token;
        ResponseEntity<Persona3> response = restTemplate.getForEntity(url,Persona3.class);



        return response.getBody();
    }





}
