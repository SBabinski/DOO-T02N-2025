package com.faculdade.tv_series;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class TVMazeAPI {
    private static final String BASE_URL = "https://api.tvmaze.com/";
    private final HttpClient httpClient;
    private final Gson gson;

    public TVMazeAPI() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public List<Serie> buscarSeries(String nomeSerie) {
        List<Serie> seriesEncontradas = new ArrayList<>();
        String url = BASE_URL + "search/shows?q=" + nomeSerie.replace(" ", "%20");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
                for (JsonElement element : jsonArray) {
                    JsonObject showObject = element.getAsJsonObject().getAsJsonObject("show");
                    if (showObject != null) {
                        Serie serie = parseSerieFromJson(showObject);
                        seriesEncontradas.add(serie);
                    }
                }
            } else {
                System.out.println("Erro ao buscar série. Código de status: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro de conexão ou interrupção: " + e.getMessage());
            
            e.printStackTrace();
        }
        return seriesEncontradas;
    }

    
    public Serie buscarSeriePorId(int idSerie) {
        String url = BASE_URL + "shows/" + idSerie;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject showObject = JsonParser.parseString(response.body()).getAsJsonObject();
                return parseSerieFromJson(showObject);
            } else {
                System.out.println("Erro ao buscar série por ID. Código de status: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro de conexão ou interrupção ao buscar por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    private Serie parseSerieFromJson(JsonObject showObject) {
        Serie serie = new Serie();

       
        if (showObject.has("id") && !showObject.get("id").isJsonNull()) {
            serie.setId(showObject.get("id").getAsInt());
        }

        
        if (showObject.has("name") && !showObject.get("name").isJsonNull()) {
            serie.setNome(showObject.get("name").getAsString());
        }

        
        if (showObject.has("language") && !showObject.get("language").isJsonNull()) {
            serie.setIdioma(showObject.get("language").getAsString());
        }

        
        if (showObject.has("genres") && showObject.get("genres").isJsonArray()) {
            JsonArray genresArray = showObject.getAsJsonArray("genres");
            List<String> generos = new ArrayList<>();
            for (JsonElement genreElement : genresArray) {
                if (genreElement != null && !genreElement.isJsonNull()) {
                    generos.add(genreElement.getAsString());
                }
            }
            serie.setGeneros(generos);
        }

        
        if (showObject.has("rating") && showObject.get("rating").isJsonObject()) {
            JsonObject ratingObject = showObject.getAsJsonObject("rating");
            if (ratingObject.has("average") && !ratingObject.get("average").isJsonNull()) {
                serie.setNota(ratingObject.get("average").getAsDouble());
            } else {
                serie.setNota(0.0); 
            }
        } else {
            serie.setNota(0.0); 
        }


        
        if (showObject.has("status") && !showObject.get("status").isJsonNull()) {
            serie.setStatus(showObject.get("status").getAsString());
        } else {
            serie.setStatus("A definir"); 
        }


       
        if (showObject.has("premiered") && !showObject.get("premiered").isJsonNull()) {
            serie.setDataEstreia(showObject.get("premiered").getAsString());
        }

       
        if (showObject.has("ended") && !showObject.get("ended").isJsonNull()) {
            serie.setDataTermino(showObject.get("ended").getAsString());
        }

        
        if (showObject.has("network") && showObject.get("network").isJsonObject()) {
            JsonObject networkObject = showObject.getAsJsonObject("network");
            if (networkObject.has("name") && !networkObject.get("name").isJsonNull()) {
                serie.setEmissora(networkObject.get("name").getAsString());
            }
        }

        return serie;
    }
}