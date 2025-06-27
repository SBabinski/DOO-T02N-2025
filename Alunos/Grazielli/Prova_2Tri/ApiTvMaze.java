package prova2tri;

import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ApiTvMaze {

    public static Serie buscarSerie(String nome) throws IOException, InterruptedException {
        String url = "https://api.tvmaze.com/singlesearch/shows?q=" + URLEncoder.encode(nome, StandardCharsets.UTF_8);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

        Serie serie = new Serie();
        serie.setNome(json.get("name").getAsString());
        serie.setIdioma(json.get("language").getAsString());

        JsonElement nota = json.getAsJsonObject("rating").get("average");
        serie.setNota(nota == null || nota.isJsonNull() ? 0.0 : nota.getAsDouble());

        serie.setStatus(json.get("status").getAsString());
        serie.setDataEstreia(json.get("premiered").getAsString());
        serie.setDataFim(json.has("ended") && !json.get("ended").isJsonNull() ? json.get("ended").getAsString()
                : "Ainda em exibição");

        List<String> generoList = new ArrayList<>();
        JsonArray generos = json.getAsJsonArray("genres");
        for (JsonElement el : generos)
            generoList.add(el.getAsString());
        serie.setGeneros(generoList);

        JsonObject network = json.has("network") && !json.get("network").isJsonNull() ? json.getAsJsonObject("network")
                : null;
        serie.setEmissora(network != null ? network.get("name").getAsString() : "Desconhecida");

        return serie;
    }
}
