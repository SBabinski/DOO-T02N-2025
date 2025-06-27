import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpService {

    private final HttpClient client = HttpClient.newHttpClient();

    public String get(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.err.println("Falha na requisição: Código " + response.statusCode() + ". Verifique a cidade ou sua chave da API.");
            System.err.println("Mensagem do servidor: " + response.body());
            return null;
        }

        return response.body();
    }
}