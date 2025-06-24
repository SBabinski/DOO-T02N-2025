import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {
    private static final String API_KEY = "QEA4BA36M4475KYCGKY6HW9C6";

    public static WeatherInfo getWeatherInfo(String cidade) throws Exception {
        String urlStr = String.format(
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/today?unitGroup=metric&key=%s&contentType=json",
                cidade.replace(" ", "%20"), API_KEY);

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Erro HTTP: " + conn.getResponseCode());
        }

        JsonObject json = JsonParser.parseReader(new InputStreamReader(conn.getInputStream())).getAsJsonObject();
        JsonObject dia = json.getAsJsonArray("days").get(0).getAsJsonObject();

        WeatherInfo info = new WeatherInfo();
        info.temp = dia.get("temp").getAsDouble();
        info.tempMin = dia.get("tempmin").getAsDouble();
        info.tempMax = dia.get("tempmax").getAsDouble();
        info.humidity = dia.get("humidity").getAsDouble();
        info.precip = dia.get("precip").getAsDouble();
        info.conditions = dia.get("conditions").getAsString();
        info.windSpeed = dia.get("windspeed").getAsDouble();
        info.windDir = dia.get("winddir").getAsDouble();

        return info;
    }
}
