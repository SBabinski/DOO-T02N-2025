package aula02;

import java.util.Scanner;

public class Calculadora {
	
	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		
		while(true) {
			System.out.println();
			System.out.println("SISTEMA LOJA DE PLANTAS");
			System.out.println("------------------------");
			System.out.println("[1] - Calcular Preço Total");
			System.out.println("[2] - Calcular Troco");
			System.out.println("[3] - Sair ");
			System.out.println();
			System.out.print("Escolha uma opção: ");
			int opcao = scan.nextInt();
			scan.nextLine();
			
			switch(opcao) {
			
			case 1: 
				precoTotal();
			break;
			
			case 2: 
				calcularTroco();
			break;
			
			case 3: 
				System.out.println("Encerrando o sistema...");
				scan.close();
				return;
				
			default: 
				System.out.println("Opção inválida!");
			}
		}
	}
	
	public static void precoTotal() {
		System.out.print("Digite o preco do item: ");
		double preco = scan.nextDouble();
		System.out.print("Digite a quantidade vendida do item: ");
		double quantidade = scan.nextDouble();
		double total = quantidade * preco;
		System.out.print("O preço total da venda: " +total);
		System.out.println();
	}
	
	public static void calcularTroco() {
		System.out.print("Digite o valor recebido pelo cliente: ");
		double valorCliente = scan.nextDouble();
		System.out.print("Digite o valor total da compra: ");
		double valorTotal = scan.nextDouble();
		
		
		if(valorCliente < valorTotal) {
			System.out.println("O valor recebido pelo cliente é inferior ao valor tota da compra");
		} else {
			double troco = valorCliente - valorTotal;
			System.out.println("O troco a ser devolvido é: " +troco);
			
		}
	}

}
