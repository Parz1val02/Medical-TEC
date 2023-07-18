package com.example.medicaltec.funciones;

public class Fechas {

    public String traducirDia(String diaDeLaSemana) {
        return switch (diaDeLaSemana) {
            case "MONDAY" -> "Lunes";
            case "TUESDAY" -> "Martes";
            case "WEDNESDAY" -> "Miercoles";
            case "THURSDAY" -> "Jueves";
            case "FRIDAY" -> "Viernes";
            case "SATURDAY" -> "Sabado";
            case "SUNDAY" -> "Domingo";
            default -> "";
        };
    }
    public String traducirMes(String mes) {
        return switch (mes) {
            case "JANUARY" -> "Enero";
            case "FEBRUARY" -> "Febrero";
            case "MARCH" -> "Marzo";
            case "APRIL" -> "Abril";
            case "MAY" -> "Mayo";
            case "JUNE" -> "Junio";
            case "JULY" -> "Julio";
            case "AUGUST" -> "Agosto";
            case "SEPTEMBER" -> "Setiembre";
            case "OCTOBER" -> "Octubre";
            case "NOVEMBER" -> "Noviembre";
            case "DECEMBER" -> "Diciembre";
            default -> "";
        };
    }
}
