package Prova_2tri;

import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class TvMazeClient {
    
        private Serie parseSerie(JsonObject obj) {
        Serie serie = new Serie();
        serie.setNome(obj.get("name").getAsString());
        serie.setIdioma(obj.get("language").getAsString());
        serie.setNotaGeral(obj.get("rating").getAsJsonObject().get("average").getAsDouble());
        serie.setStatus(obj.get("status").getAsString());
        serie.setDataEstreia(obj.get("premiered").getAsString());
        serie.setDataFim(obj.has("ended") && !obj.get("ended").isJsonNull() ? obj.get("ended").getAsString() : "Em exibição");

        List<String> generos = new ArrayList<>();
        for (JsonElement e : obj.getAsJsonArray("genres"))
            generos.add(e.getAsString());
        serie.setGeneros(generos);

        JsonObject network = obj.has("network") && !obj.get("network").isJsonNull() ?
                obj.getAsJsonObject("network") : obj.getAsJsonObject("webChannel");

        if (network != null && network.has("name"))
            serie.setEmissora(network.get("name").getAsString());
        else
            serie.setEmissora("Desconhecida");

        return serie;
    }

    public Serie buscarSeriePorNome(String nome) throws IOException {
        String url = "https://api.tvmaze.com/singlesearch/shows?q=" + URLEncoder.encode(nome, "UTF-8");
        HttpURLConnection conn = (HttpURLConnection) URI.create(url).toURL().openConnection();
        conn.setRequestMethod("GET");
   
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            JsonObject obj = JsonParser.parseReader(in).getAsJsonObject();
            return parseSerie(obj);
        }
    }

}