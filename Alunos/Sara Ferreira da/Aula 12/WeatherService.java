import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherService {

    public static void buscarClima(String cidade, String apiKey) {
        try {
            String urlStr = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                    + cidade.replace(" ", "%20") +
                    "?unitGroup=metric&include=current&key=" + apiKey + "&contentType=json";

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Imprime o JSON retornado para análise
            System.out.println("JSON retornado:");
            System.out.println(response.toString());

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();

            if (jsonObject == null || jsonObject.isJsonNull()) {
                System.out.println("Resposta JSON está vazia ou inválida.");
                return;
            }

            if (!jsonObject.has("currentConditions") || jsonObject.get("currentConditions").isJsonNull()) {
                System.out.println("Dados de 'currentConditions' não encontrados na resposta.");
                return;
            }

            JsonObject currentConditions = jsonObject.getAsJsonObject("currentConditions");

            double tempAtual = getDoubleOrDefault(currentConditions, "temp", 0.0);
            double umidade = getDoubleOrDefault(currentConditions, "humidity", 0.0);
            String condicaoTempo = currentConditions.has("conditions") && !currentConditions.get("conditions").isJsonNull()
                    ? currentConditions.get("conditions").getAsString()
                    : "Desconhecido";
            double velocidadeVento = getDoubleOrDefault(currentConditions, "windspeed", 0.0);
            double direcaoVento = getDoubleOrDefault(currentConditions, "winddir", 0.0);
            double precipitacao = getDoubleOrDefault(currentConditions, "precip", 0.0);

            if (!jsonObject.has("days") || jsonObject.get("days").isJsonNull()) {
                System.out.println("Dados de 'days' não encontrados na resposta.");
                return;
            }

            JsonArray days = jsonObject.getAsJsonArray("days");

            if (days.size() == 0) {
                System.out.println("Array 'days' está vazio.");
                return;
            }

            JsonObject hoje = days.get(0).getAsJsonObject();
            double tempMax = getDoubleOrDefault(hoje, "tempmax", 0.0);
            double tempMin = getDoubleOrDefault(hoje, "tempmin", 0.0);

            System.out.println("\n--- Clima para: " + cidade + " ---");
            System.out.println("Temperatura atual: " + tempAtual + "°C");
            System.out.println("Temperatura máxima do dia: " + tempMax + "°C");
            System.out.println("Temperatura mínima do dia: " + tempMin + "°C");
            System.out.println("Umidade: " + umidade + "%");
            System.out.println("Condição do tempo: " + condicaoTempo);
            System.out.println("Precipitação: " + precipitacao + " mm");
            System.out.println("Velocidade do vento: " + velocidadeVento + " km/h");
            System.out.println("Direção do vento: " + direcaoVento + "°");

        } catch (Exception e) {
            System.out.println("Erro ao buscar o clima: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static double getDoubleOrDefault(JsonObject obj, String memberName, double defaultValue) {
        if (obj.has(memberName) && !obj.get(memberName).isJsonNull()) {
            try {
                return obj.get(memberName).getAsDouble();
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
}
