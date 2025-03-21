import java.util.Scanner;
import java.util.ArrayList;


public class Calculadora {

    public static Scanner scan = new Scanner(System.in);
    public static ArrayList<Double> listaValores = new ArrayList<>();
    public static ArrayList<Integer> listaItens = new ArrayList<>();
    public static ArrayList<Double> listaDescontos = new ArrayList<>();
    public static double valor;
    public static double des;
    public static double tot;
    public static int [][] matrizAno = new int [12][31];




    public static void main(String[] args) {




        boolean menu=true;




        System.out.println("Bem vindo!");




        while(menu) {




            System.out.println("");
            System.out.println("Escolha uma das opcoes:");
            System.out.println("1-Calcular Preço.");
            System.out.println("2-Troco.");
            System.out.println("3-Registro de vendas");
            System.out.println("4-Historico de vendas");
            System.out.println("5-Sair");
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
                    relatorioDia();
                    break;
                case 5:
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


        adicionarVendas();


//        System.out.println("Digite o mes usando numeros");
//        int mes = scan.nextInt();
//        System.out.println("Digite o dia do mes");
//        int dia = scan.nextInt();
//
//        for(int i = 0; i < 12; i++){
//            for(int j = 0; j < 31; j++){
//
//                if(i == mes && j == dia){
//                    matrizAno[i][j] = matrizAno[i][j] + 1;
//                }
//
//            }
//        }




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


    public static void adicionarVendas(){


        System.out.println("Digite o mes usando numeros");
        int mes = scan.nextInt();
        System.out.println("Digite o dia do mes");
        int dia = scan.nextInt();


        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 31; j++){


                if(i == mes && j == dia){
                    matrizAno[i][j] = matrizAno[i][j] + 1;
                }


            }
        }


    }


    public static void relatorioDia(){


        System.out.println("Digite o mes");
        int procurarMes = scan.nextInt();
        System.out.println("Digite o dia");
        int procurarDia = scan.nextInt();


        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 31; j++) {


                if(i == procurarMes && j == procurarDia){
                    System.out.println("Teve o total de " + matrizAno[i][j] + " nesse dia.");
                }


            }


        }






    }









}
