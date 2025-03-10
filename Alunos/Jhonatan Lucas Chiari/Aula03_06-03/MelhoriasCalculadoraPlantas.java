import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MelhoriasCalculadoraPlantas {
    static class Venda {
        int quantidade;
        double valorTotal;
        double desconto;

        Venda(int quantidade, double valorTotal, double desconto) {
            this.quantidade = quantidade;
            this.valorTotal = valorTotal;
            this.desconto = desconto;
        }
    }

    private static final List<Venda> registroVendas = new ArrayList<>();

    public static double calcularPrecoTotal(int quantidade, double precoUnitario) {
        double precoTotal = quantidade * precoUnitario;
        double desconto = 0;

        if (quantidade > 10) {
            desconto = precoTotal * 0.05;
            precoTotal -= desconto;
        }

        registroVendas.add(new Venda(quantidade, precoTotal, desconto));
        return precoTotal;
    }

    public static double calcularTroco(double valorRecebido, double valorTotal) {
        return valorRecebido - valorTotal;
    }

    public static void exibirRegistroVendas() {
        System.out.println("\nRegistro de Vendas:");
        for (Venda venda : registroVendas) {
            System.out.printf("Quantidade Plantas: %d | Valor Total: R$ %.2f | Desconto: R$ %.2f\n",
                    venda.quantidade, venda.valorTotal, venda.desconto);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Calcular Preço Total");
            System.out.println("2 - Calcular Troco");
            System.out.println("3 - Exibir Registro de Vendas");
            System.out.println("4 - Sair");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Digite a quantidade de plantas: ");
                    int quantidade = scanner.nextInt();
                    System.out.println("Digite o valor de cada planta: ");
                    double precoUnitario = scanner.nextDouble();
                    double precoTotal = calcularPrecoTotal(quantidade, precoUnitario);
                    System.out.printf("O preço total com desconto é: R$ %.2f\n", precoTotal);
                    break;

                case 2:
                    System.out.println("Digite o valor recebido: ");
                    double valorRecebido = scanner.nextDouble();
                    System.out.println("Digite o valor total da compra: ");
                    double valorTotal = scanner.nextDouble();
                    double troco = calcularTroco(valorRecebido, valorTotal);
                    System.out.printf("O valor do troco é: R$ %.2f\n", troco);
                    break;

                case 3:
                    exibirRegistroVendas();
                    break;

                case 4:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida, tente novamente.");
                    break;
            }
        } while (opcao != 4);

        scanner.close();
    }
}
