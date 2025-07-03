package org.example.servico;

import com.google.gson.Gson;
import org.example.dto.SerieDto;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ServicoApi {

    private static final String BASE_URL = "https://api.tvmaze.com/singlesearch/shows?q=";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public SerieDto buscarSerie(String nomeSerie) {
        try {
            String query = URLEncoder.encode(nomeSerie, StandardCharsets.UTF_8.name());
            URI uri = new URI(BASE_URL + query);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), SerieDto.class);
            } else {
                System.out.println("Erro: código de resposta " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar série: " + e.getMessage());
        }

        return null;
    }
}
