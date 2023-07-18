package com.example.medicaltec.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingRequest {
    private String latitud1;
    private String longitud1;
    private String rutaOptima;
    private String tiempoDemora;

    // Getters y setters

    // Constructor
    public TrackingRequest(String latitud1, String longitud1, String rutaOptima, String tiempoDemora) {
        this.latitud1 = latitud1;
        this.longitud1 = longitud1;
        this.rutaOptima = rutaOptima;
        this.tiempoDemora = tiempoDemora;
    }
}