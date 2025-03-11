//Projeto Calculadora

import java.util.Scanner;

public class Calculadora {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        
        do {
            
            System.out.println("FLORICULTURA");
            System.out.println("1 - Calcular Preço Total");
            System.out.println("2 - Calcular Troco");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            
            switch (opcao) {
                case 1:
                    
                    System.out.print("Digite a quantidade de plantas: ");
                    int quantidade = scanner.nextInt();

                    System.out.print("Digite o preço de cada planta: ");
                    double precoUnitario = scanner.nextDouble();

                    double precoTotal = quantidade * precoUnitario;
                    System.out.println("Preço total: R$ " + precoTotal);

                    break;

                case 2:
                   
                    System.out.print("Digite o valor recebido: R$ ");
                    double valorRecebido = scanner.nextDouble();

                    System.out.print("Digite o valor total da compra: R$ ");
                    double valorTotal = scanner.nextDouble();

                    double troco = valorRecebido - valorTotal;

                    if (troco < 0) {
                        System.out.println("Valor recebido é insuficiente");
                    } else {
                        System.out.println("Troco: " + troco);
                    }

                    break;

                case 3:
                    
                    System.out.println("Sair");
                    break;

                default:
                    System.out.println("Opção inválida");
            }

        } while (opcao != 3); 

        scanner.close();
    }
}