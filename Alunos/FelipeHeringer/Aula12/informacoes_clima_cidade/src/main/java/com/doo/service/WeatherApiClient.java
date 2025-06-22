package com.doo.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherApiClient {

    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private static String API_KEY = "";
    private HttpClient httpClient;

    public WeatherApiClient(String apiKey) {
        this.httpClient = HttpClient.newHttpClient();

        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("A chave da API não pode ser nula ou vazia");
        }
        
        API_KEY = apiKey;
    }

    public String getWeatherData(String city) throws Exception {
        try {
            String url = BASE_URL + city + "/?key=" + API_KEY;
            String response = makeGetRequest(url);

            if (response == null || response.isEmpty()) {
                throw new RuntimeException("Nenhum dado encontrado para a cidade: " + city);
            }
            return response;

        } catch (Exception e) {
            throw new RuntimeException("Falha ao buscar dados do clima para a cidade: " + city);
        }
    }

    private String makeGetRequest(String url) throws Exception {

        URI queryUrl = new URI(url);
        HttpRequest request = HttpRequest.newBuilder(queryUrl)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Failed to fetch data from API. Status code: " + response.statusCode());
        }

        return response.body();
    }
}
