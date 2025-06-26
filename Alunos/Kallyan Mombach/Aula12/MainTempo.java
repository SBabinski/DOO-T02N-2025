import java.util.Scanner;

public class MainTempo {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            TempoEstrutura weatherService = new TempoEstrutura();

            System.out.print("Digite o nome da cidade: ");
            String city = scanner.nextLine();

            try {
                ClasseTempo info = weatherService.getWeather(city);
                System.out.println("\n=== Informações do Tempo ===");
                System.out.println(info);
            } catch (Exception e) {
                System.err.println("Erro ao buscar clima: " + e.getMessage());
            }
        } // scanner será fechado automaticamente aqui
    }
}
