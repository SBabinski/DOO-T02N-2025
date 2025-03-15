import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalculadoraV3 {

			public static Scanner scan = new Scanner(System.in);
				
			public static List<Venda> registroVendas = new ArrayList<>();
			
			public static void main(String[] args) {
				
				while(true) {
					System.out.println();
					System.out.println("SISTEMA LOJA DE PLANTAS");
					System.out.println("------------------------");
					System.out.println("[1] - Calcular Preço Total");
					System.out.println("[2] - Calcular Troco");
					System.out.println("[3] - Relatório de Vendas");
					System.out.println("[4] - Buscar Vendas por Data");
					System.out.println("[5] - Sair ");
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
						relatorioVendas();
					break;	
					
					case 4:
						buscarVendasData();
					break;	
					
					case 5: 
						System.out.println("Encerrando o sistema...");
						scan.close();
						return;
						
					default: 
						System.out.println("Opção inválida!");
					}
				}
			}
			
			public static void precoTotal() {
				System.out.print("Digite o preco do item: R$");
				double preco = scan.nextDouble();
				System.out.print("Digite a quantidade vendida do item: ");
				double quantidade = scan.nextDouble();
				double total = quantidade * preco;
				double desconto = 0;
				
				if(quantidade > 10) {
					desconto = total * 0.05;
					total -= desconto;
					System.out.print("Desconto aplicado! Valor com o desconto: R$"+total);
					System.out.println();
				} else {
					System.out.print("O preço total da venda: R$"+total);
					System.out.println();
				}
				
				registrarVendas(quantidade, total, desconto);
			
			}
			
			public static void calcularTroco() {
				System.out.print("Digite o valor recebido pelo cliente: R$");
				double valorCliente = scan.nextDouble();
				System.out.print("Digite o valor total da compra: ");
				double valorTotal = scan.nextDouble();
				
				
				if(valorCliente < valorTotal) {
					double falta = valorTotal- valorCliente;
					System.out.println("O valor recebido pelo cliente é inferior ao valor total da compra");
					System.out.println("Falta R$"+falta+ " para completar o pagamento");
				} else {
					double troco = valorCliente - valorTotal;
					System.out.println("O troco a ser devolvido é: R$" +troco);
				}
			}
			
			public static void registrarVendas(double quantidade, double total, double desconto) {
				System.out.print("Digite o mês da venda (1-12): ");
				int mes = scan.nextInt();
				System.out.print("Digite o dia da venda (1-31): ");
				int dia = scan.nextInt();
				
				Venda venda = new Venda(mes, dia, quantidade, total, desconto);
				registroVendas.add(venda);
			}
			
			public static void relatorioVendas() {
				if(registroVendas.isEmpty()) {
					System.out.println("Nenhuma venda cadastrada!");
				} else {
					System.out.println("=== Relatório de Vendas ===");
					for (Venda venda : registroVendas) {
						System.out.println(venda);
					}
				}
			}
			
			public static void buscarVendasData() {
				System.out.print("Digite o mês para buscar a venda (1-12): ");
				int mesBusca = scan.nextInt();
				System.out.print("Digite o dia para buscar a venda (1-31): ");
				int diaBusca = scan.nextInt();
				
				 int totalVendasDia = 0;
			     double totalValorDia = 0;
				
				for (Venda venda : registroVendas) {
					if(venda.getMes() == mesBusca && venda.getDia() == diaBusca) {
						totalVendasDia++;
						totalValorDia += venda.getTotal();	
					}
				}
				if (totalVendasDia > 0) {
					System.out.println("Total de vendas no " +mesBusca+ "/" +diaBusca+ ": " + totalVendasDia+ " vendas");
					System.out.println("Total de vendas no " +mesBusca+ "/" +diaBusca+ ": R$" +totalValorDia);
				}else {
					System.out.println("Nenhuma venda encontrada nesta data!");
				}
				
			}
		}

