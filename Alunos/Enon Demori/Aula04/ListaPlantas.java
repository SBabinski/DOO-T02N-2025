package aula2;

public class ListaPlantas {
	
	public int quantidade;
	public double total;
	public double valorTotalDescontos;
	
	public ListaPlantas() {
		
	}

	public ListaPlantas(int quantidade, double total, double valorTotalDescontos) {
		this.quantidade = quantidade;
		this.total = total;
		this.valorTotalDescontos = valorTotalDescontos;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getValorTotalDescontos() {
		return valorTotalDescontos;
	}

	public void setValorTotalDescontos(double valorTotalDescontos) {
		this.valorTotalDescontos = valorTotalDescontos;
	}

	@Override
	public String toString() {
		return "A quantidade total de plantas é:" + quantidade + "\nTotalizando o valor em " + total + " Reais " + "\nO valor total que foi em descontos é: "
				+ valorTotalDescontos;
	}

}