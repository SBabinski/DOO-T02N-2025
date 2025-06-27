package Aula12;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine();

        Clima clima = WeatherService.obterClima(cidade);

        if (clima != null) {
            System.out.println(clima);
        } else {
            System.out.println("Não foi possível obter os dados climáticos.");
        }

        scanner.close();
    }
}
