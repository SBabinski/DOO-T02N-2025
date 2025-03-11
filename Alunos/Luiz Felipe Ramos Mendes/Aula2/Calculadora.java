import java.util.Scanner;

public class Calculadora {

    public static void Calculadora(){
        Scanner scan = new Scanner(System.in);

        System.out.println("Digite o valor da planta: ");
        int valorPlanta = scan.nextInt();

        System.out.println("Digite a quantidade que vai comprar: ");
        int quantPlanta = scan.nextInt();

        int valorTotal = valorPlanta * quantPlanta;

        System.out.println("Seu total foi: R$ " + valorTotal);
    }

    public static void CalcTroco(){

        Scanner scan = new Scanner(System.in);

        System.out.println("Digite o valor do total: ");
        int valorTotal = scan.nextInt();

        System.out.println("Digite o valor pago: ");
        int valorPago = scan.nextInt();

        int quantTroco = valorPago- valorTotal;

        int valorTotalTroco = valorPago - valorTotal;

        System.out.println("Seu troco foi: R$ " + valorTotalTroco);
    }
}
