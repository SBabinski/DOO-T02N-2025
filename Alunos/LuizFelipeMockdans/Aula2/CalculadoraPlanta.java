package CalculadoraPlantas;

import java.util.Scanner;

public class CalculadoraPlantas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Calculadora Dona Gabrielinha ===");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    calcularPrecoTotal(scanner);
                    break;
                case 2:
                    calcularTroco(scanner);
                    break;
                case 3:
                    System.out.println("Encerrando o programa... Até logo, Dona Gabrielinha!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 3);

        scanner.close();
    }

    public static void calcularPrecoTotal(Scanner scanner) {
        System.out.print("Informe a quantidade de plantas: ");
        int quantidade = scanner.nextInt();
        System.out.print("Informe o preço unitário da planta: ");
        double precoUnitario = scanner.nextDouble();
        double total = quantidade * precoUnitario;
        System.out.printf("Preço total: R$ %.2f\n", total);
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
}