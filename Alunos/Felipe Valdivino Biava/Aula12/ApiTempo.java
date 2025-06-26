import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ApiTempo {
    private final String apiKey = "YU972U7DZTXSF2LUHM8WA7GF6";
    private final Gson gson = new Gson();

    public Tempo getWeather(String city) throws Exception {
        String encodedCity = URLEncoder.encode(city, "UTF-8");

        String urlString = String.format(
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/today?unitGroup=metric&key=%s&contentType=json",
                encodedCity, apiKey);

        HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Erro na requisição: HTTP " + conn.getResponseCode());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder json = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            json.append(line);
        }

        reader.close();

        ListaTempo response = gson.fromJson(json.toString(), ListaTempo.class);
        PadraoTempo day = response.days.get(0);

        Tempo data = new Tempo();
        data.setDate(day.datetime);
        data.setTemp(day.temp);
        data.setTempMax(day.tempmax);
        data.setTempMin(day.tempmin);
        data.setHumidity(day.humidity);
        data.setConditions(day.conditions);
        data.setPrecipitation(day.precip);
        data.setWindSpeed(day.windspeed);
        data.setWindDirection(String.format("%.0f°", day.winddir));

        return data;
    }
}
