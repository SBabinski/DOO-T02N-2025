package Aula2;;

public class Calculadora {

    public double calcularPrecoTotal(int quantidade, double preco) {
        return quantidade * preco;
    }

    public double calcularTroco(double valorRecebido, double total) {
        if (valorRecebido > total) {
            return valorRecebido - total;
        } else {
            System.out.println("Valor recebido menor que o total.");
            return 0;
        }
    }
    
}