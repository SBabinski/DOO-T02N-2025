import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Menu.mostrarMenu();


    }

    public static class Menu {
        public static void mostrarMenu() {
            Scanner sc = new Scanner(System.in);
            int menu = 0;


            while (menu != 4) {
                System.out.println("Digite [1] para calcular o preço total do produto!");
                System.out.println("Digite [2] para calcular o troco do produto!");
                System.out.println("Digite [3] para pesquisar relatório do mês do produto!");
                System.out.println("Digite [4] para sair");

                menu = sc.nextInt();


                switch (menu) {
                    case 1:
                        Precototal.calcularPrecototal();
                        break;
                    case 2:
                        CalcularTroco.calculartroco();
                        break;
                    case 3:
                        Precototal.PesquisarData();
                        break;
                    case 4:
                        System.out.println("Saindo do programa!");
                        sc.close();
                        return;
                    default:
                        System.out.println("\n-----Opção Incorreta. Digite novamente uma das opções:-----\n");
                        break;
                }
            }
        }
    }
}

