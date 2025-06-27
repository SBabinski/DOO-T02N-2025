package Aula12;

import com.google.gson.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {
    private static final String API_KEY = "XUKQ4LNHPDV5ZE4PQLQU487GE"; // coloque sua chave aqui
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    public static Clima obterClima(String cidade) {
        try {
            String urlString = BASE_URL + cidade + "?unitGroup=metric&key=" + API_KEY + "&contentType=json";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

            JsonObject hoje = json.getAsJsonArray("days").get(0).getAsJsonObject();

            Clima clima = new Clima();
            clima.cidade = cidade;
            clima.temperaturaAtual = hoje.get("temp").getAsDouble();
            clima.temperaturaMaxima = hoje.get("tempmax").getAsDouble();
            clima.temperaturaMinima = hoje.get("tempmin").getAsDouble();
            clima.umidade = hoje.get("humidity").getAsDouble();
            clima.condicaoTempo = hoje.get("conditions").getAsString();
            clima.precipitacao = hoje.has("precip") ? hoje.get("precip").getAsDouble() : 0;
            clima.velocidadeVento = hoje.get("windspeed").getAsDouble();
            clima.direcaoVento = hoje.get("winddir").getAsString();

            return clima;

        } catch (Exception e) {
            System.out.println("Erro ao buscar dados do clima: " + e.getMessage());
            return null;
        }
    }
}
