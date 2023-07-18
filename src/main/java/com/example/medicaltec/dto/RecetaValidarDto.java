package com.example.medicaltec.dto;

public interface RecetaValidarDto {

    Integer getIdcita();
    Integer getIdreceta();
    String getComentario();
    String getObservaciones();
    Integer getCantidad();
    Integer getPrecio();
    String getNombre();
    Integer getSeguros_id_seguro();
    String getNombre_seguro();
    Double getPrecio_Calculado();
    String getFullname();
    String getEmail();

}
