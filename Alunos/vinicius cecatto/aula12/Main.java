package aula12;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine();

        String apiKey = "V726HW7B7XGLHS7PAQHBD5M7Y";

        WeatherFetcher fetcher = new WeatherFetcher(apiKey);
        WeatherInfo info = fetcher.getWeather(cidade);

        if (info != null) {
            // Get the address from the WeatherInfo object
            String address = info.getAddress();

            // Perform the address length validation
            // The API might return an address like "NY" for New York, which is short.
            // This check assumes that addresses shorter than 3 characters are invalid or incomplete.
            if (address == null || address.trim().length() <= 2) {
                System.out.println("Cidade não encontrada ou nome muito curto. Tente digitar o nome completo.");
                return; // Exit the program if the address is too short/invalid
            }

            System.out.println(info);
        } else {
            System.out.println("Não foi possível obter os dados do clima.");
            System.out.println("Verifique o nome da cidade ou sua conexão com a internet.");
        }

        scanner.close(); // Close the scanner
    }
}