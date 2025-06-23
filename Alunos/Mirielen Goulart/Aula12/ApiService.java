package climaAPI;

import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class ApiService {

    public static final String CHAVE_API = "5HNA86W4BMC4KSN26E5PDCJCX";  // Sua chave da API
    public static final String URL_BASE = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private final HttpClient clienteHttp = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))  // Timeout de 10 segundos
            .build();

    public Clima buscarClima(String cidade) {
        try {
            String cidadeFormatada = URLEncoder.encode(cidade, StandardCharsets.UTF_8);

            String url = URL_BASE + cidadeFormatada + "/today?unitGroup=metric&key=" + CHAVE_API + "&contentType=json";

            HttpRequest requisicao = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .timeout(Duration.ofSeconds(15))  // Timeout da resposta
                    .build();

            HttpResponse<String> resposta = clienteHttp.send(requisicao, HttpResponse.BodyHandlers.ofString());

            if (resposta.statusCode() != 200) {
                System.out.println("Erro na requisição HTTP: Código " + resposta.statusCode());
                System.out.println("Resposta da API: " + resposta.body().substring(0, Math.min(300, resposta.body().length())) + "...");
                return null;
            }

            JsonObject json;
            try {
                json = JsonParser.parseString(resposta.body()).getAsJsonObject();
            } catch (JsonParseException e) {
                System.out.println("Erro ao interpretar a resposta JSON da API.");
                return null;
            }

            if (!json.has("currentConditions") || !json.has("days")) {
                System.out.println("Resposta da API não contém os campos esperados.");
                return null;
            }

            JsonObject condicoesAtuais = json.getAsJsonObject("currentConditions");
            JsonArray dias = json.getAsJsonArray("days");

            if (dias == null || dias.size() == 0) {
                System.out.println("Nenhum dado de previsão disponível para a cidade informada.");
                return null;
            }

            JsonObject hoje = dias.get(0).getAsJsonObject();

            double temperaturaAtual = condicoesAtuais.has("temp") ? condicoesAtuais.get("temp").getAsDouble() : 0.0;
            double temperaturaMax = hoje.has("tempmax") ? hoje.get("tempmax").getAsDouble() : 0.0;
            double temperaturaMin = hoje.has("tempmin") ? hoje.get("tempmin").getAsDouble() : 0.0;
            double umidade = condicoesAtuais.has("humidity") ? condicoesAtuais.get("humidity").getAsDouble() : 0.0;
            String condicao = condicoesAtuais.has("conditions") ? condicoesAtuais.get("conditions").getAsString() : "N/A";
            double precipitacao = hoje.has("precip") ? hoje.get("precip").getAsDouble() : 0.0;
            double velocidadeVento = condicoesAtuais.has("windspeed") ? condicoesAtuais.get("windspeed").getAsDouble() : 0.0;
            String direcaoVento = condicoesAtuais.has("winddir") ? condicoesAtuais.get("winddir").getAsString() : "N/A";

            return new Clima(temperaturaAtual, temperaturaMin, temperaturaMax, umidade,
                    condicao, precipitacao, velocidadeVento, direcaoVento);

        } catch (IOException e) {
            System.out.println("Erro de conexão com a API: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("A requisição foi interrompida: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }

        return null;
    }
}
