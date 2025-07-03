package prova2tri;

import com.google.gson.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiClient {

    private static final String BASE_URL = "https://api.tvmaze.com/search/shows?q=";

    public static List<Serie> buscarSeriesPorNome(String nome) throws IOException {
        List<Serie> series = new ArrayList<>();
        String urlString = BASE_URL + nome.replace(" ", "%20");
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int status = connection.getResponseCode();
        if (status != 200) {
            throw new IOException("Erro na requisição HTTP: " + status);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(reader).getAsJsonArray();

            for (JsonElement element : jsonArray) {
                JsonObject showObj = element.getAsJsonObject().getAsJsonObject("show");

                Serie serie = new Serie();

                serie.setNome(getAsStringSafe(showObj, "name"));

                serie.setIdioma(getAsStringSafe(showObj, "language"));

                JsonArray genresArray = showObj.getAsJsonArray("genres");
                List<String> generos = new ArrayList<>();
                if (genresArray != null) {
                    for (JsonElement g : genresArray) {
                        generos.add(g.getAsString());
                    }
                }
                serie.setGeneros(generos);

                if (showObj.has("rating") && showObj.get("rating").isJsonObject()) {
                    JsonObject ratingObj = showObj.getAsJsonObject("rating");
                    if (ratingObj.has("average") && !ratingObj.get("average").isJsonNull()) {
                        serie.setNota(ratingObj.get("average").getAsDouble());
                    } else {
                        serie.setNota(0.0);
                    }
                } else {
                    serie.setNota(0.0);
                }

                serie.setStatus(getAsStringSafe(showObj, "status"));

                serie.setDataEstreia(getAsStringSafe(showObj, "premiered"));
                serie.setDataFim(getAsStringSafe(showObj, "ended"));

                String emissora = "Desconhecida";
                if (showObj.has("network") && showObj.get("network").isJsonObject()) {
                    emissora = getAsStringSafe(showObj.getAsJsonObject("network"), "name");
                } else if (showObj.has("webChannel") && showObj.get("webChannel").isJsonObject()) {
                    emissora = getAsStringSafe(showObj.getAsJsonObject("webChannel"), "name");
                }
                serie.setEmissora(emissora);

                series.add(serie);
            }
        } finally {
            connection.disconnect();
        }

        return series;
    }

    private static String getAsStringSafe(JsonObject obj, String memberName) {
        if (obj.has(memberName) && !obj.get(memberName).isJsonNull()) {
            return obj.get(memberName).getAsString();
        }
        return "Desconhecido";
    }
}
