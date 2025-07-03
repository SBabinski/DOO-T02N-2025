package weatherapp;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


import org.json.JSONObject;

public class WeatherApp {
    private static final String API_KEY = "J228DJAYPVEEN2UFBSEHLNLUX";
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
 
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Buscar Clima por Cidade");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            String op = scanner.nextLine();

            if (op.equals("1")) {
                System.out.print("Digite o nome da cidade (ex: 'Cascavel,BR'): ");
                String cidade = scanner.nextLine().trim();
                buscarClima(cidade);
            } else if (op.equals("0")) {
                System.out.println("Saindo...");
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }

    private static void buscarClima(String cidade) {
        try {
            String url = BASE_URL + cidade.replace(" ", "%20") +
                         "?unitGroup=metric&key=" + API_KEY + "&contentType=json";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 401) {
                System.out.println("Erro 401: Chave de API inválida.");
                return;
            } else if (response.statusCode() != 200) {
                System.out.println("Erro: Código HTTP " + response.statusCode());
                return;
            }

            JSONObject json = new JSONObject(response.body());
            JSONObject current = json.getJSONObject("currentConditions");
            JSONObject hoje = json.getJSONArray("days").getJSONObject(0);

            System.out.println("\n══════════════════════════════════════════");
            System.out.printf("CLIMA EM: %s\n", json.getString("resolvedAddress").toUpperCase());
            System.out.println("══════════════════════════════════════════");
            System.out.printf("Temperatura atual:     %.1f °C\n", current.getDouble("temp"));
            System.out.printf("Temperatura mínima:    %.1f °C\n", hoje.getDouble("tempmin"));
            System.out.printf("Temperatura máxima:    %.1f °C\n", hoje.getDouble("tempmax"));
            System.out.printf("Umidade:               %.1f %%\n", current.getDouble("humidity"));
            System.out.printf("Condição do tempo:     %s\n", current.getString("conditions"));
            System.out.printf("Precipitação:          %.1f mm\n", current.optDouble("precip", 0));
            System.out.printf("Velocidade do vento:   %.1f km/h\n", current.getDouble("windspeed"));
            System.out.printf("Direção do vento:      %.1f °\n", current.getDouble("winddir"));
            System.out.println("══════════════════════════════════════════");

        } catch (Exception e) {
            System.out.println("Erro ao obter dados: " + e.getMessage());
        }
    }
}