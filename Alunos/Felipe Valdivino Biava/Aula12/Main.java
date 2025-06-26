import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ApiTempo service = new ApiTempo();

        try {
            int op;
            do {
                System.out.println("\n--- BEM VINDO AO WEATHER ---");
                System.out.println("[1] Pesquisar cidade");
                System.out.println("[2] Sair");
                System.out.print("Escolha uma opção: ");
                op = scan.nextInt();
                scan.nextLine();

                switch (op) {
                    case 1:
                        System.out.print("Digite o nome da cidade: ");
                        String city = scan.nextLine().trim();
                        //esse .trim serve para tirar os espaços do começo e do fim da variável

                        try {
                            Tempo data = service.getWeather(city);
                            System.out.println("\n=== Clima para " + city + " ===");
                            System.out.println(data);
                        } catch (Exception e) {
                            System.out.println("Erro ao buscar os dados: " + e.getMessage());
                        }
                        break;

                    case 2:
                        System.out.println("Encerrando o sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            } while (op != 2);

        } catch (Exception e) {
            System.out.println("Erro no sistema: " + e.getMessage());
        }

        scan.close();
    }
}
