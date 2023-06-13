package com.example.medicaltec.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
@Setter
public class Persona {

    private boolean success;


    private String ruc;
    private String nombre_o_razon_social;
    private String estado_del_contribuyente;
    private String condicion_de_domicilio ;
    private String ubigeo ;
    private String tipo_de_via ;
    private String nombre_de_via ;
    private String codigo_de_zona ;
    private String tipo_de_zona ;
    private String numero ;
    private String interior ;
    private String lote ;
    private String dpto ;
    private String manzana ;
    private String kilometro ;
    private String departamento ;
    private String provincia ;
    private String distrito ;
    private String direccion ;
    private String direccion_completa ;
    private Date ultima_actualizacion ;
    private String informacion_resultado ;
    private String apellido_paterno ;
    private String apellido_materno ;
    private String nombres ;
    private String nombres_completos ;





}
