import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int opcao = 0;


        System.out.println("1- Calcular Compra 2- Pagar compra 3- Verificar registro 4- Sair");

        while (opcao != 4) {

            opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    Calculadora.Calculadora();
                    break;
                case 2:
                    Calculadora.CalcTroco();
                    break;
                case 3:
                    Calculadora.exibirHistoricoVendas();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
            System.out.println("1- Calcular Compra 2- Pagar compra 3- Verificar registro 4- Sair");
        }

    }
}