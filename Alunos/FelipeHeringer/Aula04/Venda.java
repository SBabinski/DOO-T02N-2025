package Alunos.FelipeHeringer.Aula04;

public class Venda {
    protected Integer numVenda;
    protected Integer qtdPlantas;
    protected Double valorVenda;
    protected Double descontosAplicados;

    public Venda(Integer numVenda, Integer qtdPlantas, Double valorVenda, Double descontosAplicados) {
        this.numVenda = numVenda;
        this.qtdPlantas = qtdPlantas;
        this.valorVenda = valorVenda;
        this.descontosAplicados = descontosAplicados;
    }

    public Venda() {

    }
    
    @Override
    public String toString() {
        return "Venda No: " + numVenda +
                " Quantidade Plantas: " + qtdPlantas + " Valor da Venda: " + valorVenda
                + " Descontos Aplicados: " + descontosAplicados;
    }

    public Integer getQtdPlantas() {
        return qtdPlantas;
    }

    public void setQtdPlantas(Integer qtdPlantas) {
        this.qtdPlantas = qtdPlantas;
    }

    public Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Double getDescontosAplicados() {
        return descontosAplicados;
    }

    public void setDescontosAplicados(Double descontosAplicados) {
        this.descontosAplicados = descontosAplicados;
    }
}
