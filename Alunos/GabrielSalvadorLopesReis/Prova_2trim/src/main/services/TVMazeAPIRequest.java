package services;

import com.google.gson.Gson;
import models.Serie;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TVMazeAPIRequest {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static List<Serie> serieRequest(String token) {
        String uri = "https://api.tvmaze.com/search/shows?q=" + token.replace(" ","+");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return List.of(gson.fromJson(response.body(), Serie[].class));

        } catch (Exception e) {
            System.err.println("Erro ao fazer a requisição: " + e.getMessage());
        }
        return null;
    }

}
