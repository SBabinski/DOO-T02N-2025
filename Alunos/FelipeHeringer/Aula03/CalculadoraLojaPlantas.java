import java.security.InvalidParameterException;

public class CalculadoraLojaPlantas {
    private Integer qtd;
    private Double valorCompra;
    private Double valorPago;
    private Double descontoAplicado;

    public CalculadoraLojaPlantas(){

    }

    //Metodos da Classe Calculadora
    public Double calculoPrecoTotal(Integer qtdPlanta,Double preco){
        setQtd(qtdPlanta);
        // Aplicar Desconto (5%) caso o Quantidade seja 10 ou superior
        if (qtdPlanta >= 10) {
            Double precoCompra = qtdPlanta * preco;
            Double descontoAplicado = (precoCompra * 5 / 100);
            setDescontoAplicado(descontoAplicado);
            setValorCompra(precoCompra);

            return precoCompra - descontoAplicado;
        }else{
            Double precoCompra = qtdPlanta * preco;
            setValorCompra(precoCompra);
            setDescontoAplicado(0.0);

            return precoCompra;
        }

    }
    public Double calculoTroco(Double valorPago,Double valorCompra){
        if (valorPago < valorCompra){
             throw new InvalidParameterException("Valor Pago Inferior ao Valor da Compra");
        }
         Double troco = valorPago - valorCompra;

         return troco;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(Double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public Double getDescontoAplicado() {
        return descontoAplicado;
    }

    public void setDescontoAplicado(Double descontoAplicado) {
        this.descontoAplicado = descontoAplicado;
    }
}