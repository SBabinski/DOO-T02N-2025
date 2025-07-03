package com.cinelume.util;

import com.cinelume.model.Serie;
import com.cinelume.model.Usuario;
import com.google.gson.JsonObject;
import java.util.Arrays;

public class DataLoader {
    public static void carregarDadosIniciais(Usuario usuario) {
        ApiClient apiClient = new ApiClient();
        Arrays.asList("Breaking Bad", "Game of Thrones", "Stranger Things")
            .forEach(nomeSerie -> {
                try {
                    JsonObject json = apiClient.buscarSerie(nomeSerie);
                    Serie serie = Serie.fromJson(json);
                    usuario.adicionarSerie("favorites", serie);
                } catch (Exception e) {
                    System.err.println("Erro ao carregar '" + nomeSerie + "': " + e.getMessage());
                }
            });
    }
}