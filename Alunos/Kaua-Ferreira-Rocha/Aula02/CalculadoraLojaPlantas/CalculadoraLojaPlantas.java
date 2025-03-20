import java.util.Scanner;

public class CalculadoraLojaPlantas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Calculadora Dona Gabrielinha ---");
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
                    System.out.println("Encerrando a calculadora. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 3);

        scanner.close();
    }

    public static void calcularPrecoTotal(Scanner scanner) {
        System.out.print("Digite a quantidade de plantas: ");
        int quantidade = scanner.nextInt();
        System.out.print("Digite o preço unitário da planta: ");
        double precoUnitario = scanner.nextDouble();
        double precoTotal = quantidade * precoUnitario;
        System.out.printf("Preço Total: R$ %.2f\n", precoTotal);
    }

    public static void calcularTroco(Scanner scanner) {
        System.out.print("Digite o valor recebido do cliente: ");
        double valorRecebido = scanner.nextDouble();
        System.out.print("Digite o valor total da compra: ");
        double valorCompra = scanner.nextDouble();
        double troco = valorRecebido - valorCompra;
        if (troco >= 0) {
            System.out.printf("Troco a ser dado: R$ %.2f\n", troco);
        } else {
            System.out.println("Valor recebido é insuficiente para cobrir a compra.");
        }
    }
}
