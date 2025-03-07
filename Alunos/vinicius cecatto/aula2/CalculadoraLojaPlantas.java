package aula2;

import java.util.Scanner;

public class CalculadoraLojaPlantas {


    public static double calcularPrecoTotal(int quantidade, double precoUnitario) {
        return quantidade * precoUnitario;
    }


    public static double calcularTroco(double valorRecebido, double valorTotal) {
        return valorRecebido - valorTotal;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {

            System.out.println("===== Calculadora da Loja de Plantas =====");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:

                    System.out.print("Digite a quantidade de plantas: ");
                    int quantidade = scanner.nextInt();
                    System.out.print("Digite o preço unitário da planta: ");
                    double precoUnitario = scanner.nextDouble();
                    double precoTotal = calcularPrecoTotal(quantidade, precoUnitario);
                    System.out.printf("Preço total da compra: R$ %.2f%n", precoTotal);
                    break;

                case 2:

                    System.out.print("Digite o valor recebido do cliente: ");
                    double valorRecebido = scanner.nextDouble();
                    System.out.print("Digite o valor total da compra: ");
                    double valorTotal = scanner.nextDouble();
                    double troco = calcularTroco(valorRecebido, valorTotal);

                    if (troco >= 0) {
                        System.out.printf("Troco a ser devolvido: R$ %.2f%n", troco);
                    } else {
                        System.out.printf("Valor insuficiente! Faltam: R$ %.2f%n", Math.abs(troco));
                    }
                    break;

                case 3:
                    System.out.println("Saindo... Obrigada por utilizar a calculadora!");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

            System.out.println();
        } while (opcao != 3);

        scanner.close();
    }
}