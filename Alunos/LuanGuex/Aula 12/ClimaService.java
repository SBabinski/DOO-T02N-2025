import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClimaService {
    private final String apiKey;

    public ClimaService(String apiKey) {
        this.apiKey = apiKey;
    }

    public String buscarClima(String cidade) throws Exception {
        String url = String.format(
            "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/today?unitGroup=metric&key=%s&contentType=json",
            cidade.replace(" ", "%20"), apiKey
        );

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Resposta inválida da API: " + response.statusCode());
        }

        return response.body();
    }
}
