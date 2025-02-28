import java.util.Scanner;

public class Calculadora {

    public static Scanner sc = new Scanner(System.in);

    public static boolean b = true;
    public static float valorTotal = 0;
    public static float valorTroco = 0;

    public static void main(String[] args) {
        menu();
    }

    public static float total(int quantidade, float valor) {
        valorTotal = quantidade * valor;
        return valorTotal;
    }

    public static float troco(float recebido, float total) {
        valorTroco = recebido - total;
        return valorTroco;
    }

    public static void menu() {
        while(b) {
            System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
            System.out.println("[1] Calcular preço total | atual R$" + valorTotal);
            System.out.println("[2] Calcular Troco | atual R$" + valorTroco);
            System.out.println("[3] Sair");
            System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");

            opcoesMenu(sc.nextInt());

        }
    }

    public static void opcoesMenu(int opc) {

        switch (opc) {
            case 1: //Calcular preço total
                calcularPrecoTotal();
                break;
            case 2: //Calcular troco
                calcularTroco();
                break;
            case 3:
                b = false;
        }
    }

    public static void calcularPrecoTotal() {
        int qtd;
        float valorItem;

        System.out.println("Digite o valor do produto: ");
        valorItem = sc.nextFloat();

        System.out.println("Digite o quantidade de produtos: ");
        qtd = sc.nextInt();
        System.out.println("Valor Total:" + total(qtd, valorItem));

        valorTroco = 0;
    }

    public static void calcularTroco() {
        float recebido;
        System.out.println("Digite o valor recebido: ");
        recebido = sc.nextFloat();

        System.out.println("Valor do troco: " + troco(recebido, valorTotal));
    }
}