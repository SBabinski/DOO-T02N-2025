package Aula12;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public class VisualCrossingRequest {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public static Clima climaApi(String token) {
        LocalDate hoje = LocalDate.now();
        String uri = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + token.replace(' ', '+') +
                "/"+hoje+"?key=HPEXPD7VD6CH3WJGX2NQY54UK";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return gson.fromJson(response.body(), Clima.class);

        } catch (Exception e) {
            System.err.println("Erro ao fazer a requisição: " + e.getMessage());
        }
        return null;
    }
}
