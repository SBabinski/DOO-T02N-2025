import java.util.Scanner;

public class att2 {




	    public static double calcularPrecoTotal(int quantidade, double precoUnitario) {
	        return quantidade * precoUnitario;
	    }



	    public static double calcularTroco(double valorPago, double valorTotal) {
	        return valorPago - valorTotal;
	    }

	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        int opcao = 0;

	        while (opcao != 3) {


	            System.out.println("Escolha uma opção:");
	            System.out.println("[1] - Calcular Preço Total");
	            System.out.println("[2] - Calcular Troco");
	            System.out.println("[3] - Sair");
	            System.out.print("Digite sua opção: ");
	            opcao = scanner.nextInt();

	            if (opcao == 1) {

	                System.out.print("Digite a quantidade de plantas: ");
	                int quantidade = scanner.nextInt();
	                System.out.print("Digite o preço unitário da planta: R$ ");
	                double precoUnitario = scanner.nextDouble();
	                double precoTotal = calcularPrecoTotal(quantidade, precoUnitario);
	                System.out.println("Preço total da venda: R$ " + precoTotal);
	            } else if (opcao == 2) {

	                System.out.print("Digite o valor pago pelo cliente: R$ ");
	                double valorPago = scanner.nextDouble();
	                System.out.print("Digite o valor total da compra: R$ ");
	                double valorTotal = scanner.nextDouble();
	                if (valorPago >= valorTotal) {
	                    double troco = calcularTroco(valorPago, valorTotal);
	                    System.out.println("Troco a ser devolvido: R$ " + troco);
	                } else {
	                    System.out.println("Erro: O valor pago é insuficiente!");
	                }
	            } else if (opcao == 3) {
	                System.out.println("Saindo... Até logo!");
	            } else {
	                System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
	            }
	        }

	        scanner.close();
	    }
	}