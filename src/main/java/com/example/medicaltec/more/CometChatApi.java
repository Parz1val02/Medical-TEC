package com.example.medicaltec.more;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CometChatApi {

    public void crearUsuarioCometChat(String dni, String nombreUsuario) throws IOException, InterruptedException {
        String appID = "24272539e9e89a98";
        String apiKey = "9ab4636d565de4ad3ba6e57e3d361608b9d33986";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://" + appID + ".api-us.cometchat.io/v3/users"))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("apikey", apiKey)
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"uid\":\"" +dni + "\",\"name\":\" " +nombreUsuario+ "\",\"withAuthToken\":true}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        //en respuesta obtiene el authToken del usuario recien creado. puede servir para autenticacion del usuario en Cometchat.login() por medio de authtoken.
        System.out.println(response.body());
    }


}
