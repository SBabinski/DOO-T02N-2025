import java.util.Scanner;

public class Precototal {


    public static void calcularPrecototal() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Quantas plantas foram vendidas?");
        int vendas = sc.nextInt();


        System.out.println("Qual o valor de cada planta?");
        double valor = sc.nextDouble();

        double valortotal = valor * vendas;

        System.out.println("O valor do produto é:" + valortotal);
    }
}
