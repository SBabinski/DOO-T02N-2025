
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Pedido {
	
	private int id;
	private LocalDate dataCriacao;
	private LocalDate dataPagamento;
	private LocalDate dataVencimentoReserva;
	private Cliente cliente;
	private Vendedor vendedor;
	private Loja loja;
	private List<Item> itens;
	
	public Pedido(int id, LocalDate dataCriacao, LocalDate dataPagamento, LocalDate dataVencimentoReserva, Cliente cliente,
			Vendedor vendedor, Loja lojaVendedor, List<Item> itens) {
		super();
		this.id = id;
		this.dataCriacao = dataCriacao;
		this.dataPagamento = dataPagamento;
		this.dataVencimentoReserva = dataVencimentoReserva;
		this.cliente = cliente;
		this.vendedor = vendedor;
		this.loja = lojaVendedor;
		this.itens = itens;
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



	public LocalDate getDataVencimentoReserva() {
		return dataVencimentoReserva;
	}



	public void setDataVencimentoReserva(LocalDate dataVencimentoReserva) {
		this.dataVencimentoReserva = dataVencimentoReserva;
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



	public List<Item> getItens() {
		return itens;
	}



	public void setItens(List<Item> itens) {
		this.itens = itens;
	}



	public double calcularValorTotal() {
	        double valorTotal = 0.0;
	        for (Item item : itens) {
	            valorTotal += item.getValor(); 
	        }
	        return valorTotal;
	    }
	  
	  public String gerarDescricaoVenda() {
	        return "Pedido criado em " + dataCriacao + " com valor total de R$" + calcularValorTotal();
	    }
}
