import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("ERRO: A chave da API não foi fornecida.");
            System.out.println("Uso: java App <sua_chave_api>");
            return;
        }
        String apiKey = args[0];
        new App().run(apiKey);
    }

    public void run(String apiKey) {
        Scanner scanner = new Scanner(System.in);
        WeatherApiService weatherService = new WeatherApiService(apiKey);

        while (true) {
            System.out.print("\nDigite o nome da cidade (ou 'sair' para terminar): ");
            String city = scanner.nextLine();

            if (city.equalsIgnoreCase("sair")) {
                break;
            }

            WeatherResponse response = weatherService.getWeatherForCity(city);

            if (response != null) {
                displayWeather(response);
            }
        }
        System.out.println("Programa finalizado.");
        scanner.close();
    }

    private void displayWeather(WeatherResponse response) {
        CurrentConditions current = response.getCurrentConditions();
        DailyWeather today = response.getDays().get(0);

        System.out.println("\n--- Clima em " + response.getResolvedAddress() + " ---");
        System.out.printf("Temperatura Atual: %.1f °C\n", current.getTemperature());
        System.out.printf("Máxima para hoje: %.1f °C\n", today.getTempMax());
        System.out.printf("Mínima para hoje: %.1f °C\n", today.getTempMin());
        System.out.printf("Condição: %s\n", current.getConditions());
        System.out.printf("Umidade: %.1f %%\n", current.getHumidity());
        System.out.printf("Precipitação: %.2f mm\n", current.getPrecipitation());
        System.out.printf("Vento: %.1f km/h (%s)\n", current.getWindSpeed(), formatWindDirection(current.getWindDirection()));
        System.out.println("--------------------------------------------------");
    }

    private String formatWindDirection(double degrees) {
        String[] directions = {"N", "NE", "L", "SE", "S", "SW", "O", "NW", "N"};
        return directions[(int)Math.round(degrees / 45.0)];
    }
}