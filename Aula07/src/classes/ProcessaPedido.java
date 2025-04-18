package classes;

import classes.Pedido;
import classes.Item;
import classes.Cliente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ProcessaPedido {
	
	public static Scanner scan = new Scanner (System.in);
	public static DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static List<Cliente> listaClientes = new ArrayList<>();
	public static List<Vendedor> listaVendedores = new ArrayList<>();
	public static List<Loja> listaLojas = new ArrayList<>();
	public static List<Item> listaItens = new ArrayList<>();
	public static List<Pedido> listaPedidos = new ArrayList<>();
	
	public static void processar() {
		
		boolean menu = true;
		
		Pedido pedido = new Pedido(0, null, null, null, null, null, null, 0, listaItens);
		Cliente cliente = new Cliente(null, 0, null);

		System.out.println("Qual o id do pedido?");
		pedido.setId(scan.nextInt());
		scan.nextLine();
		System.out.println("Qual a data de criação do pedido? (Formato dd/mm/aaaa)");
		String dataC = scan.nextLine();
		LocalDate data01 = LocalDate.parse(dataC, formato);
		pedido.setDataCriacao(data01);
		System.out.println("Qual a data de vencimento do pagamento? (Formato dd/mm/aaaa)");
		String dataV = scan.nextLine();
		LocalDate data02 = LocalDate.parse(dataV, formato);
		pedido.setDataVencimento(data02);
		
		
		while(menu) {
		
			System.out.println("Qual o cnpj da loja?");
			String cnpjComparacao = scan.nextLine();
			
			for (Loja loja : listaLojas) {
				
				if(loja.getCnpj().equalsIgnoreCase(cnpjComparacao)) {
					
					pedido.setLoja(loja);
					System.out.println("Loja encontrada");
					menu=false;
					break;
					
				}else {
					
					System.out.println("Loja n encontrada");
					break;
				}
				
			}
			
		}
		
		
		menu=true;
		
		while(menu) {
			
			System.out.println("Qual vendedor executou a venda?");
			String nomeVendedor = scan.nextLine();
			
			for (Vendedor vendedor : listaVendedores) {
			
				if(vendedor.getNome().equalsIgnoreCase(nomeVendedor)) {
				
					pedido.setVendedor(vendedor);
					System.out.println("Vendedor encontrado");
					menu=false;
					break;
				
				}else {
					
					System.out.println("Vendedor nao encontrado");
					break;
				}
			
			}
		}
		
		
		menu=true;
		
		while(menu) {
		
		
		System.out.println("O cliente ja é cadastrado na loja? \n1-Sim \n2-Não");
		int op = scan.nextInt();
		scan.nextLine();
		
		if(op == 1) {
			
			System.out.println("Digite o nome do cliente");
			String nomeComparacao = scan.nextLine();
			
			for (Cliente cliente01 : listaClientes) {
				
				if(cliente01.getNome().equalsIgnoreCase(nomeComparacao)) {
					
					pedido.setCliente(cliente01);
					System.out.println("Cliente encontrado");
					menu=false;
					break;
					
					
				} else {
					
					System.out.println("Cliente não encontrado! \nTente novamente");
					break;
				}
				
			}
			
		} else if(op == 2) {
			
			cliente.cadastrarCliente();
			listaClientes.add(cliente);
			pedido.setCliente(cliente);
			menu=false;
		}else {
			
			System.out.println("Opcao invalida");
		}
		}
		
		
		List<Item> catalogo = new ArrayList<>();
		Item item = new Item(1, "Tulipa", "Flor", 10);
		Item item01 = new Item(2, "Macieira", "Arvore", 50);
		Item item02 = new Item(3, "Cebolinha", "Hortalica", 2);
		catalogo.add(item);
		catalogo.add(item01);
		catalogo.add(item02);
		
		
		System.out.println("Deseja ver o catalogo? \n1-Sim \n2-Nao");
		int op = scan.nextInt();
		
		if(op == 1) {
			
			for (Item itemCatalogo : catalogo) {
				
				System.out.println("Nome: " + itemCatalogo.getNome() + " Id: " + itemCatalogo.getId() + "\nTipo: " + itemCatalogo.getTipo() + "\nValor: " + itemCatalogo.getValor());
				System.out.println(" ");
			} 
		}else {
			
			System.out.println("ok :( ");
		}
		
		menu=true;
		
		while(menu) {
			
			System.out.println("Deseja adcionar itens a lista de compras?");
			int op1 = scan.nextInt();
			
			
			
			if(op1 == 1) {
			
			
			System.out.println("Qual o Id do item?");
			int busca = scan.nextInt();
			
			for (Item item2 : catalogo) {
				
				if(busca == item2.getId()) {
					
					listaItens.add(item2);
					System.out.println("Item adcionado com sucesso");
					break;
					
				}else {
					
					System.out.println("Item não encontrado");
				}
				
			}
			
			}else if(op1 == 2){
				
				menu = false;
				
			}else {
				
				System.out.println("Opcao invalida!");
				
			}
	
			
			
		}
		
		pedido.setListaItens(listaItens);
		
	    listaPedidos.add(pedido);
		pedido.descricaoVenda(pedido);
		
		
		
	}
	
	public static void confirmarPagamento(double valor) {
		
		
		
		System.out.println("Valor pago?");
		double valorRecebido = scan.nextDouble();
		System.out.println("Qual o id do pedido?");
		int idProcurar = scan.nextInt();
		System.out.println("Qual a data que o pagamento esta sendo efetuado?");
		String dataP = scan.nextLine();
		LocalDate data03 = LocalDate.parse(dataP, formato);
		
		boolean menu01 = true;
		boolean menu02 = true;
		
		for (Pedido pedido : listaPedidos) {
			if(pedido.getId() == idProcurar) {
				
				if(data03.isAfter(pedido.getDataVencimento())) {
					
					System.out.println("Pagamento nao confirmado! \nReserva vencida.");
					
				}else {
					
					double troco = pedido.getValorTotal() - valorRecebido;
					
					if(troco < 0) {
						
						System.out.println("Valor insuficiente. \nFaltam R$" + troco * -1 + " para completar o valor da compra.");
					}else {
						
						System.out.println("O troco é: R$" + troco);
					}
					
				}
				
			}
				
			
		}
		
		

	}
	
	public static void teste() {
		
		
		 Endereco enderecoEduarda = new Endereco("Parana", "Cascavel", "Aquele", "Sem saida", "Apt 1000", 2);
		    Cliente cliente01 = new Cliente("Eduarda", 20, enderecoEduarda);
			Cliente cliente02= new Cliente("Marcus", 20, enderecoEduarda);
			listaClientes.add(cliente01);
			listaClientes.add(cliente02);
			
			
			Endereco enderecoFabio = new Endereco("Parana", "Cascavel", "centro", "sla", "Apt 206", 100);
			Vendedor vendedor01 = new Vendedor("Fabio", 25, 01, enderecoFabio, 2500, null);
			Vendedor vendedor02 = new Vendedor("Jane", 28, 02, enderecoFabio, 2500, null);
			Vendedor vendedor03 = new Vendedor("Laila", 20, 03, enderecoFabio, 2500, null);
			listaVendedores.add(vendedor01);
			listaVendedores.add(vendedor02);
			listaVendedores.add(vendedor03);
			
			Endereco enderecoLoja = new Endereco("Parana", "Cascavel", "Cataratas", "Sla", 999);
			Loja loja01 = new Loja("My Plant", "Dona Gabrielinha LTDA", "0001", enderecoLoja);
			listaLojas.add(loja01);
		
	}
	public static void main(String[] args) {
		
		teste();
		processar();
		//imprimirPedido();
		//Pedido.descricaoVenda(null);
		
	}

}
