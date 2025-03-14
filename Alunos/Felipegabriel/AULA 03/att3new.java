import java.util.ArrayList;
import java.util.Scanner;

public class att3new {

	    public static double calcularPrecoTotal(int quantidade, double precoUnitario) {
	        return quantidade * precoUnitario;
	    }


	    public static double calcularPrecoComDesconto(int quantidade, double precoUnitario) {
	        double precoTotal = calcularPrecoTotal(quantidade, precoUnitario);
	        if (quantidade > 10) {
	            double desconto = precoTotal * 0.05; 
	            precoTotal -= desconto;
	            return precoTotal;
	        }
	        return precoTotal; 
	    }


	    public static void registrarVenda(int quantidade, double precoUnitario, ArrayList<venda> registroVendas) {
	        double valorTotal = calcularPrecoComDesconto(quantidade, precoUnitario);
	        double desconto = calcularPrecoTotal(quantidade, precoUnitario) - valorTotal;

	        venda venda = new venda(quantidade, precoUnitario, valorTotal, desconto);
	        registroVendas.add(venda);

	        System.out.println("Venda registrada com sucesso!");
	        System.out.printf("Valor total da venda: R$%.2f\n", valorTotal);
	        if (desconto > 0) {
	            System.out.printf("Desconto aplicado: R$%.2f\n", desconto);
	        }
	    }


	    public static void exibirRegistroVendas(ArrayList<venda> registroVendas) {
	        if (registroVendas.isEmpty()) {
	            System.out.println("Não há vendas registradas.");
	        } else {
	            System.out.println("Registro de Vendas:");
	            for (int i = 0; i < registroVendas.size(); i++) {
	                venda venda = registroVendas.get(i);
	                System.out.println("Venda " + (i + 1) + ":");
	                System.out.println(venda);
	                System.out.println("-------------------------------");
	            }
	        }
	    }


	    public static double calcularTroco(double valorPago, double valorTotal) {
	        return valorPago - valorTotal;
	    }

	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        ArrayList<venda> registroVendas = new ArrayList<>();
	        int opcao = 0;

	        while (opcao != 4) {

	            System.out.println("Escolha uma opção:");
	            System.out.println("[1] - Registrar Venda");
	            System.out.println("[2] - Exibir Registro de Vendas");
	            System.out.println("[3] - Calcular Troco");
	            System.out.println("[4] - Sair");
	            System.out.print("Digite sua opção: ");
	            opcao = scanner.nextInt();

	            if (opcao == 1) {

	                System.out.print("Digite a quantidade de plantas: ");
	                int quantidade = scanner.nextInt();
	                System.out.print("Digite o preço unitário da planta: R$ ");
	                double precoUnitario = scanner.nextDouble();

	                registrarVenda(quantidade, precoUnitario, registroVendas);

	            } else if (opcao == 2) {

	                exibirRegistroVendas(registroVendas);

	            } else if (opcao == 3) {

	                System.out.print("Digite o valor pago pelo cliente: R$ ");
	                double valorPago = scanner.nextDouble();
	                System.out.print("Digite o valor total da compra: R$ ");
	                double valorTotal = scanner.nextDouble();

	                if (valorPago >= valorTotal) {
	                    double troco = calcularTroco(valorPago, valorTotal);
	                    System.out.printf("Troco a ser devolvido: R$ %.2f\n", troco);
	                } else {
	                    System.out.println("Erro: O valor pago é insuficiente!");
	                }

	            } else if (opcao == 4) {

	                System.out.println("Saindo... Até logo!");

	            } else {
	                System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
	            }
	        }

	        scanner.close();
	    }
	}


	 class venda {
	    private int quantidade;
	    private double precoUnitario;
	    private double valorTotal;
	    private double desconto;

	    public venda(int quantidade, double precoUnitario, double valorTotal, double desconto) {
	        this.quantidade = quantidade;
	        this.precoUnitario = precoUnitario;
	        this.valorTotal = valorTotal;
	        this.desconto = desconto;
	    }

	    public int getQuantidade() {
	        return quantidade;
	    }

	    public double getPrecoUnitario() {
	        return precoUnitario;
	    }

	    public double getValorTotal() {
	        return valorTotal;
	    }

	    public double getDesconto() {
	        return desconto;
	    }


	    public String toString() {
	        return "Quantidade: " + quantidade + ", Preço Unitário: R$" + precoUnitario + ", Total: R$" + valorTotal + ", Desconto: R$" + desconto;
	    }
	}