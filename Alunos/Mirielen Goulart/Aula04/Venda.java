public class Venda {
	private int mes;
	private int dia;
	private double quantidade;
	private double total;
	private double desconto;
	
	public Venda(int mes, int dia, double quantidade, double total, double desconto) {
		super();
		this.mes = mes;
		this.dia = dia;
		this.quantidade = quantidade;
		this.total = total;
		this.desconto = desconto;
	}

	public int getMes() {
		return mes;
	}


	public int getDia() {
		return dia;
	}

	public double getTotal() {
		return total;
	}

	@Override
	public String toString() {
		return "Vendas (Data:" + mes + "/" + dia + ", Quantidade:" + quantidade + ", Total:" + total + ", Desconto:"
				+ desconto + ")";
	}	
}
