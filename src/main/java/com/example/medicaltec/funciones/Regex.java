package com.example.medicaltec.funciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public boolean contrasenaisValid(String pass2) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&*]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pass2);
        return matcher.find();
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
}
