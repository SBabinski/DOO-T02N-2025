package Wheater;

import java.util.Scanner;

public class WeatherApp {

    private static final WeatherService weatherService = new WeatherService();

    public static void main(String[] args) {
        System.out.println("APLICATIVO DE CONSULTA METEOROLÓGICA");
        System.out.println("Powered by Visual Crossing Weather API");
        System.out.println("=".repeat(50));

        Scanner scanner = new Scanner(System.in);

        if (args.length > 0) {
            System.setProperty("weather.api.key", args[0]);
        }

        AuthService.validateApiKey(scanner);

        while (true) {
            try {
                System.out.print("\nDigite o nome da cidade (ou 'sair' para encerrar): ");
                String city = scanner.nextLine().trim();

                if ("sair".equalsIgnoreCase(city)) {
                    System.out.println("\nEncerrando aplicação. Até logo!");
                    break;
                }

                if (city.isEmpty()) {
                    System.out.println("Por favor, digite um nome de cidade válido.");
                    continue;
                }

                System.out.println("\nBuscando informações meteorológicas para: " + city);
                WeatherData weatherData = weatherService.getWeatherData(city);
                weatherService.displayWeatherInfo(weatherData);

            } catch (WeatherApiException e) {
                System.err.println("\nErro: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("\nErro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }

        scanner.close();
    }
}
