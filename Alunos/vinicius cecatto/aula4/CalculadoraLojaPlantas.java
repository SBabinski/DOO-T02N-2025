package aula4;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CalculadoraLojaPlantas {
    private static final double DESCONTO_ESPECIAL = 0.05;
    private static final Map<LocalDate, Integer> registroVendas = new HashMap<>();
    private static final DateTimeFormatter FORMATTER_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static double calcularPrecoTotal(int quantidade, double precoUnitario) {
        double precoTotal = quantidade * precoUnitario;
        double desconto = (quantidade > 10) ? precoTotal * DESCONTO_ESPECIAL : 0;
        return precoTotal - desconto;
    }

    public static void registrarVenda(int quantidade) {
        LocalDate hoje = LocalDate.now();
        registroVendas.put(hoje, registroVendas.getOrDefault(hoje, 0) + quantidade);
    }

    public static int buscarVendasPorDia(LocalDate data) {
        return registroVendas.getOrDefault(data, 0);
    }

    public static int buscarVendasPorMes(int ano, int mes) {
        YearMonth yearMonth = YearMonth.of(ano, mes);
        return registroVendas.entrySet().stream()
                .filter(entry -> YearMonth.from(entry.getKey()).equals(yearMonth))
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    public static void processarVenda(Scanner scanner) {
        try {
            System.out.print("Digite a quantidade de plantas: ");
            int quantidade = scanner.nextInt();

            System.out.print("Digite o preço unitário da planta: ");
            double precoUnitario = scanner.nextDouble();

            double precoFinal = calcularPrecoTotal(quantidade, precoUnitario);
            registrarVenda(quantidade);

            System.out.printf("Preço final da compra (aplicado desconto, se houver): R$ %.2f%n", precoFinal);
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida! Tente novamente.");
            scanner.next();
        }
    }

    public static void exibirVendasPorDia(Scanner scanner) {
        System.out.print("Digite a data (DD/MM/AAAA) para verificar as vendas: ");
        String dataStr = scanner.next();
        LocalDate data = LocalDate.parse(dataStr, FORMATTER_BR);
        int vendas = buscarVendasPorDia(data);
        System.out.printf("Total de vendas em %s: %d%n", data.format(FORMATTER_BR), vendas);
    }

    public static void exibirVendasPorMes(Scanner scanner) {
        System.out.print("Digite o ano (AAAA): ");
        int ano = scanner.nextInt();
        System.out.print("Digite o mês (MM): ");
        int mes = scanner.nextInt();
        int vendas = buscarVendasPorMes(ano, mes);
        System.out.printf("Total de vendas no mês %02d/%d: %d%n", mes, ano, vendas);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n===== Calculadora da Loja de Plantas =====");
            System.out.println("[1] - Realizar Venda");
            System.out.println("[2] - Consultar Vendas por Dia");
            System.out.println("[3] - Consultar Vendas por Mês");
            System.out.println("[4] - Sair");

            try {
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();

                switch (opcao) {
                    case 1 -> processarVenda(scanner);
                    case 2 -> exibirVendasPorDia(scanner);
                    case 3 -> exibirVendasPorMes(scanner);
                    case 4 -> System.out.println("Saindo... Obrigado por utilizar a calculadora!");
                    default -> System.out.println("Opção inválida! Escolha entre 1 e 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número.");
                scanner.next();
                opcao = 0;
            }
        } while (opcao != 4);

        scanner.close();
    }
}
