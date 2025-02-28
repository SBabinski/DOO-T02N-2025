package pacote;
import java.util.Scanner;

public class Projeto {
	public static Scanner scan = new Scanner(System.in);
	public static void main (String []Args) {
		while (true) {
            System.out.println("=== MENU ===");
            System.out.println("[1] - Calcular Preço Total");
            System.out.println("[2] - Calcular Troco");
            System.out.println("[3] - Sair");
            System.out.println("Escolha uma opção: ");
            
            int opcao = scan.nextInt();
            
            switch (opcao) {
                case 1:
                    calculoPrecoTotal();
                    break;
                case 2:
                    calculoDeTroco();
                    break;
                case 3:
                    System.out.println("Saindo...");
                    scan.close();
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
		
	}
	public static void calculoPrecoTotal () {
		System.out.println("Digite quantas plantas foram compradas");
		int quantidade = scan.nextInt();
		System.out.println("Digite o valor do tipo de planta comprada");
		float valor = scan.nextFloat();
		float total = quantidade*valor;
		System.out.println("O calculo do preço total: "+ String.format("%.2f", total));
	}
	
	public static void calculoDeTroco () {
		System.out.println("Digite o valor entregue pelo cliente");
		float valorCliente = scan.nextFloat();
		System.out.println("Digite o valor total da compra");
		float valor = scan.nextFloat();
		if (valorCliente < valor) {
			System.out.println("ERRO: O valor entregue pelo cliente não é o suficiente para pagar as flores.");
		}
		else {
			float troco = valorCliente - valor;
			System.out.println("O cliente precisa receber R$ " + String.format("%.2f", troco) + "de troco");
		}
	
		
	}
}
