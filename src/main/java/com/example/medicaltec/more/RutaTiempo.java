package com.example.medicaltec.more;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RutaTiempo {

        private String rutaOptima;
        private String tiempoDemora;

        public RutaTiempo(String rutaOptima, String tiempoDemora) {
            this.rutaOptima = rutaOptima;
            this.tiempoDemora = tiempoDemora;
        }
    }

