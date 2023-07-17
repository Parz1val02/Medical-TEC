package com.example.medicaltec.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingResponse {
    private String message;

    // Getters y setters

    // Constructor
    public TrackingResponse(String message) {
        this.message = message;
    }
}

