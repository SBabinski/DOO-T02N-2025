import java.util.ArrayList;
import java.util.Scanner;

public class calculadoraPlantas {
    

    static class Venda {
        int quantidade;
        double precoUnitario;
        double valorTotal;
        double desconto;
        double valorFinal;

        Venda(int quantidade, double precoUnitario, double valorTotal, double desconto, double valorFinal) {
            this.quantidade = quantidade;
            this.precoUnitario = precoUnitario;
            this.valorTotal = valorTotal;
            this.desconto = desconto;
            this.valorFinal = valorFinal;
        }
    }


    static ArrayList<Venda> registroVendas = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("Escolha uma opção:");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Ver Registro de Vendas");
            System.out.println("[4] - Sair");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    calcularPrecoTotal(scanner);
                    break;
                case 2:
                    calcularTroco(scanner);
                    break;
                case 3:
                    visualizarRegistroVendas();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 4);

        scanner.close();
    }

    public static void calcularPrecoTotal(Scanner scanner) {
        System.out.print("Quantidade de plantas: ");
        int quantidade = scanner.nextInt();

        System.out.print("Preço unitário da planta: ");
        double precoUnitario = scanner.nextDouble();

        double precoTotal = quantidade * precoUnitario;
        double desconto = 0.0;
        double valorFinal = precoTotal;

        if (quantidade > 10) {
            desconto = precoTotal * 0.05;  
            valorFinal = precoTotal - desconto;
        }

        registrarVenda(quantidade, precoUnitario, precoTotal, desconto, valorFinal);
        
        System.out.printf("Preço total: R$ %.2f%n", precoTotal);
        if (desconto > 0) {
            System.out.printf("Desconto aplicado: R$ %.2f%n", desconto);
        }
        System.out.printf("Valor a pagar: R$ %.2f%n", valorFinal);
    }

    public static void registrarVenda(int quantidade, double precoUnitario, double precoTotal, double desconto, double valorFinal) {

        Venda venda = new Venda(quantidade, precoUnitario, precoTotal, desconto, valorFinal);
        registroVendas.add(venda);
    }

    public static void visualizarRegistroVendas() {
        if (registroVendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
        } else {
            System.out.println("Registro de Vendas:");
            for (Venda venda : registroVendas) {
                System.out.println("Quantidade: " + venda.quantidade);
                System.out.printf("Preço unitário: R$ %.2f%n", venda.precoUnitario);
                System.out.printf("Preço total: R$ %.2f%n", venda.valorTotal);
                if (venda.desconto > 0) {
                    System.out.printf("Desconto aplicado: R$ %.2f%n", venda.desconto);
                }
                System.out.printf("Valor final: R$ %.2f%n", venda.valorFinal);
                System.out.println("-------------");
            }
        }
    }

    public static void calcularTroco(Scanner scanner) {
        System.out.print("Valor recebido: ");
        double valorRecebido = scanner.nextDouble();

        System.out.print("Valor total da compra: ");
        double valorTotal = scanner.nextDouble();

        double troco = valorRecebido - valorTotal;
        System.out.printf("Troco: R$ %.2f%n", troco);
    }
}
