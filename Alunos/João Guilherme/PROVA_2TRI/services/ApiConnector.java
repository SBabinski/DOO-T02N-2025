import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiConnector {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Logger logger = Logger.getLogger(ApiConnector.class.getName());

    public static String buscarUnicaSerie(String nome) {
        return buscarSerie("https://api.tvmaze.com/singlesearch/shows?q=" + nome).join();
    }

    private static CompletableFuture<String> buscarSerie(String url) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao criar URI: " + e.getMessage(), e);
            return CompletableFuture.completedFuture(null);
        }

        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        return client.sendAsync(requisicao, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200 && response.body() != null && !response.body().isEmpty()) {
                        return response.body();
                    } else {
                        logger.log(Level.WARNING, "Resposta inesperada: " + response.statusCode());
                        return null;
                    }
                })
                .exceptionally(ex -> {
                    logger.log(Level.SEVERE, "Erro durante a requisição: " + ex.getMessage(), ex);
                    return null;
                });
    }
}
