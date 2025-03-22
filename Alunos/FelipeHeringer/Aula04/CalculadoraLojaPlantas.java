package Alunos.FelipeHeringer.Aula04;

import java.security.InvalidParameterException;

public class CalculadoraLojaPlantas {

    protected Venda venda;

    public CalculadoraLojaPlantas() {
        this.venda = new Venda();
    }

    //Metodos da Classe Calculadora
    public Double calculoPrecoTotal(Integer qtdPlanta,Double preco){
        this.venda.setQtdPlantas(qtdPlanta);
        // Aplicar Desconto (5%) caso o Quantidade seja 10 ou superior
        if (qtdPlanta >= 10) {
            Double precoCompra = qtdPlanta * preco;
            this.venda.setValorVenda(precoCompra);
            Double descontoAplicado = (precoCompra * 5 / 100);
            this.venda.setDescontosAplicados(descontoAplicado);

            return precoCompra - descontoAplicado;
        }else{
            Double precoCompra = qtdPlanta * preco;
            this.venda.setValorVenda(precoCompra);
            this.venda.setDescontosAplicados(0.0);

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

}