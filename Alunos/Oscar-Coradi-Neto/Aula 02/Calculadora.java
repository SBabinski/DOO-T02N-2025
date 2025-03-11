
import java.util.Scanner;



public class Calculadora {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        

        System.out.println("Informe a quantidade de plantas");
        int quantidade = scanner.nextInt();

        System.out.println("Informe o valor da planta");
        double valorPlanta = scanner.nextDouble();

        

        double precoTotal = PrecoTotal(quantidade, valorPlanta);
        

        boolean menu = true;
        while(menu){
                System.out.println("Informe a operação desejada: \n 1 - Calcular o preço total \n 2 - Calcular troco \n 3 - Sair");
                int op = scanner.nextInt();

                switch(op){
                    case 1:
                        System.out.println("O valor total é de: " + precoTotal);
                    break;

                    case 2:
                        System.out.println("Informe o valor recebido");
                        double valorRecebido = scanner.nextDouble();
                        double troco = Troco(valorRecebido, precoTotal);
                        System.out.println("O troco é de: " + troco);
                    break;

                    case 3:
                        System.out.println("Saindo do sistema...");
                        menu = false;
                    break;

                    default:
                        System.out.println("Opção inválida, tente novamente");
                }
        }

        

        
    }

    public static double PrecoTotal(int quantidade, double valorPlanta){
        return quantidade * valorPlanta;
    }

    public static double Troco(double valorRecebido, double precoTotal){
        return valorRecebido - precoTotal;
    }
}
