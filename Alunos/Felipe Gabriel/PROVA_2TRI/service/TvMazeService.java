package service;

import model.Serie;
import com.google.gson.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class TvMazeService {
    public List<Serie> buscarSeries(String nome) {
        List<Serie> series = new ArrayList<>();
        try {
            String urlStr = "https://api.tvmaze.com/search/shows?q=" + nome.replace(" ", "%20");
            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            conn.setRequestMethod("GET");

            JsonArray resultados = JsonParser.parseReader(new InputStreamReader(conn.getInputStream())).getAsJsonArray();

            for (JsonElement elem : resultados) {
                try {
                    JsonObject show = elem.getAsJsonObject().getAsJsonObject("show");
                    int id = show.get("id").getAsInt();
                    String titulo = show.get("name").getAsString();
                    String idioma = show.has("language") && !show.get("language").isJsonNull()
                            ? show.get("language").getAsString() : "Desconhecido";

                    List<String> generos = new ArrayList<>();
                    for (JsonElement g : show.getAsJsonArray("genres")) {
                        generos.add(g.getAsString());
                    }

                    double nota = (show.has("rating") && show.getAsJsonObject("rating").has("average")
                            && !show.getAsJsonObject("rating").get("average").isJsonNull())
                            ? show.getAsJsonObject("rating").get("average").getAsDouble()
                            : 0.0;

                    String status = show.has("status") && !show.get("status").isJsonNull()
                            ? show.get("status").getAsString()
                            : "Indefinido";

                    String estreia = show.has("premiered") && !show.get("premiered").isJsonNull()
                            ? show.get("premiered").getAsString() : "-";

                    String fim = show.has("ended") && !show.get("ended").isJsonNull()
                            ? show.get("ended").getAsString() : "-";

                    String emissora = (show.has("network") && !show.get("network").isJsonNull()
                            && show.getAsJsonObject("network").has("name"))
                            ? show.getAsJsonObject("network").get("name").getAsString()
                            : "-";

                    series.add(new Serie(id, titulo, idioma, generos, nota, status, estreia, fim, emissora));

                } catch (Exception ignored) {
              
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao conectar com a API do TVMaze.");
        }

        return series;
    }
}
