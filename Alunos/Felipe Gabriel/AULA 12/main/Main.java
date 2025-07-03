import model.Clima;
import service.VisualCrossingService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        VisualCrossingService climaService = new VisualCrossingService();

        System.out.println("🌤️ Bem-vindo ao ClimaTempoApp!");
        System.out.print("Digite o nome da cidade: ");
        String cidade = sc.nextLine();

        Clima clima = climaService.buscarClima(cidade);

        if (clima != null) {
            System.out.println(clima);
        } else {
            System.out.println("❌ Não foi possível obter as informações do clima.");
        }
    }
}
