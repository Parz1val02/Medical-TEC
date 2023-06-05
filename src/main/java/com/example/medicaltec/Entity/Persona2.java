package com.example.medicaltec.Entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Persona2 {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String tipoDocumento;
    private String numeroDocumento;
    private String digitoVerificador;

}
