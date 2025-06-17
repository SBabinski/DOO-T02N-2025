package Aula4;;

public class Calculadora {

    public double calcularPrecoTotal(int quantidade, double preco) {
        if (quantidade >= 10) {
            return (quantidade * preco) * 0.95;
        } else {
            return quantidade * preco;
        }
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