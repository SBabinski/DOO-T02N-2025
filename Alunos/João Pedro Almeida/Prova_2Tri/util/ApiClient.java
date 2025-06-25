package com.cinelume.util;

import com.google.gson.*;
import okhttp3.*;
import java.io.IOException;
import java.util.*;

public class ApiClient {
    private static final String BASE_URL = "https://api.tvmaze.com";
    private final OkHttpClient client = new OkHttpClient();

    // Método NOVO - Busca múltiplas séries
    public List<JsonObject> buscarSeries(String query) throws IOException {
        String url = BASE_URL + "/search/shows?q=" + query.replace(" ", "%20");
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Erro na API: " + response.code());
            return parseJsonToSeries(response.body().string());
        }
    }

    // Método original mantido para compatibilidade
    public JsonObject buscarSerie(String nomeSerie) throws IOException {
        String url = BASE_URL + "/singlesearch/shows?q=" + nomeSerie.replace(" ", "%20");
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Erro na API: " + response.code());
            return JsonParser.parseString(response.body().string()).getAsJsonObject();
        }
    }

    private List<JsonObject> parseJsonToSeries(String jsonData) {
        JsonArray jsonArray = JsonParser.parseString(jsonData).getAsJsonArray();
        List<JsonObject> series = new ArrayList<>(jsonArray.size());
        
        for (JsonElement element : jsonArray) {
            series.add(element.getAsJsonObject().getAsJsonObject("show"));
        }
        return series;
    }
}