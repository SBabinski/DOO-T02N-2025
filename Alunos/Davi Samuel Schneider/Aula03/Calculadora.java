import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculadora {
    static Scanner sc = new Scanner(System.in);
    static List<Produto> registros = new ArrayList<Produto>();

    public static void calcularPreco() {
        System.out.println("Qual valor do produto: ");
        double preco = sc.nextDouble();
        System.out.println("Quantidade do produto: ");
        int quantidade = sc.nextInt();
        double total = verificarDesconto(quantidade, preco);
        System.out.println("Total da compra: R$ " + total);

        Produto produto = new Produto(quantidade, preco, total);
        registros.add(produto);
    }

    public static void calcularTroco() {
        System.out.println("Valor da compra: ");
        double valorCompra = sc.nextDouble();
        System.out.println("Valor pago: ");
        double valorPago = sc.nextDouble();

        if (valorCompra > valorPago) {
            System.out.println("Faltam: R$ " + (valorCompra - valorPago));
        } else {
            System.out.println("Troco: R$ " + (valorPago - valorCompra));
        }
    }

    public static double verificarDesconto(int quantidade, double preco) {
        if (quantidade > 10) {
            return (preco * quantidade) * 0.95;
        } else {
            return preco * quantidade;
        }
    }

    public static void imprimirRegistros() {
        for (Produto produto : registros) {
            System.out.println(produto);
        }
    }

    public static void fecharScanner() {
        sc.close();
    }
}

