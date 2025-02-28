package aula2;

import java.util.Scanner;

public class Calculadora {
	public static Scanner scan = new Scanner(System.in); 
	
	public static void main(String[] args) {
		int op = 0;
		while (op!=3) {
		System.out.println("\nDigite de acordo com o quer realizar");
		System.out.println("1 - Calcular Preço Total");
		System.out.println("2 - Calcular Troco");
		System.out.println("3- Sair");
		op = scan.nextInt();
		scan.nextLine();
		if (op==1) {
			calculadora();
		}
		
		else if (op==2) {
			troco();
		}
	}
	}
	public static void calculadora() {
		System.out.println("Quantas plantas vc vai querer?");
		float quantidade = scan.nextFloat();
		System.out.println("E qual o preço dela?");
		float valor = scan.nextFloat();
		float total = 0;
		total = (quantidade*valor);
		System.out.println("Cada uma custa "+valor+" reais, totalizando "+ total + " Reais.");
	}
	
	public static void troco() {
		System.out.println("Vc vai pagar com quanto para eu já calcular o troco?");
		float valorEntregue = scan.nextFloat();
		System.out.println("E quanto deu o total anterior?");
		float total = scan.nextFloat();
		if (valorEntregue < total) {
			System.out.println("Essa quantia de dinheiro é insuficiente!");
			
		}
		else {
		float troco = valorEntregue - total;
		System.out.println("Vou te dar "+troco+" Reais de troco!");
	}
	}
}
