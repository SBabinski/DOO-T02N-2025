import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ApiConnector {

    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static String buscarUnicaSerie(String nome) throws IOException, InterruptedException {
        String url = "https://api.tvmaze.com/singlesearch/shows?q=" + nome;
        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> resposta = client.send(requisicao, HttpResponse.BodyHandlers.ofString());

        if (resposta.statusCode() == 200) {
            return resposta.body();
        } else {
            throw new IOException("Erro HTTP: " + resposta.statusCode());
        }
    }

    public static String buscarMultiplasSeries(String nome) throws IOException, InterruptedException {
        String url = "https://api.tvmaze.com/search/shows?q=" + nome;
        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> resposta = client.send(requisicao, HttpResponse.BodyHandlers.ofString());

        if (resposta.statusCode() == 200) {
            return resposta.body();
        } else {
            throw new IOException("Erro HTTP: " + resposta.statusCode());
        }
    }
}
