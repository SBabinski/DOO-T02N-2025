import java.util.Scanner;

public class CalcularTroco {
    public static void calculartroco() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Qual o valor recebido?");
        double valorrecebido = sc.nextDouble();

        System.out.println("Qual o valor total da compra?");
        double valorcompra = sc.nextDouble();

        double valortotalcompra = valorrecebido - valorcompra;

        System.out.println("O valor do troco é:" + valortotalcompra);
    }
}
