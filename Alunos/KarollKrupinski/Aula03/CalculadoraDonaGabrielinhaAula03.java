package calculadoraDonaGabrielinhaAula03;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Venda {
    int quantidade;
    double precoUnitario;
    double total;
    double desconto;

    public Venda(int quantidade, double precoUnitario, double total, double desconto) {
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.total = total;
        this.desconto = desconto;
    }
}

public class CalculadoraDonaGabrielinhaAula03 {
    private static List<Venda> registroVendas = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nMenu:");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Exibir Registro de Vendas");
            System.out.println("[4] - Sair");
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
                    exibirRegistroVendas();
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

    private static void calcularPrecoTotal(Scanner scanner) {
        System.out.print("Digite a quantidade de plantas: ");
        int quantidade = scanner.nextInt();
        System.out.print("Digite o preço unitário da planta: ");
        double precoUnitario = scanner.nextDouble();
        
        double total = quantidade * precoUnitario;
        double desconto = 0;
        
        if (quantidade > 10) {
            desconto = total * 0.05;
            total -= desconto;
        }
        
        registroVendas.add(new Venda(quantidade, precoUnitario, total, desconto));
        
        System.out.printf("Total a pagar: R$ %.2f (Desconto aplicado: R$ %.2f)\n", total, desconto);
    }

    private static void calcularTroco(Scanner scanner) {
        System.out.print("Digite o valor recebido do cliente: ");
        double valorRecebido = scanner.nextDouble();
        System.out.print("Digite o valor total da compra: ");
        double valorTotal = scanner.nextDouble();
        
        double troco = valorRecebido - valorTotal;
        
        if (troco < 0) {
            System.out.println("Valor insuficiente. O cliente precisa pagar mais R$ " + Math.abs(troco));
        } else {
            System.out.printf("Troco a ser dado: R$ %.2f\n", troco);
        }
    }

    private static void exibirRegistroVendas() {
        if (registroVendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada ainda.");
            return;
        }
        
        System.out.println("\nRegistro de Vendas:");
        for (Venda venda : registroVendas) {
            System.out.printf("Quantidade: %d | Preço Unitário: R$ %.2f | Total: R$ %.2f | Desconto: R$ %.2f\n",
                    venda.quantidade, venda.precoUnitario, venda.total, venda.desconto);
        }
    }
}
