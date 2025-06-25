package sistemaSerie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class ApiTVMaze {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("unchecked")//so pra tirar os avisos
	public static List<Serie> buscarSeries(String nome) {
        List<Serie> series = new ArrayList<>();
        try {
            String url = "https://api.tvmaze.com/search/shows?q=" + nome.replace(" ", "%20");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            List<Map<String, Object>> lista = mapper.readValue(response.body(), new TypeReference<>() {});
            for (Map<String, Object> item : lista) {
                Map<String, Object> show = (Map<String, Object>) item.get("show");
                if (show == null) continue;

                Serie serie = new Serie(
                        (String) show.get("name"),
                        (String) show.get("language"),
                        (List<String>) show.get("genres"),
                        show.get("rating") != null && ((Map<?, ?>) show.get("rating")).get("average") != null ? ((Number) ((Map<?, ?>) show.get("rating")).get("average")).doubleValue() : 0.0,
                        (String) show.get("status"),
                        (String) show.get("premiered"),
                        show.get("ended") != null ? (String) show.get("ended") : "-",
                        show.get("network") != null ? (String) ((Map<?, ?>) show.get("network")).get("name") : "Desconhecida"
                );

                series.add(serie);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar séries: " + e.getMessage());
        }

        return series;
    }
}