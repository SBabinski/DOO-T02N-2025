public class VendaHistorico {

    private Integer quantidade;
    private Double valorPago;
    private Double desconto;

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public VendaHistorico(Integer quantidade, Double valorPago, Double desconto) {
        this.quantidade = quantidade;
        this.valorPago = valorPago;
        this.desconto = desconto;
    }

    @Override
    public String toString() {
        return "Venda: " +
                "Quantidade Vendida: " + quantidade +
                ", Valor Pago: " + valorPago +
                ", Desconto: " + desconto +
                '}';
    }
}
