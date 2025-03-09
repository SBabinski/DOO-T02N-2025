import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("1- Calcular compra 2- Valor Troco 3- Sair");
        int opcao=0;

        while(opcao !=3)
        {
            opcao = scanner.nextInt();
            switch(opcao)
            {
                case 1:
                {
                   Calculadora.Calculadora();
                   break;
                }

                case 2:
                {
                    Calculadora.CalcTroco();
                    break;
                }

                case 3:
                {
                    System.out.println("Saindo...");
                    break;
                }
            }
            System.out.println("1- Calcular compra 2- Valor Troco 3- Sair");
        }
        scanner.close();
        }
    }
