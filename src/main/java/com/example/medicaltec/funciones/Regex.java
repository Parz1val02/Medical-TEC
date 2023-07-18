package com.example.medicaltec.funciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public boolean contrasenaisValid(String pass2) {
        // Verificar al menos un número
        if (!pass2.matches(".*\\d.*")) {
            return false;
        }

        // Verificar al menos un carácter especial
        if (!pass2.matches(".*[!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*")) {
            return false;
        }

        // Verificar al menos una mayúscula
        if (!pass2.matches(".*[A-Z].*")) {
            return false;
        }

        // Si la contraseña cumple con todos los requisitos, retornar true
        return true;
    }
    public boolean inputisValid(String input){
        String regex = "^[A-Za-zñÑáéíóúÁÉÍÓÚ]{1,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public boolean emailValid(String input) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public boolean dniValid(String input){
        String regex = "^(?=.*[0-9]).{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
    public boolean fechaValid(String input){
        String regex = "^(0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-(\\d{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public boolean cardNumberValid(String input){
        String regex = "^(?:\\d{4}[- ]){3}\\d{4}|\\d{16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public boolean expDateValid(String input){
        String regex = "^(0[1-9]|1[0-2])\\/?(0[1-9]|1[0-9]|2[0-9]|3[0-1])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public boolean cvvValid(String input){
        String regex = "^\\d{3,4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public boolean cedulaDoctorValid(String input){
        String regex = "^[0-9]{5,45}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

}
