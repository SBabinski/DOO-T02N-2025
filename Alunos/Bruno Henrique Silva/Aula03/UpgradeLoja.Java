import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Double> plantasVendidas = new ArrayList<>();
    public static ArrayList<Double> descontosAplicados = new ArrayList<>();
    public static ArrayList<Double> valorVendas = new ArrayList<>();
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("\nBem-Vindo!");

        boolean sair = true;

        while (sair) {
            System.out.println("Selecione a opção que deseja executar:");
            System.out.println("""
                1 - Calcular preço total;
                2 - Calcular troco;
                3 - Registro;
                4 - Sair;
                """);

            int operacao = scan.nextInt();

            if (operacao == 1) {
                calcularTotal();
            }

            if (operacao == 2) {
                calcularTroco();
            }

            if (operacao == 3) {
                registro();
            }

            if (operacao == 4) {
                sair = false;
            }
        }
    }

    public static void calcularTotal() {
        System.out.println("Entre com a quantidade de plantas compradas: ");
        Double quantidade = scan.nextDouble();
        plantasVendidas.add(quantidade);

        System.out.println("Entre com o valor da planta: ");
        double valor = scan.nextDouble();
        double total;

        if (quantidade > 10) {
            total = quantidade * valor;
            double auxiliar = total;
            total = total * 0.95;
            auxiliar = auxiliar - total;

            valorVendas.add(total);
            descontosAplicados.add(auxiliar);

            System.out.printf("Valor total: R$ %.2f \n", total);
        }

        else {
            total = quantidade * valor;

            descontosAplicados.add(null);
            valorVendas.add(total);

            System.out.printf("Valor total: R$ %.2f \n", total);
        }
    }

    public static void calcularTroco() {
        System.out.println("Entre com o valor recebido pelo cliente: ");
        double totalCliente = scan.nextDouble();

        System.out.println("Entre com o valor da compra: ");
        double totalCompra = scan.nextDouble();

        double total = totalCliente - totalCompra;
        System.out.printf("Troco a ser dado: R$ %.2f \n", total);
    }

    public static void registro() {
        for (int i = 0; i < plantasVendidas.size(); i++) {
            System.out.println("Quantidade de plantas vendidas: " + plantasVendidas.get(i));

            if (descontosAplicados.get(i) != null) {
                System.out.printf("Descontos aplicados nas compras: R$ %.2f \n", descontosAplicados.get(i));
            }

            else {
                System.out.println("Não houveram descontos aplicados.");
            }

            System.out.printf("Valor das vendas: R$ %.2f \n", valorVendas.get(i));
        }
    }
}