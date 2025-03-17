import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculadora {
    static List<Venda> historicoVendas = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);

    public static void Calculadora() {
        System.out.println("Digite o valor da planta: ");
        int valorPlanta = scan.nextInt();

        System.out.println("Digite a quantidade que vai comprar: ");
        int quantPlanta = scan.nextInt();

        scan.nextLine();
        System.out.println("Digite a data da venda (DD/MM/AAAA): ");
        String data = scan.nextLine();

        int valorTotal = valorPlanta * quantPlanta;
        double desconto = (quantPlanta > 10) ? valorTotal * 0.05 : 0;
        double valorComDesconto = valorTotal - desconto;

        historicoVendas.add(new Venda(quantPlanta, valorPlanta, valorTotal, desconto, valorComDesconto, data));
        System.out.println("Seu total foi: R$ " + valorComDesconto);
    }

    public static void CalcTroco() {
        System.out.println("Digite o valor do total: ");
        int valorTotal = scan.nextInt();

        System.out.println("Digite o valor pago: ");
        int valorPago = scan.nextInt();

        int valorTotalTroco = valorPago - valorTotal;
        System.out.println("Seu troco foi: R$ " + valorTotalTroco);
    }

    public static void exibirHistoricoVendas() {
        System.out.println("\nHistórico de Vendas:");
        for (Venda venda : historicoVendas) {
            System.out.println(venda);
        }
    }

    public static void buscarVendasPorData() {
        scan.nextLine();
        System.out.println("Digite a data desejada (DD/MM/AAAA): ");
        String dataBusca = scan.nextLine();
        int totalVendas = 0;

        for (Venda venda : historicoVendas) {
            if (venda.data.equals(dataBusca)) {
                totalVendas += venda.quantPlanta;
            }
        }
        System.out.println("Total de vendas em " + dataBusca + ": " + totalVendas);
    }
}
