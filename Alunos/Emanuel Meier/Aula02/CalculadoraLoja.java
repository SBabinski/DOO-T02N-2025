import java.util.Locale;
import java.util.Scanner;

public class CalculadoraLoja {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US); 

        int opcao;
        do {
            System.out.println("\n--- Calculadora da Dona Gabrielinha ---");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    calcularPrecoTotal(scanner);
                    break;
                case 2:
                    calcularTroco(scanner);
                    break;
                case 3:
                    System.out.println("Saindo... Obrigado por usar a calculadora da Dona Gabrielinha!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 3);

        scanner.close();
    }

    public static void calcularPrecoTotal(Scanner scanner) {
        System.out.print("Digite a quantidade da planta: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Digite o preço unitário da planta: ");
        double precoUnitario = scanner.nextDouble();
        scanner.nextLine(); 

        double total = quantidade * precoUnitario;
        System.out.println("Preço total: R$ " + String.format("%.2f", total));
    }

    public static void calcularTroco(Scanner scanner) {
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
            System.out.println("Troco a ser devolvido: R$ " + String.format("%.2f", troco));
        }
    }
}
