package com.fag.doo_series.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fag.doo_series.model.Series;
import com.fag.doo_series.dto.SeriesParser;

public class SeriesApiClient {
    private static final String baseUrl = "https://api.tvmaze.com";
    private HttpClient httpClient;
    private SeriesParser seriesParser;
    
    public SeriesApiClient(){
        this.httpClient = HttpClient.newHttpClient();
        this.seriesParser = new SeriesParser();
    }

    public List<Series> searchSeriesByName(String name) throws Exception{
        String validName = validateName(name);
        
        try {
            String queryUrl = baseUrl + "/search/shows?q=" + validName;
            String jsonResponse = makeGetRequest(queryUrl);

            return seriesParser.parseSeriesList(jsonResponse);
        } catch (Exception e) {
            throw new RuntimeException("Falha na busca por series");
        }

        
    }

    private String makeGetRequest(String queryPath) throws Exception {
        URI queryUrl = new URI(queryPath);
        HttpRequest request = HttpRequest.newBuilder(queryUrl)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Failed to fetch data from API. Status code: " + response.statusCode());
        }

        return response.body();
    }

    private String validateName(String name){
        String formattedName = name.replaceAll("\\s", "+");

        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("A pesquisa não pode estar nula ou vazia");
        }

        return formattedName;
    }


}

