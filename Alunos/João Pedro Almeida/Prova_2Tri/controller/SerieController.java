package com.cinelume.controller;

import com.cinelume.model.Serie;
import com.cinelume.util.ApiClient;
import com.google.gson.JsonObject;
import java.io.IOException;

public class SerieController {
    private final ApiClient apiClient = new ApiClient();

    public Serie buscarSerie(String nome) throws IOException {
        JsonObject json = apiClient.buscarSerie(nome); // Agora retorna JsonObject (Gson)
        return Serie.fromJson(json);
    }
}