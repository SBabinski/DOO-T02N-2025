public class Calculadora {

    //Metodos da Classe Calculadora
    public Double calculoPrecoTotal(Integer qtdPlanta,Double preco){
        Double precoTotal = qtdPlanta * preco;

        return precoTotal;
    }
    public Double calculoTroco(Double valorPago,Double valorCompra){

        Double troco = valorPago-valorCompra;
        return troco;
    }

}