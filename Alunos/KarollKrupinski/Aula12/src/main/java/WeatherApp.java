import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine();

        try {
            WeatherInfo info = WeatherService.getWeatherInfo(cidade);
            System.out.println("\n--- Clima em " + cidade + " ---");
            System.out.println("Temperatura atual: " + info.temp + "°C");
            System.out.println("Temperatura mínima: " + info.tempMin + "°C");
            System.out.println("Temperatura máxima: " + info.tempMax + "°C");
            System.out.println("Condição: " + info.conditions);
            System.out.println("Umidade: " + info.humidity + "%");
            System.out.println("Precipitação: " + info.precip + " mm");
            System.out.println("Vento: " + info.windSpeed + " km/h, direção " + info.windDir);
        } catch (Exception e) {
            System.out.println("Erro ao obter dados do clima: " + e.getMessage());
        }

        scanner.close();
    }
}
