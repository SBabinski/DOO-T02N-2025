package aula12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherFetcher {
    private final String apiKey;

    public WeatherFetcher(String apiKey) {
        this.apiKey = apiKey;
    }

    public WeatherInfo getWeather(String cidade) {
        try {
            String urlStr = String.format(
                    "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/today?unitGroup=metric&key=%s&contentType=json",
                    cidade.replace(" ", "%20"), apiKey
            );

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {

                BufferedReader errorBr = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorJson = new StringBuilder();
                String errorLine;
                while ((errorLine = errorBr.readLine()) != null) {
                    errorJson.append(errorLine);
                }
                errorBr.close();
                System.out.println("Erro na resposta da API (" + responseCode + "): " + errorJson.toString());
                return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                json.append(line);
            }

            br.close();

            JSONObject obj = new JSONObject(json.toString());

            String address = obj.getString("address");

            JSONObject hoje = obj.getJSONArray("days").getJSONObject(0);
            JSONObject condicoesAtuais = obj.getJSONObject("currentConditions");

            double tempAtual = condicoesAtuais.getDouble("temp");
            double tempMax = hoje.getDouble("tempmax");
            double tempMin = hoje.getDouble("tempmin");
            double umidade = condicoesAtuais.getDouble("humidity");
            String condicao = condicoesAtuais.getString("conditions");
            double precipitacao = hoje.optDouble("precip", 0);
            double ventoVel = condicoesAtuais.getDouble("windspeed");
            double ventoDir = condicoesAtuais.getDouble("winddir");

            return new WeatherInfo(cidade, tempAtual, tempMax, tempMin, umidade, condicao, precipitacao, ventoVel, ventoDir, address);

        } catch (Exception e) {
            System.out.println("Erro ao buscar dados: " + e.getMessage());
            return null;
        }
    }
}
