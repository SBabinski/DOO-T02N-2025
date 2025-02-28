package calculadora;
import java.util.Scanner;

public class LojaPlantas {
	public static Scanner scan = new Scanner (System.in);
	
	public static void main(String[]args) {
		calculadora();
	}
		public static void calculadora() {
			
		while(true) {
			System.out.println("1 - Calcular preço total");
			System.out.println("2 - Calcular troco");
			System.out.println("3 - Sair ");
			int opcao = scan.nextInt();
			
			
		
		
		
		if(opcao == 1) {
			System.out.println("Quantidade de plantas:");
			int quant = scan.nextInt();
			
			System.out.println("Preço da unidade:\n ");
			double preco = scan.nextDouble();
			double total = quant * preco; 
			System.out.printf(" O preço total é : %.2f\n",total);
			
		}
		
		else if(opcao == 2) {
			System.out.println("Informe o valor entregue pelo cliente:");
			double valorEntregue = scan.nextDouble();
			System.out.println("Informe o valor total da compra:");
			double valorTotal = scan.nextDouble();
		
			
			if (valorEntregue < valorTotal) {
                System.out.println("Valor insuficiente!");
            } else {
                System.out.printf("Valor do Troco: R$ %.2f\n", valorEntregue - valorTotal);
            }
		}
		
		else if(opcao == 3) {
			System.out.println("Saindo...");
			break;
			
			
		}
		else {
			System.out.println("Opcao inválida");
		}
	}
}
}