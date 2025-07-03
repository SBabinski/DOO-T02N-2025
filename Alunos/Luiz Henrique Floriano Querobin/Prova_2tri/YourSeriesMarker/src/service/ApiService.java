package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Serie;
import model.json.api.SerieApiJson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiService {

    public static List<Serie> buscaListaSerie(String nomeSerieBusca) {
        HttpClient client = HttpClient.newHttpClient();

        var htmlRequisicao = "https://api.tvmaze.com/search/shows?q=" + nomeSerieBusca;
        htmlRequisicao = htmlRequisicao.replace(" ", "+");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(htmlRequisicao))
                .GET()
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();
        List<SerieApiJson> listaSerieApiJson = gson.fromJson(response.body(), new TypeToken<List<SerieApiJson>>(){}.getType());

        return Serie.getListSerieFromListSerieJSON(listaSerieApiJson);
    }
}
