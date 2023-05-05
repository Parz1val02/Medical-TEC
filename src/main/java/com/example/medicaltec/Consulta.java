package com.example.medicaltec;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Consulta {

    public String getNombres (String url){
        String datos = "";
        try {
            Document doc =  Jsoup.connect(url).get();
            Element el = doc.body();
            datos = el.text();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return datos;
    }


}
