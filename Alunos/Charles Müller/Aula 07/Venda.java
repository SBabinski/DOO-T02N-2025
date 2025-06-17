public class Venda {
    private int quantidadePlantas;
    private double precoUnitario;
    private double valorTotal;
    private double desconto;

    public Venda(int quantidadePlantas, double precoUnitario, double valorTotal, double desconto) {
        this.quantidadePlantas = quantidadePlantas;
        this.precoUnitario = precoUnitario;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
    }

    // Getters
    public int getQuantidadePlantas() { return quantidadePlantas; }
    public double getPrecoUnitario() { return precoUnitario; }
    public double getValorTotal() { return valorTotal; }
    public double getDesconto() { return desconto; }

    @Override
    public String toString() {
        return "Quantidade: " + quantidadePlantas +
               " | Preço unitário: R$" + String.format("%.2f", precoUnitario) +
               " | Desconto: R$" + String.format("%.2f", desconto) +
               " | Valor total: R$" + String.format("%.2f", valorTotal);
    }
}