package Aula02;
import java.util.Scanner;

public class Calculos {

    private int quantidadeProduto;
    private double precoUnitario;
    private double precoTotal;
    private double valorRecebido;
    private double troco;

    Scanner scanner = new Scanner(System.in);


    public void calcularPrecoTotal() {

        System.out.println("Informe o valor do preço unitário:");
        double precoUnitario = scanner.nextDouble();

        System.out.println("Informe a quantidade do produto:");
        int quantidadeProduto = scanner.nextInt();

        precoTotal = precoUnitario * quantidadeProduto;

        System.out.printf("O preço total é: R$ %.2f%n", precoTotal);

    }



    public void calcularTroco(){

        System.out.println("Informe o valor pago pelo cliente: ");
        double valorRecebido = scanner.nextDouble();

        if (valorRecebido < precoTotal) {
            System.out.println("Erro: Valor insuficiente para a compra!");
        } else {
            troco = valorRecebido - precoTotal;
            System.out.printf("Troco do cliente: R$ %.2f%n", troco);
        }
    }









}
