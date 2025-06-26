package sistemaserie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ApiTVMaze {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Serie> buscarSeries(String nome) {
        List<Serie> series = new ArrayList<>();
        try {
            String nomeEncoded = URLEncoder.encode(nome, StandardCharsets.UTF_8);
            String url = "https://api.tvmaze.com/search/shows?q=" + nomeEncoded;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                List<SerieResult> resultados = mapper.readValue(response.body(), new TypeReference<>() {});
                for (SerieResult result : resultados) {
                    if (result.getShow() != null) {
                        series.add(result.getShow());
                    }
                }
            } else {
                System.out.println("Erro na API: Código " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar séries: " + e.getMessage());
        }

        return series;
    }
}
