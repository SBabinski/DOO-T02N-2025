package Aula12;

import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Consulta de Clima (Visual Crossing) ===");
        System.out.print("Digite o nome da cidade: ");
        String city = scanner.nextLine().trim();

        String apiKey = "XFFVCE8ULHWEQXSERL3VX3JXS";

        WeatherService weatherService = new WeatherService(apiKey);

        try {
            WeatherInfo info = weatherService.getWeatherInfo(city);
            System.out.println("\nInformações do clima para " + city + ":");
            System.out.printf("Temperatura atual: %.1f°C\n", info.getCurrentTemperature());
            System.out.printf("Máxima do dia: %.1f°C\n", info.getMaxTemperature());
            System.out.printf("Mínima do dia: %.1f°C\n", info.getMinTemperature());
            System.out.println("Umidade: " + info.getHumidity() + "%");
            System.out.println("Condição: " + info.getCondition());
            if (info.getPrecipitation() > 0) {
                System.out.printf("Precipitação: %.1f mm\n", info.getPrecipitation());
            } else {
                System.out.println("Precipitação: 0 mm");
            }
            System.out.printf("Vento: %.1f km/h, direção: %s\n", info.getWindSpeed(), info.getWindDirection());
        } catch (Exception e) {
            System.err.println("Erro ao obter informações do clima: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}