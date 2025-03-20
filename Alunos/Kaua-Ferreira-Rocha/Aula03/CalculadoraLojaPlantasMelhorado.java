import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalculadoraLojaPlantasMelhorado {

    static class Venda {
        int quantidade;
        double valorTotal;
        double descontoAplicado;

        Venda(int quantidade, double valorTotal, double descontoAplicado) {
            this.quantidade = quantidade;
            this.valorTotal = valorTotal;
            this.descontoAplicado = descontoAplicado;
        }

        @Override
        public String toString() {
            return "Quantidade: " + quantidade + ", Valor Total: R$ " + String.format("%.2f", valorTotal) +
                    ", Desconto Aplicado: R$ " + String.format("%.2f", descontoAplicado);
        }
    }

    private static final List<Venda> registroVendas = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            exibirMenu();
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    calcularPrecoTotal(scanner);
                    break;
                case 2:
                    calcularTroco(scanner);
                    break;
                case 3:
                    exibirRegistroVendas();
                    break;
                case 4:
                    System.out.println("Encerrando a calculadora. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 4);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n--- Calculadora Dona Gabrielinha ---");
        System.out.println("[1] - Calcular Preço Total");
        System.out.println("[2] - Calcular Troco");
        System.out.println("[3] - Exibir Registro de Vendas");
        System.out.println("[4] - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void calcularPrecoTotal(Scanner scanner) {
        System.out.print("Digite a quantidade de plantas: ");
        int quantidade = scanner.nextInt();
        System.out.print("Digite o preço unitário da planta: ");
        double precoUnitario = scanner.nextDouble();

        double valorTotal = quantidade * precoUnitario;
        double desconto = 0;

        if (quantidade > 10) {
            desconto = valorTotal * 0.05;
            valorTotal -= desconto;
            System.out.printf("Desconto de 5%% aplicado: R$ %.2f\n", desconto);
        }

        System.out.printf("Preço Total a Pagar: R$ %.2f\n", valorTotal);

        registroVendas.add(new Venda(quantidade, valorTotal, desconto));
    }

    private static void calcularTroco(Scanner scanner) {
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

    private static void exibirRegistroVendas() {
        if (registroVendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada até o momento.");
        } else {
            System.out.println("\n--- Registro de Vendas ---");
            for (Venda venda : registroVendas) {
                System.out.println(venda);
            }
        }
    }
}