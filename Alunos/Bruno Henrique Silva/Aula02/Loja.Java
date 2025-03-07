import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("\nBem-Vindo!");

        boolean sair = true;

        while (sair) {
            System.out.println("Selecione a opção que deseja executar:");
            System.out.println("""
                1 - Calcular preço total;
                2 - Calcular troco;
                3 - Sair;
                """);

            int op = scan.nextInt();

            if (op == 1) {
                pTotal();
            }

            if (op == 2) {
                troco();
            }

            if (op == 3) {
                sair = false;
            }
        }
    }

    public static void pTotal() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Entre com a quantidade de plantas compradas: ");
        int quant = scan.nextInt();

        System.out.println("Entre com o valor da planta: ");
        int valor = scan.nextInt();

        int total = quant * valor;
        System.out.println("Valor total: R$" + total);
    }

    public static void troco() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Entre com o valor recebido pelo cliente: ");
        int totalCliente = scan.nextInt();

        System.out.println("Entre com o valor da compra: ");
        int totalCompra = scan.nextInt();

        int total = totalCliente - totalCompra;
        System.out.println("Troco a ser dado: R$" + total);
    }
}