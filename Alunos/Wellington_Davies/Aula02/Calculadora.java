import java.util.Scanner;

public class Calculadora {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        boolean rodar = true;

        while (rodar) {
            System.out.println("""
                        Selecione entre as opções:
                        [1] - Calcular Preço Total e Troco
                        [2] - Calcular Troco Manualmente
                        [3] - Sair
                    """);
            System.out.print("Opção?: ");
            int opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    calculadora();
                    break;
                case 2:
                    System.out.print("Digite o valor da compra: ");
                    float valorCompra = scan.nextFloat();
                    System.out.print("Digite o valor recebido do cliente: ");
                    float valorTransferencia = scan.nextFloat();
                    calculadoraTroco(valorCompra, valorTransferencia);
                    break;
                case 3:
                    rodar = false;
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    public static void calculadora() {
        float valorPlanta = 10;
        System.out.println("CADA PLANTA VALE 10 REAIS");

        System.out.print("Digite a quantidade de plantas: ");
        int quantidadePlantas = scan.nextInt();
        float valorCompra = valorPlanta * quantidadePlantas;
        System.out.println("Valor da compra: " + valorCompra + " REAIS");

        System.out.print("Valor recebido do cliente: ");
        float valorTransferencia = scan.nextFloat();

        calculadoraTroco(valorCompra, valorTransferencia);
    }

    public static void calculadoraTroco(float valorCompra, float valorTransferencia) {
        float valorTroco = valorTransferencia - valorCompra;

        if (valorTroco < 0) {
            System.out.println("O cliente ainda deve:" + valorTroco);
        } else {
            System.out.println("O troco do cliente é:" + valorTroco);
        }
    }
}
