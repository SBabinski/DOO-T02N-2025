package com.monitoradeseries.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest; 
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List; 

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.monitoradeseries.model.Serie;


public class TVMazeService {

    private static final String BASE_URL = "https://api.tvmaze.com";
    private final Gson gson;

    public TVMazeService() {
        this.gson = new Gson();
    }

    public List<Serie> buscarSeriesPorNome(String nome) {
    // Codifica o nome da série para ser usado na URL
    String nomeCodificado;
    try {
        nomeCodificado = URLEncoder.encode(nome, StandardCharsets.UTF_8.toString());
    } catch (Exception e) { 
        System.err.println("Erro ao codificar o nome da série: " + e.getMessage());
        return Collections.emptyList(); 
    }

    String url = "https://api.tvmaze.com/search/shows?q=" + nomeCodificado; 

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();

    try {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String jsonResponse = response.body();
            Gson gson = new Gson();
          

           
            List<java.util.Map<String, Object>> results = gson.fromJson(jsonResponse,
                    new TypeToken<List<java.util.Map<String, Object>>>() {}.getType());

            
            List<Serie> series = new java.util.ArrayList<>();
            for (java.util.Map<String, Object> result : results) {
                if (result.containsKey("show")) {
                    
                    Serie serie = gson.fromJson(gson.toJson(result.get("show")), Serie.class);
                    series.add(serie);
                }
            }
            return series;

        } else {
            System.err.println("Erro na requisição à API: Código de status " + response.statusCode());
            return Collections.emptyList();
        }

    } catch (IOException | InterruptedException e) {
        System.err.println("Ocorreu um erro ao buscar séries: " + e.getMessage());
        return Collections.emptyList();
    }
}

    
    private static class TVMazeSearchResult {
        private Serie show;

        public Serie getShow() {
            return show;
        }
    }
}
