import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConexaoApi {

    private HttpClient client;

    // Adicionar chave da API
    private static final String API_KEY = "";

    public ConexaoApi() {
        this.client = HttpClient.newHttpClient();
    }

    public String buscarClima(String cidade) {
        String cidadeFormatada = java.net.URLEncoder.encode(cidade, java.nio.charset.StandardCharsets.UTF_8);

        String url = String.format(
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/today?unitGroup=metric&key=%s&contentType=json",
                cidadeFormatada, API_KEY
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return null;
            }
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }
}