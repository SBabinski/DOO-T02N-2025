package com.seuprojeto;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seuprojeto.dto.ShowDTO;
import com.seuprojeto.dto.ShowResultDTO;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TvMazeApi {

    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<Serie> buscarSeries(String nomeBusca) throws Exception {
        // Codifica o nome da busca para ser usado em uma URL
        String encodedQuery = URLEncoder.encode(nomeBusca, StandardCharsets.UTF_8);
        String url = "https://api.tvmaze.com/search/shows?q=" + encodedQuery;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // Envia a requisição e recebe a resposta como String
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Falha na API: HTTP " + response.statusCode());
        }

        String jsonBody = response.body();

        // Define o tipo da lista de resultados para o Gson
        Type listType = new TypeToken<ArrayList<ShowResultDTO>>() {}.getType();
        List<ShowResultDTO> results = gson.fromJson(jsonBody, listType);

        // Mapeia os resultados da API (DTOs) para a classe do seu modelo (Serie)
        return results.stream()
                .map(result -> {
                    ShowDTO show = result.show;
                    String emissora = (show.network != null) ? show.network.name : "N/A";
                    double nota = (show.rating != null && show.rating.average != null) ? show.rating.average : 0.0;
                    
                    return new Serie(
                            show.name,
                            show.language,
                            show.genres,
                            nota,
                            show.status,
                            show.premiered,
                            show.ended,
                            emissora
                    );
                })
                .collect(Collectors.toList());
    }
}