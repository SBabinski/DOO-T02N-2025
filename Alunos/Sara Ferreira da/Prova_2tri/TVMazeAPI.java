package com.faculdade.tvseries;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TVMazeAPI { 

    
    public static List<Serie> buscarSeries(String nomeSerie) {
        List<Serie> resultados = new ArrayList<>();
        try {
            
            String urlString = "https://api.tvmaze.com/search/shows?q=" +
                    URLEncoder.encode(nomeSerie, "UTF-8"); 
            URL url = new URL(urlString);
            
            
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");

            
            if (conexao.getResponseCode() == 200) {
                
                BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                
                
                JsonArray jsonArray = JsonParser.parseReader(leitor).getAsJsonArray();

                Gson gson = new Gson();
               
                for (JsonElement element : jsonArray) {
                    JsonObject obj = element.getAsJsonObject();
                    
                    JsonObject show = obj.getAsJsonObject("show");
                    
                    Serie serie = gson.fromJson(show, Serie.class);
                    resultados.add(serie);
                }
            } else {
                System.out.println("Erro na conexão com a API: " + conexao.getResponseCode());
            }
        } catch (Exception e) {
            
            System.err.println("Erro ao buscar série: " + e.getMessage());
            
        }
        return resultados;
    }
}