import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherApp {

    private static final String API_KEY = "RBTT8VR2933FN4Y8DKGFF4L4D"; 
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cidade;

        System.out.println("Digite o nome da cidade para obter o clima.");
        System.out.println("Digite 'sair' para encerrar.");

        while (true) {
            System.out.print("\nCidade: ");
            cidade = scanner.nextLine().trim();

            if (cidade.equalsIgnoreCase("sair")) {
                System.out.println("Programa encerrado.");
                break;
            }

            if (cidade.isEmpty()) {
                System.out.println("Por favor, digite um nome válido.");
                continue;
            }

            try {
                WeatherData clima = obterClimaAtual(cidade);
                System.out.println(clima);
            } catch (IOException e) {
                System.err.println("Erro ao buscar dados do clima: " + e.getMessage());
            }
        }

        scanner.close();
    }

    public static WeatherData obterClimaAtual(String cidade) throws IOException {
        String cidadeUrl = cidade.replace(" ", "%20");
        String url = BASE_URL + cidadeUrl +
                     "?unitGroup=metric&key=" + API_KEY + "&include=current";

        HttpURLConnection conexao = (HttpURLConnection) new URL(url).openConnection();
        conexao.setRequestMethod("GET");

        if (conexao.getResponseCode() != 200) {
            throw new IOException("Erro na conexão: HTTP " + conexao.getResponseCode());
        }

        Scanner scanner = new Scanner(conexao.getInputStream());
        StringBuilder resposta = new StringBuilder();
        while (scanner.hasNext()) {
            resposta.append(scanner.nextLine());
        }
        scanner.close();

        return parseClimaJson(resposta.toString());
    }

    private static WeatherData parseClimaJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        JsonNode current = root.path("currentConditions");
        JsonNode today = root.path("days").get(0);

        return new WeatherData(
            root.path("resolvedAddress").asText(),
            current.path("temp").asDouble(),
            today.path("tempmax").asDouble(),
            today.path("tempmin").asDouble(),
            current.path("humidity").asDouble(),
            current.path("conditions").asText(),
            current.path("precip").asDouble(),
            current.path("windspeed").asDouble(),
            current.path("winddir").asDouble()
        );
    }
}
