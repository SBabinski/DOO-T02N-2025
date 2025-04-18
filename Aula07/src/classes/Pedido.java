package classes;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import classes.Item;

public class Pedido {
	
	public static Scanner scan = new Scanner(System.in);
	
	private int id;
	private LocalDate dataCriacao;
	private LocalDate dataPagamento;
	private LocalDate dataVencimento;
	private Cliente cliente;
	private Vendedor vendedor;
	private Loja loja;
	private double valorTotal;
	private List<Item>listaItens;
	
	

	public Pedido(int id, LocalDate dataCriacao, LocalDate dataPagamento, LocalDate dataVencimento, Cliente cliente, Vendedor vendedor,
			Loja loja, double valorTotal, List<Item> listaItens) {
		super();
		this.id = id;
		this.dataCriacao = dataCriacao;
		this.dataPagamento = dataPagamento;
		this.dataVencimento = dataVencimento;
		this.cliente = cliente;
		this.vendedor = vendedor;
		this.loja = loja;
		this.valorTotal = valorTotal;
		this.listaItens = listaItens;
	}
	
	


	
	public double getValorTotal() {
		return valorTotal;
	}





	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	
	public int getId() {
		return id;
	}





	public void setId(int id) {
		this.id = id;
	}





	public LocalDate getDataCriacao() {
		return dataCriacao;
	}





	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}





	public LocalDate getDataPagamento() {
		return dataPagamento;
	}





	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}





	public LocalDate getDataVencimento() {
		return dataVencimento;
	}





	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}





	public Cliente getCliente() {
		return cliente;
	}





	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}





	public Vendedor getVendedor() {
		return vendedor;
	}





	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}





	public Loja getLoja() {
		return loja;
	}





	public void setLoja(Loja loja) {
		this.loja = loja;
	}





	public List<Item> getListaItens() {
		return listaItens;
	}





	public void setListaItens(List<Item> listaItens) {
		this.listaItens = listaItens;
	}
	
	public void calcularValor() {
		
		double valor = 0;
		
		for (Item item : listaItens) {
			
			
			
			valor = valor + item.getValor();
			
		}
		
		valorTotal = valor;
		
	}
	
public static void descricaoVenda(Pedido pedido) {
		
	    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		System.out.println("Pedido: \nId: " + pedido.getId() + "\nData de Emissao:" + pedido.getDataCriacao().format(formato)
				+ " / Vencimento:" + pedido.getDataVencimento().format(formato) + "\nCliente: " + pedido.getCliente().getNome() + 
				" / Vendedor: " + pedido.getVendedor().getNome() + " / Loja: " + pedido.getLoja().getNome() + "\nItens Comprados:");
		
		System.out.println(" ");
		
		for (Item item2 : pedido.getListaItens()) {
			
				System.out.println("Nome: " + item2.getNome() + "Id:" + item2.getId() + "\nTipo: " + item2.getTipo() + "\nValor: " + item2.getValor());
				System.out.println(" ");
			}
		
		
		pedido.calcularValor();
		System.out.println("Valor total:" + pedido.getValorTotal());
	}
	

	public static void main(String[] args) {

		
		List<Item> listaItens = new ArrayList<>();
		
		Endereco enderecoFabio = new Endereco("Parana", "Cascavel", "centro", "sla", "Apt 206", 100);
		Vendedor vendedor01 = new Vendedor("Fabio", 25, 01, enderecoFabio, 2500, null);
		
		Endereco enderecoEduarda = new Endereco("Parana", "Cascavel", "Aquele", "Sem saida", "Apt 1000", 2);
		Cliente cliente01 = new Cliente("Eduarda", 20, enderecoEduarda);
		
		Endereco enderecoLoja = new Endereco("Parana", "Cascavel", "Cataratas", "Sla", 999);
		Loja loja01 = new Loja("My Plant", "Dona Gabrielinha LTDA", "0001", enderecoLoja);
		
		
		
		//pedidoTeste.calcularValor();
		
		//teste();
		
	}





	
}
