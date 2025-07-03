package service;

import com.google.gson.Gson;
import model.Serie;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiService {

    public static List<Serie> searchSeriesByName(String name) {
        Serie[] seriesArray = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.tvmaze.com/search/shows?q=" + URLEncoder.encode(name, StandardCharsets.UTF_8))).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            seriesArray = gson.fromJson(response.body(), Serie[].class);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(Arrays.asList(seriesArray));
    }
}
