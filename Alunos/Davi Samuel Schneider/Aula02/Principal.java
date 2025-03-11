import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        executar();

    }

    public static void menu() {
        System.out.println("1- Calcular Preço Total");
        System.out.println("2- Calcular Troco");
        System.out.println("0- Sair");
    }

    public static void executar() {
        Scanner sc = new Scanner(System.in);
        int opc = 1;

        do {
            menu();
            opc = sc.nextInt();

            switch (opc) {
                case 1:
                    Calculadora.calcularVendas();
                    break;
                case 2:
                    Calculadora.calcularTroco();
                    break;
                default:
                    System.exit(0);
                    break;

            }
        } while (true);
    }
}
