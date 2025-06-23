package climaAPI;

import climaAPI.ApiService;
import climaAPI.Clima;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ApiService servico = new ApiService();

        System.out.println("=== Aplicativo de Consulta de Clima ===");

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n--- Menu ---");
            System.out.println("[1] Consultar Clima");
            System.out.println("[2] Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1":
                    System.out.print("\nDigite o nome da cidade: ");
                    String cidade = scanner.nextLine().trim();

                    if (cidade.isEmpty()) {
                        System.out.println("Erro: Você precisa digitar o nome de uma cidade.");
                        break;
                    }

                    Clima clima = servico.buscarClima(cidade);

                    if (clima != null) {
                        System.out.println("\n=== Informações do Tempo para " + cidade + " ===");
                        System.out.println(clima);
                    } else {
                        System.out.println("Não foi possível obter os dados do clima para a cidade informada.");
                    }
                    break;

                case "2":
                    System.out.println("Encerrando o programa. Até mais!");
                    continuar = false;
                    break;

                default:
                    System.out.println("Opção inválida! Por favor, escolha 1 ou 2.");
                    break;
            }
        }

        scanner.close();
    }
}
