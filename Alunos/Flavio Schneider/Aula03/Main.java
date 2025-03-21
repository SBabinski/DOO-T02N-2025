import java.util.Scanner;
import java.util.ArrayList;


public class Main {
    public static Scanner scan = new Scanner(System.in);
    public static ArrayList<Double> listaValores = new ArrayList<>();
    public static ArrayList<Integer> listaItens = new ArrayList<>();
    public static ArrayList<Double> listaDescontos = new ArrayList<>();
    public static double valor;
    public static double des;
    public static double tot;
//    public static int [][] matrizAno = new int [12][30];

    public static void main(String[] args) {

        boolean menu=true;

        System.out.println("Bem vindo!");

        while(menu) {

            System.out.println("");
            System.out.println("Escolha uma das opcoes:");
            System.out.println("1-Calcular Preço.");
            System.out.println("2-Troco.");
            System.out.println("3-Historico de vendas");
            System.out.println("4-Sair");
            System.out.println("");

            int op = scan.nextInt();

            switch (op) {

                case 1:
                    calcularPreco();
                    break;
                case 2:
                    troco();
                    break;
                case 3:
                    listar();
                    break;
                case 4:
                    menu = false;
                    break;
                default:
                    System.out.println("opcao invalida");
            }

        }




    }

    public static void calcularPreco() {

        des=0;

        System.out.println("Qual o preco da planta?");
        double preco = scan.nextDouble();
        System.out.println("Qual a quantidade?");
        int quant = scan.nextInt();

        valor = preco * quant;

        if(quant>10) {
            tot = valor * 0.95;
            des = valor - tot;
            System.out.println("O desconto de 5% é:" + des);
            System.out.println("O preco total da compra é:" + tot);
        }
        else {
            System.out.println("O preco total da compra é:" + valor);
        }

        listaItens.add(quant);
        listaValores.add(valor);
        listaDescontos.add(des);



//       System.out.println("O preco total da compra é: " + tot);



    }

    public static void troco() {

        System.out.println("Qual o valor pago?");
        double paga = scan.nextDouble();

        tot = paga - tot;

        System.out.println("Troco:" + tot);

    }

    public static void listar() {

        for(int i = 0; i < listaValores.size(); i++) {

            System.out.println("Valor da venda: " + listaValores.get(i) +
                    ", Quantidade de itens: " + listaItens.get(i) +
                    ", Desconto: " + listaDescontos.get(i));
        }
    }



}