import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Double> plantasVendidas = new ArrayList<>();
    public static ArrayList<Double> descontosAplicados = new ArrayList<>();
    public static ArrayList<Double> valorVendas = new ArrayList<>();
    public static Scanner scan = new Scanner(System.in);
    public static int[][] matrizVendas = new int[12][30];

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
                4 - Salvar quantidade vendas;
                5 - Buscar quantidade vendas;
                6 - Sair;
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
                salvarVendas();
            }

            if (operacao == 5) {
                buscarVendas();
            }

            if (operacao == 6) {
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

    public static void salvarVendas() {
        System.out.print("Insira o dia do mês: ");
        int dia = scan.nextInt();

        System.out.print("Insira o mês: ");
        int mes = scan.nextInt();

        System.out.print("Entre com a quantidade de vendas totais do dia: ");
        int totalDia = scan.nextInt();

        matrizVendas[mes][dia] = totalDia;
        System.out.println("Concluído!\n");
    }

    public static void buscarVendas() {
        System.out.print("Insira o dia do mês: ");
        int dia = scan.nextInt();

        System.out.print("Insira o mês: ");
        int mes = scan.nextInt();

        System.out.println("Quantidade de vendas totais: \n" + matrizVendas[mes][dia]);
    }
}