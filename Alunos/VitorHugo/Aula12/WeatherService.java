package Aula12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherService {
    private final String apiKey;

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
    }

    public WeatherInfo getWeatherInfo(String city) throws Exception {
        String urlString = String.format(
            "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/today?unitGroup=metric&key=%s&lang=pt",
            city.replace(" ", "%20"),
            apiKey
        );

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("Erro de resposta da API: " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return parseWeatherResponse(response.toString());
    }

    private WeatherInfo parseWeatherResponse(String json) throws Exception {
        JSONObject obj = new JSONObject(json);
        // O campo 'days' é um array com o dia de hoje
        JSONObject today = obj.getJSONArray("days").getJSONObject(0);

        double temp = today.getDouble("temp");
        double tempmax = today.getDouble("tempmax");
        double tempmin = today.getDouble("tempmin");
        double humidity = today.optDouble("humidity", Double.NaN);
        double precip = today.optDouble("precip", 0.0);
        String conditions = today.optString("conditions", "Sem dados");
        double windspeed = today.optDouble("windspeed", 0.0);
        String winddir = getWindDirection(today.optDouble("winddir", 0.0));

        return new WeatherInfo(temp, tempmax, tempmin, humidity, conditions, precip, windspeed, winddir);
    }

    // Converte graus em texto (N, NE, E, etc)
    private String getWindDirection(double degrees) {
        String[] directions = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        int idx = (int)Math.round(((degrees % 360) / 45)) % 8;
        return directions[idx];
    }
}