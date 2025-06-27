package org.series.service;
import org.series.Serie;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SerieService {
    public Serie buscarSeriePorNome(String nome) throws Exception {
        String urlString = "https://api.tvmaze.com/search/shows?q=" + nome.replace(" ", "%20");
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder resposta = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            resposta.append(inputLine);
        }
        in.close();

        JsonArray jsonArray = JsonParser.parseString(resposta.toString()).getAsJsonArray();
        if (jsonArray.size() == 0) return null;

        JsonObject show = jsonArray.get(0).getAsJsonObject().getAsJsonObject("show");

        String titulo = show.get("name").getAsString();
        String idioma = show.get("language").getAsString();
        List<String> generos = Arrays.asList(show.get("genres").toString().replace("[", "").replace("]", "").replace("", "").split(","));
        double nota = show.get("rating").getAsJsonObject().get("average").isJsonNull() ? 0.0 : show.get("rating").getAsJsonObject().get("average").getAsDouble();
        String status = show.get("status").getAsString();
        String estreia = show.get("premiered").isJsonNull() ? "N/A" : show.get("premiered").getAsString();
        String termino = show.get("ended").isJsonNull() ? "N/A" : show.get("ended").getAsString();
        String emissora = show.get("network") != null && !show.get("network").isJsonNull()
                ? show.get("network").getAsJsonObject().get("name").getAsString()
                : "N/A";

        return new Serie(titulo, idioma, generos, nota,
                status, estreia, termino, emissora);
    } }