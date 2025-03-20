import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CalculadoraPlantas {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Integer> vendas = new HashMap<>();

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n=== Calculadora Loja de Plantas ===");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Registrar Venda");
            System.out.println("[4] - Consultar Vendas por Dia");
            System.out.println("[5] - Consultar Vendas por Mês");
            System.out.println("[6] - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    calcularPrecoTotal();
                    break;
                case 2:
                    calcularTroco();
                    break;
                case 3:
                    registrarVenda();
                    break;
                case 4:
                    consultarVendasPorDia();
                    break;
                case 5:
                    consultarVendasPorMes();
                    break;
                case 6:
                    System.out.println("Saindo... Obrigado por usar a calculadora!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 6);

        scanner.close();
    }

    private static void calcularPrecoTotal() {
        System.out.print("Digite a quantidade da planta: ");
        int quantidade = scanner.nextInt();
        System.out.print("Digite o preço unitário da planta: ");
        double precoUnitario = scanner.nextDouble();

        double precoTotal = quantidade * precoUnitario;
        if (quantidade > 10) {
            precoTotal *= 0.95;
            System.out.println("Desconto de 5% aplicado!");
        }
        System.out.printf("O preço total da compra é: R$ %.2f\n", precoTotal);
    }

    private static void calcularTroco() {
        System.out.print("Digite o valor recebido do cliente: ");
        double valorRecebido = scanner.nextDouble();
        System.out.print("Digite o valor total da compra: ");
        double valorTotal = scanner.nextDouble();

        if (valorRecebido < valorTotal) {
            System.out.println("O valor recebido é insuficiente!");
        } else {
            double troco = valorRecebido - valorTotal;
            System.out.printf("O troco a ser dado ao cliente é: R$ %.2f\n", troco);
        }
    }

    private static void registrarVenda() {
        System.out.print("Digite o dia da venda: ");
        int dia = scanner.nextInt();
        System.out.print("Digite o mês da venda: ");
        int mes = scanner.nextInt();
        System.out.print("Digite a quantidade de plantas vendidas: ");
        int quantidade = scanner.nextInt();

        String chave = mes + "-" + dia;
        vendas.put(chave, vendas.getOrDefault(chave, 0) + quantidade);
        System.out.println("Venda registrada.");
    }

    private static void consultarVendasPorDia() {
        System.out.print("Digite o dia da consulta: ");
        int dia = scanner.nextInt();
        System.out.print("Digite o mês da consulta: ");
        int mes = scanner.nextInt();

        String chave = mes + "-" + dia;
        int total = vendas.getOrDefault(chave, 0);
        System.out.println("Total de vendas no dia " + dia + "/" + mes + ": " + total);
    }

    private static void consultarVendasPorMes() {
        System.out.print("Digite o mês da consulta: ");
        int mes = scanner.nextInt();

        int total = vendas.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(mes + "-"))
                .mapToInt(Map.Entry::getValue)
                .sum();

        System.out.println("Total de vendas no mês " + mes + ": " + total);
    }
}
