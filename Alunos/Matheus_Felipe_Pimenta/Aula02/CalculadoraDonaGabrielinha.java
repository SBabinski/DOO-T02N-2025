package calculadoraDonaGabrielinha;

import java.util.Scanner;

public class CalculadoraDonaGabrielinha {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Calculadora da Dona Gabrielinha ===");
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
                    System.out.println("Saindo... Obrigado por usar a calculadora!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 3);

        scanner.close();
    }

    public static void calcularPrecoTotal(Scanner scanner) {
        System.out.print("\nDigite a quantidade de plantas vendidas: ");
        int quantidade = scanner.nextInt();

        System.out.print("Digite o preço unitário da planta: ");
        double precoUnitario = scanner.nextDouble();

        double precoTotal = quantidade * precoUnitario;
        System.out.println("O preço total da venda é: R$ " + precoTotal);
    }

    public static void calcularTroco(Scanner scanner) {
        System.out.print("\nDigite o valor recebido pelo cliente: ");
        double valorRecebido = scanner.nextDouble();

        System.out.print("Digite o valor total da compra: ");
        double valorTotal = scanner.nextDouble();

        double troco = valorRecebido - valorTotal;
        if (troco >= 0) {
            System.out.println("O troco a ser dado é: R$ " + troco);
        } else {
            System.out.println("Valor insuficiente. Faltam: R$ " + Math.abs(troco));
        }
    }
}
