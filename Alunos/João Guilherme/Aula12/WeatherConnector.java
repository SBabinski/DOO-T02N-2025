import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherConnector {

    private static final String API_KEY = "8SP5WSAHFJQ5DEFUBGESV8S3J";
    private static final HttpClient client = HttpClient.newHttpClient();

    public static WeatherData buscarClima(String cidade) throws Exception {
        String cidadeEncoded = URLEncoder.encode(cidade, StandardCharsets.UTF_8);
        String url = String.format(
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/today" +
                        "?unitGroup=metric&include=current,days&elements=temp,tempmax,tempmin,humidity,conditions,precip,winddir,windspeed&key=%s",
                cidadeEncoded, API_KEY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro na API: " + response.statusCode());
        }

        String json = response.body();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode atual = root.get("currentConditions");
        JsonNode dia = root.get("days").get(0);

        return new WeatherData(
                atual.get("temp").asDouble(),
                dia.get("tempmax").asDouble(),
                dia.get("tempmin").asDouble(),
                atual.get("humidity").asInt(),
                atual.get("conditions").asText(),
                atual.get("precip").asDouble(),
                atual.get("windspeed").asDouble(),
                atual.get("winddir").asDouble()
        );
    }
}
