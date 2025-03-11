import java.util.Scanner;

public class Calculadora {

    public static void calcularVendas() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Quantidade de itens: ");
        int quantidade = sc.nextInt();

        System.out.println("Preço: ");
        double preco = sc.nextDouble();

        double total = quantidade * preco;
        System.out.println("Valor total da compra: R$" + total + "\n");
    }

    public static void calcularTroco(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Valor total da compra: ");
        double total = sc.nextDouble();

        System.out.println("Valor recebido pelo cliente: ");
        double pago = sc.nextDouble();

        if (pago > total) {
            double troco = pago - total;
            System.out.println("Valor do troco: R$" + troco + "\n");
        }

        else{
            System.out.println("Valor insuficiente, faltam R$:" + (total-pago) + "\n");
        }

    }
}
