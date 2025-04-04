import java.util.ArrayList;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("1- Cadastrar vendedor");
        System.out.println("2- Apresentar vendedor");
        System.out.println("3- Cadastrar cliente");
        System.out.println("4- Apresentar cliente");
        System.out.println("5- Media salarial ");
        System.out.println("6- Salario com bonus ");

        int opcao = scanner.nextInt();

        while (opcao != 6) {
            switch (opcao) {
                case 1: {
                    Loja.cadastroVendedor();
                    break;
                }
                case 2: {
                    Loja.mostrarVendedor();
                    break;
                }
                case 3: {
                    Loja.cadastroCliente();
                    break;
                }
                case 4: {
                    Loja.mostrarCliente();
                    break;
                }
                case 5: {
                    Loja.calcularMediaSalarial();
                    break;
                }
                case 6: {
                    Loja.calcularSalarioComBonus();
                    break;
                }

            }
            System.out.println("1- Cadastrar vendedor");
            System.out.println("2- Apresentar vendedor");
            System.out.println("3- Cadastrar cliente");
            System.out.println("4- Apresentar cliente");
            System.out.println("5- Media salarial ");
            System.out.println("6- Salario com bonus ");
            opcao = scanner.nextInt();
        }
    }
}