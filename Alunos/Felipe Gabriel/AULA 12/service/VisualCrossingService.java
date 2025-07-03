package service;

import com.google.gson.*;
import model.Clima;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VisualCrossingService {
    private final String API_KEY = "SUA_CHAVE_AQUI"; 

    public Clima buscarClima(String cidade) {
        try {
            String urlStr = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                    + cidade.replace(" ", "%20")
                    + "?unitGroup=metric&key=" + API_KEY + "&include=current";

            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            conn.setRequestMethod("GET");

            JsonObject json = JsonParser.parseReader(new InputStreamReader(conn.getInputStream())).getAsJsonObject();

            JsonObject current = json.getAsJsonObject("currentConditions");
            JsonArray dias = json.getAsJsonArray("days");
            JsonObject hoje = dias.get(0).getAsJsonObject();

            String data = hoje.get("datetime").getAsString();
            double tempAtual = current.get("temp").getAsDouble();
            double tempMax = hoje.get("tempmax").getAsDouble();
            double tempMin = hoje.get("tempmin").getAsDouble();
            int umidade = current.get("humidity").getAsInt();
            String condicao = current.get("conditions").getAsString();
            double precipitacao = current.has("precip") && !current.get("precip").isJsonNull() ? current.get("precip").getAsDouble() : 0.0;
            double ventoVel = current.get("windspeed").getAsDouble();
            String ventoDir = current.get("winddir").getAsString();

            return new Clima(cidade, data, tempAtual, tempMax, tempMin, umidade, condicao, precipitacao, ventoVel, ventoDir);

        } catch (Exception e) {
            System.out.println("Erro ao buscar dados: " + e.getMessage());
            return null;
        }
    }
}