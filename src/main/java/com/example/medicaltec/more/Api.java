package com.example.medicaltec.more;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class Api {

    //api
    //http://api.ateneaperu.com/api/Sunat/JBqV3djTyQZtB0ej8FeFUjR1kNXUKoX5Jo4+kTE1iwg=




    void sendRequest(String request) throws IOException {
        // i.e.: request = "http://example.com/index.php?param1=a&param2=b&param3=c";
        URL url = new URL(request);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "text/plain");
        connection.setRequestProperty("charset", "utf-8");
        connection.connect();
    }



}
