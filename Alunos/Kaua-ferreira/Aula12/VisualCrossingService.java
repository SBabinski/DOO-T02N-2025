package com.meuaplicativoclima.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.meuaplicativoclima.model.WeatherData;

public class VisualCrossingService {

   
    private static final String API_KEY = "EJTP86MUR7KDD4XUVJXMKCU8U";
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private final Gson gson = new Gson();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public WeatherData getWeatherData(String location) {
        
        String encodedLocation;
        try {
            encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            System.err.println("Erro ao codificar a localização: " + e.getMessage());
            return null;
        }

      
        String url = BASE_URL + encodedLocation + "?unitGroup=metric&include=current,days&key=" + API_KEY;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET() 
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                
                return gson.fromJson(response.body(), WeatherData.class);
            } else {
                System.err.println("Erro na requisição à API Visual Crossing: Código de status " + response.statusCode());
                System.err.println("Resposta: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Ocorreu um erro ao buscar dados do clima: " + e.getMessage());
            return null;
        }
    }
}
