package CalculadoraPlantas.Aula3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalculadoraPlantas {
    static List<String> registroVendas = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Calculadora Dona Gabrielinha ===");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Registrar Venda e Exibir Histórico");
            System.out.println("[3] - Calcular Troco");
            System.out.println("[4] - Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    calcularPrecoTotal(scanner);
                    break;
                case 2:
                    exibirRegistroVendas();
                    break;
                case 3:
                    calcularTroco(scanner);
                    break;
                case 4:
                    System.out.println("Encerrando o programa... Até logo, Dona Gabrielinha!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 4);

        scanner.close();
    }

    public static void calcularPrecoTotal(Scanner scanner) {
        System.out.print("Informe a quantidade de plantas: ");
        int quantidade = scanner.nextInt();
        System.out.print("Informe o preço unitário da planta: ");
        double precoUnitario = scanner.nextDouble();
        double total = quantidade * precoUnitario;

        if (quantidade > 10) {
            double desconto = total * 0.05;
            total -= desconto;
            System.out.printf("Desconto de 5%% aplicado! Valor com desconto: R$ %.2f\n", total);
        } else {
            System.out.printf("Preço total: R$ %.2f\n", total);
        }

        registrarVenda(quantidade, total);
    }

    public static void calcularTroco(Scanner scanner) {
        System.out.print("Informe o valor recebido pelo cliente: ");
        double valorRecebido = scanner.nextDouble();
        System.out.print("Informe o valor total da compra: ");
        double valorTotal = scanner.nextDouble();
        
        if (valorRecebido >= valorTotal) {
            double troco = valorRecebido - valorTotal;
            System.out.printf("Troco: R$ %.2f\n", troco);
        } else {
            System.out.println("Valor insuficiente! O cliente precisa fornecer mais dinheiro.");
        }
    }

    public static void registrarVenda(int quantidade, double valor) {
        String registro = "Quantidade: " + quantidade + " | Valor: R$ " + String.format("%.2f", valor);
        registroVendas.add(registro);
        System.out.println("Venda registrada com sucesso!");
    }

    public static void exibirRegistroVendas() {
        if (registroVendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada até o momento.");
        } else {
            System.out.println("\n=== Registro de Vendas ===");
            for (String registro : registroVendas) {
                System.out.println(registro);
            }
        }
    }
}
