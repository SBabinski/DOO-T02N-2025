package Aula2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Atividade {
    
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        Calculadora calculadora = new Calculadora();

        boolean continuar = true;
        double total = 0;
        while (continuar == true) {
            try {
                menu();
                int opcao = scan.nextInt();
                switch (opcao) {
                    case 1: 
                        System.out.println("Digite a quantidade da flor:");
                        int quantidade = scan.nextInt();
                        System.out.println("Digite o preco da flor:");
                        double preco = scan.nextDouble();
                        System.out.println("O preco total e: " + calculadora.calcularPrecoTotal(quantidade, preco));
                        total = calculadora.calcularPrecoTotal(quantidade, preco);
                        break;
                    case 2:
                        System.out.println("Digite o valor recebido: ");
                        double valorRecebido = scan.nextDouble();
                        System.out.println("O troco é: " + calculadora.calcularTroco(valorRecebido, total));
                        break;
                    case 3:
                        continuar = false;
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opcao invalida.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida.");
	            scan.nextLine();
            }
        }

        scan.close();
    }

    static void menu() {
        System.out.println("Digite uma das opcoes:");
        System.out.println("[1] Calcular Preco Total");
        System.out.println("[2] Calcular Troco");
        System.out.println("[3] Sair");
        return;
    }

}
