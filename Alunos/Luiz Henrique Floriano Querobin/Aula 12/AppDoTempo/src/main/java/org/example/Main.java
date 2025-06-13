package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

    private static final String API_KEY = System.getenv("VISUAL_CROSSING_API_KEY");
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    public static void main(String[] args) {
        if (API_KEY == null || API_KEY.isEmpty()) {
            System.err.println("Erro: variável de ambiente VISUAL_CROSSING_API_KEY não definida.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine().trim();

        try {
            WeatherData weather = fetchWeatherData(cidade);
            if (weather != null) {
                System.out.println(weather);
            } else {
                System.out.println("Não foi possível obter os dados climáticos.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar o clima: " + e.getMessage());
        }
    }

    private static WeatherData fetchWeatherData(String location) throws IOException {
        String urlString = BASE_URL + location + "?unitGroup=metric&key=" + API_KEY + "&include=current";

        HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
        connection.setRequestMethod("GET");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(connection.getInputStream());

        JsonNode current = root.path("currentConditions");
        JsonNode today = root.path("days").get(0);

        if (current.isMissingNode() || today.isMissingNode()) return null;

        WeatherData data = new WeatherData();
        data.setLocal(location);
        data.setTempAtual(current.path("temp").asDouble());
        data.setTempMax(today.path("tempmax").asDouble());
        data.setTempMin(today.path("tempmin").asDouble());
        data.setUmidade(current.path("humidity").asDouble());
        data.setCondicao(current.path("conditions").asText());
        data.setPrecipitacao(current.path("precip").asDouble());
        data.setVentoVelocidade(current.path("windspeed").asDouble());
        data.setVentoDirecao(current.path("winddir").asDouble());

        return data;
    }
}
