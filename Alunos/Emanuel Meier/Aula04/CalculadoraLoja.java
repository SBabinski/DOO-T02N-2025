package fag;

import java.util.*;

public class CalculadoraLoja {
    private static final Map<String, List<Venda>> REGISTRO_VENDAS = new HashMap<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        int opcao;
        do {
            System.out.println("\n--- Calculadora da Dona Gabrielinha ---");
            System.out.println("[1] - Registrar Venda");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Mostrar Histórico de Vendas");
            System.out.println("[4] - Buscar Vendas por Dia");
            System.out.println("[5] - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    registrarVenda(scanner);
                    break;
                case 2:
                    calcularTroco(scanner);
                    break;
                case 3:
                    mostrarHistoricoVendas();
                    break;
                case 4:
                    buscarVendasPorDia(scanner);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 5);

        scanner.close();
    }

    private static void registrarVenda(Scanner scanner) {
        System.out.print("Digite o dia da venda (dd): ");
        String dia = scanner.next();
        System.out.print("Digite o mês da venda (MM): ");
        String mes = scanner.next();
        scanner.nextLine();

        System.out.print("Digite a quantidade da planta: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o preço unitário da planta: ");
        double precoUnitario = scanner.nextDouble();
        scanner.nextLine();

        double total = quantidade * precoUnitario;
        double desconto = (quantidade > 10) ? total * 0.05 : 0.00;
        double totalComDesconto = total - desconto;

        System.out.printf("Preço total: R$ %.2f (Desconto: R$ %.2f)%n", totalComDesconto, desconto);

        String chave = mes + "-" + dia;
        REGISTRO_VENDAS.putIfAbsent(chave, new ArrayList<>());
        REGISTRO_VENDAS.get(chave).add(new Venda(quantidade, precoUnitario, totalComDesconto, desconto));

        System.out.println("Venda registrada com sucesso!");
    }

    private static void calcularTroco(Scanner scanner) {
        System.out.print("Digite o valor recebido do cliente: ");
        double valorRecebido = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Digite o valor total da compra: ");
        double valorCompra = scanner.nextDouble();
        scanner.nextLine();

        if (valorRecebido < valorCompra) {
            System.out.println("O valor recebido é insuficiente!");
        } else {
            double troco = valorRecebido - valorCompra;
            System.out.printf("Troco a ser devolvido: R$ %.2f%n", troco);
        }
    }

    private static void mostrarHistoricoVendas() {
        if (REGISTRO_VENDAS.isEmpty()) {
            System.out.println("Nenhuma venda registrada ainda.");
        } else {
            System.out.println("\n--- Histórico de Vendas ---");
            for (Map.Entry<String, List<Venda>> entry : REGISTRO_VENDAS.entrySet()) {
                System.out.println("Data: " + entry.getKey());
                for (Venda venda : entry.getValue()) {
                    System.out.println(venda);
                }
            }
        }
    }

    private static void buscarVendasPorDia(Scanner scanner) {
        System.out.print("Digite o mês da venda (MM): ");
        String mes = scanner.next();
        System.out.print("Digite o dia da venda (dd): ");
        String dia = scanner.next();
        scanner.nextLine();

        String chave = mes + "-" + dia;

        if (REGISTRO_VENDAS.containsKey(chave)) {
            List<Venda> vendas = REGISTRO_VENDAS.get(chave);
            double totalDia = vendas.stream().mapToDouble(Venda::getValorTotal).sum();
            int quantidadeTotal = vendas.stream().mapToInt(Venda::getQuantidade).sum();

            System.out.printf("Total de vendas no dia %s: %d plantas | Valor total: R$ %.2f%n", chave, quantidadeTotal, totalDia);
        } else {
            System.out.println("Nenhuma venda encontrada para essa data.");
        }
    }
}

class Venda {
    private int quantidade;
    private double precoUnitario;
    private double valorTotal;
    private double desconto;

    public Venda(int quantidade, double precoUnitario, double valorTotal, double desconto) {
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    @Override
    public String toString() {
        return String.format("Qtd: %d | Preço Unitário: R$ %.2f | Total: R$ %.2f | Desconto: R$ %.2f",
                quantidade, precoUnitario, valorTotal, desconto);
    }
}
