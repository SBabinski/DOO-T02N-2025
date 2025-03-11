import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalculadoraUpgrade {

    public static Scanner sc = new Scanner(System.in);

    public static boolean flagMenu = true;

    public static int quantidade = 0;
    public static double valorTotal = 0;
    public static double valorTroco = 0;
    public static double valorDesconto = 0;

    public static Venda novaVenda;
    public static List<Venda> registroVendas = new ArrayList<Venda>();

    public static void main(String[] args) {
        menu();
    }

    public static double total(int quantidade, float valor) {
        valorTotal = (quantidade * valor) - desconto(quantidade, valor);
        return valorTotal;
    }

    public static double troco(float recebido, double total) {
        valorTroco = recebido - total;
        return valorTroco;
    }

    public static double desconto(int qtd, float total) {
        if (qtd > 10) {
            valorDesconto = 0.05 * (total * qtd) ;
        }
        return valorDesconto;
    }

    public static void menu() {
        while(flagMenu) {
            System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
            System.out.println("[1] Calcular preço total | total R$" + valorTotal);
            System.out.println("[2] Calcular Troco");
            System.out.println("[3] Visualizar registros");
            System.out.println("[0] Sair");
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
                for (int i = 0; i < registroVendas.size(); i++) {
                    System.out.println(registroVendas.get(i).toString());
                }
                break;
            case 0:
                flagMenu = false;
        }
    }

    public static void calcularPrecoTotal() {
        float valorItem;

        if (iniciarNovaVenda()) {

            System.out.println("Digite o valor do produto: ");
            valorItem = sc.nextFloat();

            System.out.println("Digite o quantidade de produtos: ");
            quantidade = sc.nextInt();
            System.out.println("Valor Total:" + total(quantidade, valorItem));

            valorTroco = 0;
        }
    }

    public static void calcularTroco() {
        float recebido;
        System.out.println("Digite o valor recebido: ");
        recebido = sc.nextFloat();

        System.out.println("Valor do troco: " + troco(recebido, valorTotal));

        novaVenda = new Venda(quantidade, valorTotal, valorDesconto);
        registroVendas.add(novaVenda);
    }

    public static boolean iniciarNovaVenda() {
        int resp;

        System.out.println("Deseja iniciar nova venda?");
        System.out.println("[1] Sim     [2] Não");

        resp = sc.nextInt();
        sc.nextLine();

        if (resp == 1) {
            return true;
        } else {
            return false;
        }

    }
}
