public class calculadora {

       import java.util.Scanner;

    public class CalculadoraPlantas {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                // Exibe o menu
                System.out.println("\n=== Calculadora da Loja de Plantas ===");
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
                        System.out.println("Saindo... Obrigado por usar a calculadora da Dona Gabrielinha!");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } while (opcao != 3);

            scanner.close();
        }

        // Método para calcular o preço total
        public static void calcularPrecoTotal(Scanner scanner) {
            System.out.print("Digite a quantidade de plantas: ");
            int quantidade = scanner.nextInt();

            System.out.print("Digite o preço unitário da planta: ");
            double precoUnitario = scanner.nextDouble();

            double total = quantidade * precoUnitario;
            System.out.printf("O preço total da compra é: R$ %.2f\n", total);
        }

        // Método para calcular o troco
        public static void calcularTroco(Scanner scanner) {
            System.out.print("Digite o valor recebido do cliente: ");
            double valorRecebido = scanner.nextDouble();

            System.out.print("Digite o valor total da compra: ");
            double valorCompra = scanner.nextDouble();

            if (valorRecebido < valorCompra) {
                System.out.println("Valor insuficiente! O cliente não deu dinheiro suficiente.");
            } else {
                double troco = valorRecebido - valorCompra;
                System.out.printf("O troco a ser dado ao cliente é: R$ %.2f\n", troco);
            }
        }
    }

}
