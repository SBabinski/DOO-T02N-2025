import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Menu.mostrarMenu();


    }

    public static class Menu {
        public static void mostrarMenu() {
            Scanner sc = new Scanner(System.in);
            int menu = 0;

            System.out.println("Digite [1] para calcular o preço total do produto!");
            System.out.println("Digite [2] para calcular o troco do produto!");
            System.out.println("Digite [3] para sair");

            menu = sc.nextInt();


            switch (menu) {
                case 1:
                    Precototal.calcularPrecototal();
                    break;
                case 2:
                    CalcularTroco.calculartroco();
                    break;
                    case 3:
                        sc.close();
            }
        }
    }
}