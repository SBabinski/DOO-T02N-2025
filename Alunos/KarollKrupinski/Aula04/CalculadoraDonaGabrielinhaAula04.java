package calculadoraDonaGabrielinhaAula04;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CalculadoraDonaGabrielinhaAula04 {

    private static Map<Integer, Map<Integer, Integer>> vendas = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            exibirMenu();
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    registrarVenda(scanner);
                    break;
                case 2:
                    buscarVendasPorDia(scanner);
                    break;
                case 3:
                    buscarVendasPorMes(scanner);
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 4);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("[1] - Registrar Venda");
        System.out.println("[2] - Buscar Vendas por Dia");
        System.out.println("[3] - Buscar Vendas por Mês");
        System.out.println("[4] - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void registrarVenda(Scanner scanner) {
        System.out.print("Digite o mês (1-12): ");
        int mes = scanner.nextInt();
        System.out.print("Digite o dia (1-31): ");
        int dia = scanner.nextInt();
        System.out.print("Digite a quantidade de plantas vendidas: ");
        int quantidade = scanner.nextInt();

        // Verifica se o mês já existe no mapa
        if (!vendas.containsKey(mes)) {
            vendas.put(mes, new HashMap<>());
        }

        // Verifica se o dia já existe no mês
        Map<Integer, Integer> vendasDoMes = vendas.get(mes);
        vendasDoMes.put(dia, vendasDoMes.getOrDefault(dia, 0) + quantidade);

        System.out.println("Venda registrada com sucesso!");
    }

    private static void buscarVendasPorDia(Scanner scanner) {
        System.out.print("Digite o mês (1-12): ");
        int mes = scanner.nextInt();
        System.out.print("Digite o dia (1-31): ");
        int dia = scanner.nextInt();

        if (vendas.containsKey(mes) && vendas.get(mes).containsKey(dia)) {
            int quantidade = vendas.get(mes).get(dia);
            System.out.println("Vendas no dia " + dia + "/" + mes + ": " + quantidade + " plantas.");
        } else {
            System.out.println("Nenhuma venda registrada para o dia " + dia + "/" + mes + ".");
        }
    }

    private static void buscarVendasPorMes(Scanner scanner) {
        System.out.print("Digite o mês (1-12): ");
        int mes = scanner.nextInt();

        if (vendas.containsKey(mes)) {
            int totalVendas = vendas.get(mes).values().stream().mapToInt(Integer::intValue).sum();
            System.out.println("Total de vendas no mês " + mes + ": " + totalVendas + " plantas.");
        } else {
            System.out.println("Nenhuma venda registrada para o mês " + mes + ".");
        }
    }
}