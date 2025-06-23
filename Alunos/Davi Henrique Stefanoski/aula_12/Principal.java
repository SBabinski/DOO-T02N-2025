package clima;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Principal {

    private static final String API_KEY = "Q5RKN64D4VUDRMDVVC3AW9Y62";
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    public static void main(String[] args) {
        menuPrincipal();
    }

    public static void menuPrincipal() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== CLIMA APP =====");
            System.out.println("1. Pesquisar clima por cidade");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = sc.nextLine().trim();

            switch (opcao) {
                case "1":
                    pesquisarClima(sc);
                    break;
                case "0":
                    System.out.println("Saindo do sistema...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void pesquisarClima(Scanner sc) {
        System.out.print("Digite o nome da cidade: ");
        String cidade = sc.nextLine().trim();

        final String url = construirUrl(cidade);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();

            JsonObject atual = root.getAsJsonObject("currentConditions");
            JsonObject dia = root.getAsJsonArray("days").get(0).getAsJsonObject();

            final String condicaoOriginal = atual.has("conditions") && !atual.get("conditions").isJsonNull()
                    ? atual.get("conditions").getAsString()
                    : "Desconhecido";

            final String condicaoTraduzida = traduzirCondicao(condicaoOriginal);

            Clima clima = new Clima(
                    root.get("resolvedAddress").getAsString(),
                    getValorDouble(atual, "temp"),
                    getValorDouble(dia, "tempmax"),
                    getValorDouble(dia, "tempmin"),
                    getValorDouble(atual, "humidity"),
                    condicaoTraduzida,
                    getValorDouble(atual, "precip"),
                    getValorDouble(atual, "windspeed"),
                    atual.has("winddir") && !atual.get("winddir").isJsonNull()
                            ? String.format("%.0f°", atual.get("winddir").getAsDouble())
                            : "Desconhecido"
            );

            clima.exibir();

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao consultar a API: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao interpretar os dados. Verifique se o nome da cidade está correto.");
            e.printStackTrace(); // Ajuda na depuração
        }
    }

    private static String construirUrl(String cidade) {
        String cidadeCodificada = URLEncoder.encode(cidade, StandardCharsets.UTF_8);
        return BASE_URL + cidadeCodificada +
                "?unitGroup=metric&include=current,days&key=" + API_KEY + "&contentType=json";
    }

    private static double getValorDouble(JsonObject obj, String chave) {
        return obj.has(chave) && !obj.get(chave).isJsonNull() ? obj.get(chave).getAsDouble() : 0.0;
    }

    public static String traduzirCondicao(String condicao) {
        String c = condicao.toLowerCase();

        switch (c) {
            case "clear": return "Céu limpo";
            case "partly cloudy":
            case "partially cloudy": return "Parcialmente nublado";
            case "cloudy": return "Nublado";
            case "overcast": return "Encoberto";
            case "rain": return "Chuva";
            case "light rain": return "Chuva leve";
            case "heavy rain": return "Chuva forte";
            case "snow": return "Neve";
            case "fog": return "Nevoeiro";
            case "thunderstorm": return "Trovoadas";
            case "drizzle": return "Garoa";
            default: return Character.toUpperCase(c.charAt(0)) + c.substring(1);
        }
    }
}
