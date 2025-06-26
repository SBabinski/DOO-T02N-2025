import java.util.Scanner;

public class ClimaTempoApp {
    public static final String API_KEY = "SLNVKQKSP3MJYLY6SBQKR5SFM";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nOlá! Vamos consultar o clima de uma cidade?");

        String cidade = "";

        while(true) {
            System.out.print("\nDigite o nome da cidade (ou 0 para sair): ");
            cidade = scanner.nextLine();

            if (cidade.equals("0")) {
                System.out.println("Encerrando o programa...");
                break;

            }

            try {
                ClimaService climaService = new ClimaService(API_KEY);
                String json = climaService.buscarClima(cidade);

                ClimaFormatter formatter = new ClimaFormatter();
                formatter.mostrarDados(json);

            } catch (Exception e) {
                System.out.println("Erro ao consultar o clima: " + e.getMessage());
            }

        }

        scanner.close();
    }
}
