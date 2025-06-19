package sistemaTempo.service;

import com.google.gson.*;
import sistemaTempo.Clima;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClimaService {

    private static final String API_KEY = "PDU6UCCUSHJSSHAW52Q9M5GXD";

    public Clima obterClima(String cidade) throws Exception {
        HttpURLConnection conn = getHttpURLConnection(cidade);

        JsonObject json = JsonParser.parseReader(new InputStreamReader(conn.getInputStream())).getAsJsonObject();
        JsonObject current = json.getAsJsonObject("currentConditions");
        JsonObject dia = json.getAsJsonArray("days").get(0).getAsJsonObject();

        Clima clima = new Clima();
        clima.setTemperaturaAtual(current.get("temp").getAsDouble());
        clima.setTemperaturaMax(dia.get("tempmax").getAsDouble());
        clima.setTemperaturaMin(dia.get("tempmin").getAsDouble());
        clima.setUmidade(current.get("humidity").getAsInt());
        clima.setCondicao(current.get("conditions").getAsString());
        clima.setPrecipitacao(current.has("precip") ? current.get("precip").getAsDouble() : 0.0);
        clima.setVelocidadeVento(current.get("windspeed").getAsDouble());
        clima.setDirecaoVento(String.valueOf(current.get("winddir").getAsDouble()));

        conn.disconnect();
        return clima;
    }

    private static HttpURLConnection getHttpURLConnection(String cidade) throws IOException {
        String endpoint = String.format(
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/today?unitGroup=metric&key=%s&include=current",
                cidade, API_KEY);

        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Erro ao conectar à API: " + conn.getResponseCode());
        }
        return conn;
    }
}
