import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TempoEstrutura {
    private static final String API_KEY = "J4ULLVZDKRHXVUS3N8BYUC2CS"; 
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    public ClasseTempo getWeather(String city) throws Exception {
        String today = LocalDate.now().toString();
        String url = String.format(
            "%s%s/%s?unitGroup=metric&key=%s&include=current",
            BASE_URL, city, today, API_KEY
        );

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro na requisição: código " + response.statusCode());
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());

        JsonNode current = root.get("currentConditions");
        JsonNode day = root.get("days").get(0);

        ClasseTempo info = new ClasseTempo();
        info.temperature = current.get("temp").asDouble();
        info.tempMax = day.get("tempmax").asDouble();
        info.tempMin = day.get("tempmin").asDouble();
        info.humidity = current.get("humidity").asInt();
        info.condition = current.get("conditions").asText();
        info.precipitation = current.has("precip") ? current.get("precip").asDouble() : 0.0;
        info.windSpeed = current.get("windspeed").asDouble();
        info.windDirection = current.get("winddir").asText();

        return info;
    }
}
