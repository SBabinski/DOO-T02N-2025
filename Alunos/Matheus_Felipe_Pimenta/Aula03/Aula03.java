package aula03;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Aula03 {
    
    private static List<String> registroVendas = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Calculadora da Dona Gabrielinha ===");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Ver Registro de Vendas");
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
                    mostrarRegistroVendas();
                    break;
                case 4:
                    System.out.println("Saindo... Obrigado por usar a calculadora!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 4);

        scanner.close();
    }

    public static void calcularPrecoTotal(Scanner scanner) {
        System.out.print("\nDigite a quantidade de plantas vendidas: ");
        int quantidade = scanner.nextInt();
        
        System.out.print("Digite o preço unitário da planta: ");
        double precoUnitario = scanner.nextDouble();

        double precoTotal = quantidade * precoUnitario;
        double desconto = 0;

        if (quantidade > 10) {
            desconto = precoTotal * 0.05;
            precoTotal -= desconto;
        }

        System.out.println("O preço total da venda é: R$ " + precoTotal);
        if (desconto > 0) {
            System.out.println("Desconto aplicado: R$ " + desconto);
        }

        String registro = "Qtd: " + quantidade + ", Valor: R$ " + precoTotal + ", Desconto: R$ " + desconto;
        registroVendas.add(registro);
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

    public static void mostrarRegistroVendas() {
        System.out.println("\n=== Registro de Vendas ===");
        if (registroVendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada até o momento.");
        } else {
            for (String venda : registroVendas) {
                System.out.println(venda);
            }
        }
    }
}
